package com.noads.vip.pass ;

import android.app.Activity ;
import android.app.AlertDialog ;
import android.content.ClipData ;
import android.content.ClipboardManager ;
import android.content.Context ;
import android.content.Intent ;
import android.content.SharedPreferences ;
import android.net.Uri ;
import android.os.Bundle ;
import android.text.InputType ;
import android.view.Gravity ;
import android.widget.* ;

import java.io.* ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Locale ;
import java.util.zip.ZipEntry ;
import java.util.zip.ZipFile ;

import org.jf.baksmali.Adaptors.ClassDefinition ;
import org.jf.baksmali.Adaptors.MethodDefinition ;
import org.jf.baksmali.BaksmaliOptions ;
import org.jf.baksmali.formatter.BaksmaliWriter ;

import org.jf.dexlib2.DexFileFactory ;
import org.jf.dexlib2.iface.ClassDef ;
import org.jf.dexlib2.iface.DexFile ;
import org.jf.dexlib2.iface.Field ;
import org.jf.dexlib2.iface.Method ;

public class ApkEditorActivity extends Activity {

    // =========================================================
    // APK
    // =========================================================

    private Uri apkUri ;

    private TextView apkInfo ;
    private TextView statusText ;
    private LinearLayout editorContainer ;

    // =========================================================
    // DEX / CLASE / MÉTODO
    // =========================================================

    private File currentDexFile ;
    private String currentDexName ;
    private String currentClassName ;
    private Method currentMethod ;

    private final ArrayList<String> dexNames = new ArrayList<String>() ;

    // =========================================================
    // GUARDAR SMALI
    // =========================================================

    private static final int REQUEST_SAVE_SMALI = 501 ;

    private String pendingSmaliContent = "" ;

    // =========================================================
    // BUILD APK
    // =========================================================

    private File buildWorkDir ;
    private File buildSmaliDir ;
    private File buildOriginalDex ;
    private File buildNewDex ;
    private File buildUnsignedApk ;
    private File buildSignedApk ;

    // APK que se está esperando guardar mediante ACTION_CREATE_DOCUMENT
    private File pendingSaveApk ;

    // =========================================================
    // FIRMA
    // =========================================================

    private static final int REQUEST_KEYSTORE = 601 ;
    private static final int REQUEST_SAVE_APK = 602 ;

    private Uri selectedKeystoreUri ;

    private String pendingKeystorePassword = "" ;
    private String pendingAlias = "" ;
    private String pendingKeyPassword = "" ;

    // =========================================================
    // PREFERENCIAS DE FIRMA
    // =========================================================

    private static final String PREFS_SIGNING = "apk_signing_config" ;

    private static final String PREF_KEYSTORE_URI = "keystore_uri" ;

    private static final String PREF_ALIAS = "alias" ;

    private static final String PREF_STORE_PASSWORD = "store_password" ;

    private static final String PREF_KEY_PASSWORD = "key_password" ;

    // =========================================================
    // ON CREATE
    // =========================================================

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState ) ;

        apkUri = getIntent().getData() ;

        createInterface() ;

        if ( apkUri == null ) {

            statusText.setText( "❌ No se recibió ningún APK." ) ;

            return ;
        }

        showApkInfo() ;
    }

    // =========================================================
    // INTERFAZ
    // =========================================================

    private void createInterface() {

        LinearLayout root = new LinearLayout( this ) ;

        root.setOrientation( LinearLayout.VERTICAL ) ;

        root.setPadding( dp( 16 ), dp( 16 ), dp( 16 ), dp( 16 ) ) ;

        TextView title = text( "APK EDITOR", 28 ) ;

        title.setTypeface( android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD ) ;

        root.addView( title, params( -1, -2 ) ) ;

        TextView subtitle = text( "Explorador APK / DEX / Smali", 15 ) ;

        root.addView( subtitle, marginParams( -1, -2, 0, 0, 0, 12 ) ) ;

        apkInfo = text( "APK: ninguno", 14 ) ;

        apkInfo.setTextIsSelectable( true ) ;

        root.addView( apkInfo, params( -1, -2 ) ) ;

        statusText = text( "Esperando APK...", 14 ) ;

        root.addView( statusText, marginParams( -1, -2, 0, 8, 0, 12 ) ) ;

        ScrollView scroll = new ScrollView( this ) ;

        editorContainer = new LinearLayout( this ) ;

        editorContainer.setOrientation( LinearLayout.VERTICAL ) ;

        editorContainer.setPadding( 0, dp( 8 ), 0, dp( 30 ) ) ;

        scroll.addView( editorContainer ) ;

        root.addView( scroll, new LinearLayout.LayoutParams( -1, 0, 1 ) ) ;

        Button back = new Button( this ) ;

        back.setText( "ATRÁS" ) ;

        back.setOnClickListener( v -> finish() ) ;

        root.addView( back, params( -1, -2 ) ) ;

        setContentView( root ) ;

        createEditorOptions() ;
    }

    // =========================================================
    // OPCIONES
    // =========================================================

    private void createEditorOptions() {

        editorContainer.removeAllViews() ;

        currentDexFile = null ;
        currentDexName = null ;
        currentClassName = null ;
        currentMethod = null ;

        Button dexButton = new Button( this ) ;

        dexButton.setText( "📦 VER ARCHIVOS DEX" ) ;

        dexButton.setOnClickListener( v -> loadDexList() ) ;

        editorContainer.addView( dexButton, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;

        Button searchButton = new Button( this ) ;

        searchButton.setText( "🔎 BUSCAR EN DEX" ) ;

        searchButton.setOnClickListener( v -> showSearchDialog() ) ;

        editorContainer.addView( searchButton, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;

        Button infoButton = new Button( this ) ;

        infoButton.setText( "ℹ️ INFORMACIÓN DEL APK" ) ;

        infoButton.setOnClickListener( v -> showApkDetails() ) ;

        editorContainer.addView( infoButton, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;

        TextView info = text( "\nEXPLORADOR\n\n" + "APK\n" + " ↓\n" + "DEX\n" + " ↓\n" + "CLASE\n"
                + " ↓\n" + "MÉTODO\n" + " ↓\n" + "SMALI\n\n" + "El código Smali se genera "
                + "mediante Baksmali 2.5.2.", 14 ) ;

        info.setPadding( dp( 10 ), dp( 10 ), dp( 10 ), dp( 10 ) ) ;

        editorContainer.addView( info, marginParams( -1, -2, 0, 12, 0, 0 ) ) ;
    }

    // =========================================================
    // INFORMACIÓN APK
    // =========================================================

    private void showApkInfo() {

        String name = getDisplayName( apkUri ) ;

        apkInfo.setText(
                "📦 APK seleccionado\n\n" + "Nombre: " + name + "\n\nURI:\n" + apkUri.toString() ) ;

        statusText.setText( "✓ APK cargado correctamente." ) ;
    }

    // =========================================================
    // LISTA DEX
    // =========================================================

    private void loadDexList() {

        if ( apkUri == null ) {

            Toast.makeText( this, "❌ No hay APK seleccionado.", Toast.LENGTH_SHORT ).show() ;

            return ;
        }

        statusText.setText( "📦 Buscando archivos DEX..." ) ;

        editorContainer.removeAllViews() ;

        new Thread( () -> {

            File apk = null ;

            try {

                apk = copyApkToCache() ;

                ArrayList<String> found = new ArrayList<String>() ;

                ZipFile zip = new ZipFile( apk ) ;

                try {

                    java.util.Enumeration<? extends ZipEntry> entries = zip.entries() ;

                    while ( entries.hasMoreElements() ) {

                        ZipEntry entry = entries.nextElement() ;

                        String name = entry.getName() ;

                        if ( name.matches( "classes(\\d*)?\\.dex" ) ) {

                            found.add( name ) ;
                        }
                    }

                } finally {

                    zip.close() ;
                }

                if ( apk.exists() ) {
                    apk.delete() ;
                }

                runOnUiThread( () -> showDexList( found ) ) ;

            } catch ( Exception e ) {

                if ( apk != null && apk.exists() ) {

                    apk.delete() ;
                }

                final String error = getErrorMessage( e ) ;

                runOnUiThread( () -> statusText.setText( "❌ Error:\n" + error ) ) ;
            }

        } ).start() ;
    }

    // =========================================================
    // MOSTRAR DEX
    // =========================================================

    private void showDexList( List<String> names ) {

        editorContainer.removeAllViews() ;

        dexNames.clear() ;

        if ( names != null ) {
            dexNames.addAll( names ) ;
        }

        if ( names == null || names.isEmpty() ) {

            statusText.setText( "❌ No se encontraron DEX." ) ;

            editorContainer.addView( text( "No se encontraron classes*.dex.", 14 ),
                    params( -1, -2 ) ) ;

            createBackToToolsButton() ;

            return ;
        }

        statusText.setText( "✓ DEX encontrados: " + names.size() ) ;

        TextView title = text( "ARCHIVOS DEX\n\n" + "Cantidad: " + names.size(), 20 ) ;

        title.setTypeface( android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD ) ;

        editorContainer.addView( title, marginParams( -1, -2, 0, 0, 0, 12 ) ) ;

        for ( String dexName : names ) {

            final String selectedDex = dexName ;

            Button button = new Button( this ) ;

            button.setText( "📄 " + selectedDex + "\nAbrir clases" ) ;

            button.setOnClickListener( v -> openDexClasses( selectedDex ) ) ;

            editorContainer.addView( button, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;
        }

        createBackToToolsButton() ;
    }

    // =========================================================
    // ABRIR DEX
    // =========================================================

    private void openDexClasses( String dexName ) {

        statusText.setText( "🔍 Cargando " + dexName + "..." ) ;

        new Thread( () -> {

            File apk = null ;
            File dexFile = null ;

            try {

                apk = copyApkToCache() ;

                ZipFile zip = new ZipFile( apk ) ;

                try {

                    ZipEntry entry = zip.getEntry( dexName ) ;

                    if ( entry == null ) {

                        throw new Exception( "No se encontró " + dexName ) ;
                    }

                    dexFile = new File( getCacheDir(), "editor_" + dexName ) ;

                    InputStream input = zip.getInputStream( entry ) ;

                    try {

                        FileOutputStream output = new FileOutputStream( dexFile ) ;

                        try {

                            byte[] buffer = new byte[8192] ;

                            int read ;

                            while ( ( read = input.read( buffer ) ) != -1 ) {

                                output.write( buffer, 0, read ) ;
                            }

                        } finally {

                            output.close() ;
                        }

                    } finally {

                        input.close() ;
                    }

                } finally {

                    zip.close() ;
                }

                DexFile dex = DexFileFactory.loadDexFile( dexFile, null ) ;

                ArrayList<String> classes = new ArrayList<String>() ;

                for ( ClassDef cls : dex.getClasses() ) {

                    if ( cls == null ) {
                        continue ;
                    }

                    if ( cls.getType() != null ) {

                        classes.add( cls.getType() ) ;
                    }
                }

                final File finalDex = dexFile ;

                runOnUiThread( () -> showClassList( dexName, classes, finalDex ) ) ;

                if ( apk.exists() ) {
                    apk.delete() ;
                }

            } catch ( Exception e ) {

                if ( dexFile != null && dexFile.exists() ) {

                    dexFile.delete() ;
                }

                if ( apk != null && apk.exists() ) {

                    apk.delete() ;
                }

                final String error = getErrorMessage( e ) ;

                runOnUiThread( () -> statusText.setText( "❌ Error:\n" + error ) ) ;
            }

        } ).start() ;
    }

    // =========================================================
    // LISTA CLASES
    // =========================================================

    // =========================================================
    // LISTA CLASES + BUSCADOR INTERNO
    // =========================================================

    private void showClassList( String dexName, ArrayList<String> classes, File dexFile ) {

        editorContainer.removeAllViews() ;

        currentDexFile = dexFile ;
        currentDexName = dexName ;

        statusText.setText( "✓ " + dexName + " cargado. Clases: " + classes.size() ) ;

        TextView title = text( dexName + "\n\nCLASES: " + classes.size(), 20 ) ;

        title.setTypeface( android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD ) ;

        editorContainer.addView( title, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;

        // =====================================================
        // BUSCADOR DE CLASES
        // =====================================================

        EditText classSearch = new EditText( this ) ;

        classSearch.setHint( "🔎 Buscar clase..." ) ;

        classSearch.setSingleLine( true ) ;

        classSearch.setInputType(
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS ) ;

        classSearch.setPadding( dp( 12 ), dp( 12 ), dp( 12 ), dp( 12 ) ) ;

        editorContainer.addView( classSearch, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;

        // =====================================================
        // CONTADOR
        // =====================================================

        final TextView classCounter = text( "Mostrando: " + classes.size() + " / " + classes.size(),
                14 ) ;

        classCounter.setTextIsSelectable( true ) ;

        editorContainer.addView( classCounter, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;

        // =====================================================
        // CONTENEDOR DE CLASES
        // =====================================================

        final LinearLayout classesContainer = new LinearLayout( this ) ;

        classesContainer.setOrientation( LinearLayout.VERTICAL ) ;

        editorContainer.addView( classesContainer, new LinearLayout.LayoutParams( -1, -2 ) ) ;

        // =====================================================
        // MOSTRAR / FILTRAR CLASES
        // =====================================================

        final Runnable updateClasses = new Runnable() {

            @Override
            public void run() {

                String filter = classSearch.getText().toString().trim().toLowerCase( Locale.ROOT ) ;

                classesContainer.removeAllViews() ;

                int count = 0 ;

                for ( String className : classes ) {

                    if ( className == null ) {
                        continue ;
                    }

                    String searchable = className.toLowerCase( Locale.ROOT ) ;

                    // Si hay búsqueda,
                    // solamente mostramos
                    // las clases que coinciden.
                    if ( !filter.isEmpty() && !searchable.contains( filter ) ) {

                        continue ;
                    }

                    final String selectedClass = className ;

                    Button button = new Button( ApkEditorActivity.this ) ;

                    button.setText( formatClassName( selectedClass ) ) ;

                    button.setGravity( Gravity.START | Gravity.CENTER_VERTICAL ) ;

                    // =================================================
                    // CLICK EN LA CLASE
                    // =================================================

                    button.setOnClickListener( v -> showClassDetails( dexFile, selectedClass ) ) ;

                    classesContainer.addView( button, marginParams( -1, -2, 0, 0, 0, 6 ) ) ;

                    count++ ;
                }

                // =================================================
                // CONTADOR
                // =================================================

                classCounter.setText( "Mostrando: " + count + " / " + classes.size() ) ;

                // =================================================
                // SIN RESULTADOS
                // =================================================

                if ( count == 0 ) {

                    TextView empty = text( filter.isEmpty() ? "No hay clases."
                            : "❌ No se encontró ninguna clase que coincida con:\n\n" + filter,
                            14 ) ;

                    empty.setPadding( dp( 10 ), dp( 10 ), dp( 10 ), dp( 10 ) ) ;

                    classesContainer.addView( empty, marginParams( -1, -2, 0, 4, 0, 8 ) ) ;
                }
            }
        } ;

        // =====================================================
        // MOSTRAR TODAS AL INICIO
        // =====================================================

        updateClasses.run() ;

        // =====================================================
        // BUSCAR EN TIEMPO REAL
        // =====================================================

        classSearch.addTextChangedListener( new android.text.TextWatcher() {

            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {
            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count ) {

                updateClasses.run() ;
            }

            @Override
            public void afterTextChanged( android.text.Editable s ) {
            }
        } ) ;

        // =====================================================
        // VOLVER
        // =====================================================

        createBackToDexButton() ;
    }
    // =========================================================
    // DETALLES CLASE
    // =========================================================

    private void showClassDetails( File dexFile, String className ) {

        try {

            DexFile dex = DexFileFactory.loadDexFile( dexFile, null ) ;

            ClassDef selected = null ;

            for ( ClassDef cls : dex.getClasses() ) {

                if ( cls != null && className.equals( cls.getType() ) ) {

                    selected = cls ;
                    break ;
                }
            }

            if ( selected == null ) {

                Toast.makeText( this, "Clase no encontrada.", Toast.LENGTH_SHORT ).show() ;

                return ;
            }

            currentDexFile = dexFile ;
            currentClassName = className ;

            editorContainer.removeAllViews() ;

            TextView title = text( "DETALLES DE CLASE\n\n" + formatClassName( className ), 20 ) ;

            title.setTypeface( android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD ) ;

            editorContainer.addView( title, marginParams( -1, -2, 0, 0, 0, 12 ) ) ;

            TextView fieldsTitle = text( "CAMPOS", 17 ) ;

            fieldsTitle.setTypeface( android.graphics.Typeface.DEFAULT,
                    android.graphics.Typeface.BOLD ) ;

            editorContainer.addView( fieldsTitle, marginParams( -1, -2, 0, 0, 0, 6 ) ) ;

            int fieldCount = 0 ;

            for ( Field field : selected.getFields() ) {

                if ( field == null ) {
                    continue ;
                }

                TextView fieldView = text( field.getName() + " : " + field.getType(), 14 ) ;

                fieldView.setTextIsSelectable( true ) ;

                editorContainer.addView( fieldView, marginParams( -1, -2, 8, 0, 0, 4 ) ) ;

                fieldCount++ ;
            }

            if ( fieldCount == 0 ) {

                editorContainer.addView( text( "(ninguno)", 14 ),
                        marginParams( -1, -2, 0, 0, 0, 8 ) ) ;
            }

            TextView methodsTitle = text( "\nMÉTODOS", 17 ) ;

            methodsTitle.setTypeface( android.graphics.Typeface.DEFAULT,
                    android.graphics.Typeface.BOLD ) ;

            editorContainer.addView( methodsTitle, marginParams( -1, -2, 0, 8, 0, 6 ) ) ;

            int methodCount = 0 ;

            for ( Method method : selected.getMethods() ) {

                if ( method == null ) {
                    continue ;
                }

                final Method selectedMethod = method ;

                Button methodButton = new Button( this ) ;

                methodButton.setText( buildMethodDisplaySignature( selectedMethod ) ) ;

                methodButton.setOnClickListener(
                        v -> showMethodSmali( dexFile, className, selectedMethod ) ) ;

                editorContainer.addView( methodButton, marginParams( -1, -2, 0, 0, 0, 6 ) ) ;

                methodCount++ ;
            }

            if ( methodCount == 0 ) {

                editorContainer.addView( text( "(ninguno)", 14 ), params( -1, -2 ) ) ;
            }

            createBackToDexButton() ;

        } catch ( Exception e ) {

            Toast.makeText( this, "Error: " + getErrorMessage( e ), Toast.LENGTH_LONG ).show() ;
        }
    }

    // =========================================================
    // FIRMA INTERNA DEL MÉTODO
    // =========================================================

    private String buildMethodSignature( Method method ) {

        if ( method == null ) {
            return "" ;
        }

        StringBuilder sb = new StringBuilder() ;

        sb.append( "(" ) ;

        List<? extends CharSequence> parameters = method.getParameterTypes() ;

        if ( parameters != null ) {

            for ( CharSequence parameter : parameters ) {

                sb.append( parameter ) ;
            }
        }

        sb.append( ")" ) ;

        sb.append( method.getReturnType() ) ;

        return sb.toString() ;
    }

    // =========================================================
    // FIRMA PARA MOSTRAR
    // =========================================================

    private String buildMethodDisplaySignature( Method method ) {

        if ( method == null ) {
            return "" ;
        }

        return method.getName() + buildMethodSignature( method ) ;
    }

    // =========================================================
    // GENERAR SMALI
    // =========================================================

    private void showMethodSmali( File dexFile, String className, Method method ) {

        if ( method == null ) {
            return ;
        }

        currentDexFile = dexFile ;
        currentClassName = className ;
        currentMethod = method ;

        new Thread( () -> {

            try {

                final String smali = disassembleMethodWithBaksmali( method, className ) ;

                runOnUiThread( () -> showSmaliEditor( method, smali ) ) ;

            } catch ( Exception e ) {

                final String error = getErrorMessage( e ) ;

                runOnUiThread( () -> Toast
                        .makeText( this, "❌ Error generando Smali:\n" + error, Toast.LENGTH_LONG )
                        .show() ) ;
            }

        } ).start() ;
    }

    // =========================================================
    // BAKSMALI
    // =========================================================

    private String disassembleMethodWithBaksmali( Method method, String className )
            throws Exception {

        if ( method == null ) {
            return "" ;
        }

        if ( method.getImplementation() == null ) {

            return buildEmptyMethodWithBaksmali( method ) ;
        }

        BaksmaliOptions options = new BaksmaliOptions() ;

        options.implicitReferences = false ;
        options.debugInfo = true ;
        options.apiLevel = 35 ;

        ClassDef classDef = findClassForMethod( currentDexFile, className ) ;

        if ( classDef == null ) {

            throw new Exception( "No se encontró la clase " + className ) ;
        }

        ClassDefinition classDefinition = new ClassDefinition( options, classDef ) ;

        StringWriter stringWriter = new StringWriter() ;

        BaksmaliWriter writer = new BaksmaliWriter( stringWriter, null ) ;

        MethodDefinition methodDefinition = new MethodDefinition( classDefinition, method,
                method.getImplementation() ) ;

        methodDefinition.writeTo( writer ) ;

        writer.close() ;

        String result = stringWriter.toString() ;

        if ( result == null ) {
            return "" ;
        }

        return result.trim() ;
    }

    // =========================================================
    // BUSCAR CLASE
    // =========================================================

    private ClassDef findClassForMethod( File dexFile, String className ) throws Exception {

        if ( dexFile == null || className == null ) {

            return null ;
        }

        DexFile dex = DexFileFactory.loadDexFile( dexFile, null ) ;

        for ( ClassDef cls : dex.getClasses() ) {

            if ( cls != null && className.equals( cls.getType() ) ) {

                return cls ;
            }
        }

        return null ;
    }

    // =========================================================
    // MÉTODO SIN IMPLEMENTACIÓN
    // =========================================================

    private String buildEmptyMethodWithBaksmali( Method method ) {

        StringBuilder builder = new StringBuilder() ;

        String flags = formatAccessFlags( method.getAccessFlags() ) ;

        builder.append( ".method " ) ;

        if ( !flags.isEmpty() ) {

            builder.append( flags ) ;
            builder.append( " " ) ;
        }

        builder.append( method.getName() ) ;

        builder.append( buildMethodSignature( method ) ) ;

        builder.append( "\n" ) ;

        builder.append( ".end method" ) ;

        return builder.toString() ;
    }

    // =========================================================
    // EDITOR SMALI
    // =========================================================

    private void showSmaliEditor( Method method, String smaliCode ) {

        editorContainer.removeAllViews() ;

        statusText.setText( "✓ Smali generado correctamente." ) ;

        TextView title = text( "EDITOR SMALI\n\n" + method.getName(), 20 ) ;

        title.setTypeface( android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD ) ;

        editorContainer.addView( title, marginParams( -1, -2, 0, 0, 0, 12 ) ) ;

        TextView info = text( "Clase:\n" + formatClassName( currentClassName ) + "\n\nMétodo:\n"
                + buildMethodDisplaySignature( method ) + "\n\nGenerado mediante "
                + "Baksmali 2.5.2.", 13 ) ;

        info.setTextIsSelectable( true ) ;

        editorContainer.addView( info, marginParams( -1, -2, 0, 0, 0, 12 ) ) ;

        final EditText editor = new EditText( this ) ;

        editor.setText( smaliCode ) ;

        editor.setTextSize( 13 ) ;

        editor.setGravity( Gravity.TOP | Gravity.START ) ;

        editor.setSingleLine( false ) ;

        editor.setHorizontallyScrolling( true ) ;

        editor.setTextIsSelectable( true ) ;

        editor.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS ) ;

        editor.setTypeface( android.graphics.Typeface.MONOSPACE ) ;

        editor.setPadding( dp( 10 ), dp( 10 ), dp( 10 ), dp( 10 ) ) ;

        editorContainer.addView( editor, new LinearLayout.LayoutParams( -1, dp( 450 ) ) ) ;

        Button copy = new Button( this ) ;

        copy.setText( "📋 COPIAR SMALI" ) ;

        copy.setOnClickListener( v -> copyTextSafely( "Smali", editor.getText().toString() ) ) ;

        editorContainer.addView( copy, marginParams( -1, -2, 0, 8, 0, 6 ) ) ;

        Button save = new Button( this ) ;

        save.setText( "💾 GUARDAR .SMALI" ) ;

        save.setOnClickListener(
                v -> saveSmaliFile( editor.getText().toString(), method.getName() ) ) ;

        editorContainer.addView( save, marginParams( -1, -2, 0, 0, 0, 6 ) ) ;

        Button build = new Button( this ) ;

        build.setText( "🔨 COMPILAR APK" ) ;

        build.setOnClickListener( v -> buildModifiedApk( editor.getText().toString(), method ) ) ;

        editorContainer.addView( build, marginParams( -1, -2, 0, 0, 0, 6 ) ) ;

        Button back = new Button( this ) ;

        back.setText( "← VOLVER A CLASE" ) ;

        back.setOnClickListener( v -> showClassDetails( currentDexFile, currentClassName ) ) ;

        editorContainer.addView( back, marginParams( -1, -2, 0, 0, 0, 6 ) ) ;
    }

    // =========================================================
    // COMPILAR APK
    // =========================================================

    private void buildModifiedApk( String modifiedSmali, Method editedMethod ) {

        if ( apkUri == null ) {

            Toast.makeText( this, "❌ No hay APK seleccionado.", Toast.LENGTH_SHORT ).show() ;

            return ;
        }

        if ( currentDexName == null ) {

            Toast.makeText( this, "❌ No se conoce el DEX actual.", Toast.LENGTH_SHORT ).show() ;

            return ;
        }

        if ( currentClassName == null ) {

            Toast.makeText( this, "❌ No se conoce la clase actual.", Toast.LENGTH_SHORT ).show() ;

            return ;
        }

        if ( editedMethod == null ) {

            Toast.makeText( this, "❌ No se conoce el método actual.", Toast.LENGTH_SHORT ).show() ;

            return ;
        }

        if ( modifiedSmali == null || modifiedSmali.trim().isEmpty() ) {

            Toast.makeText( this, "❌ El Smali está vacío.", Toast.LENGTH_SHORT ).show() ;

            return ;
        }

        new Thread( () -> {

            try {

                // =================================================
                // 1. WORKSPACE
                // =================================================

                buildWorkDir = new File( getCacheDir(), "apkeditor_build" ) ;

                deleteDirectory( buildWorkDir ) ;

                if ( !buildWorkDir.mkdirs() && !buildWorkDir.exists() ) {

                    throw new Exception( "No se pudo crear el " + "directorio de trabajo." ) ;
                }

                buildSmaliDir = new File( buildWorkDir, "smali" ) ;

                if ( !buildSmaliDir.mkdirs() && !buildSmaliDir.exists() ) {

                    throw new Exception( "No se pudo crear " + "la carpeta Smali." ) ;
                }

                updateBuildStatus( "🔨 Preparando compilación..." ) ;

                // =================================================
                // 2. COPIAR APK
                // =================================================

                File originalApk = new File( buildWorkDir, "original.apk" ) ;

                copyUriToFile( apkUri, originalApk ) ;

                // =================================================
                // 3. EXTRAER DEX
                // =================================================

                buildOriginalDex = new File( buildWorkDir, currentDexName ) ;

                updateBuildStatus( "1/6 → Extrayendo " + currentDexName + "..." ) ;

                extractDexFromApk( originalApk, currentDexName, buildOriginalDex ) ;

                // =================================================
                // 4. DEX → SMALI
                // =================================================

                updateBuildStatus( "2/6 → Generando Smali..." ) ;

                SmaliManager smaliManager = new SmaliManager( 35 ) ;

                smaliManager.disassemble( buildOriginalDex, buildSmaliDir ) ;

                // =================================================
                // 5. LOCALIZAR CLASE
                // =================================================

                updateBuildStatus( "3/6 → Localizando clase..." ) ;

                File classSmali = findSmaliClassFile( buildSmaliDir, currentClassName ) ;

                if ( classSmali == null ) {

                    throw new Exception(
                            "No se encontró el archivo Smali de:\n" + currentClassName ) ;
                }

                // =================================================
                // 6. REEMPLAZAR MÉTODO
                // =================================================

                updateBuildStatus( "4/6 → Aplicando modificación..." ) ;

                String originalClassSmali = readTextFile( classSmali ) ;

                String newClassSmali = replaceMethodInClass( originalClassSmali, modifiedSmali,
                        editedMethod ) ;

                writeTextFile( classSmali, newClassSmali ) ;

                // =================================================
                // 7. GENERAR PROVIDER DEL APK OBJETIVO
                // =================================================

                String targetPackage = ManifestEditor.readPackageName( originalApk ) ;

                if ( targetPackage == null || targetPackage.trim().isEmpty() ) {

                    throw new Exception( "No se pudo determinar el package del APK objetivo." ) ;
                }

                updateBuildStatus( "5/8 → Generando ContentProvider..." ) ;

                PrefProviderSmaliGenerator.generate( buildSmaliDir, targetPackage ) ;

                // =================================================
                // 8. SMALI → DEX
                // =================================================

                buildNewDex = new File( buildWorkDir, "new-" + currentDexName ) ;

                updateBuildStatus( "6/8 → Recompilando DEX..." ) ;

                smaliManager.assemble( buildSmaliDir, buildNewDex ) ;

                // =================================================
                // 9. RECONSTRUIR APK
                // =================================================

                buildUnsignedApk = new File( buildWorkDir, "unsigned.apk" ) ;

                ApkBuilder apkBuilder = new ApkBuilder() ;

                updateBuildStatus( "7/8 → Reconstruyendo APK..." ) ;

                apkBuilder.rebuild( originalApk, buildNewDex, currentDexName, buildUnsignedApk ) ;

                // =================================================
                // 10. INYECTAR PROVIDER EN ANDROIDMANIFEST.XML
                // =================================================

                File manifestApk = new File( buildWorkDir, "manifest.apk" ) ;

                updateBuildStatus( "8/8 → Actualizando AndroidManifest..." ) ;

                ManifestEditor.injectProvider( buildUnsignedApk, manifestApk, targetPackage,
                        targetPackage + ".VipPrefProvider", targetPackage + ".vip.provider" ) ;

                if ( buildUnsignedApk.exists() && !buildUnsignedApk.delete() ) {
                    throw new Exception( "No se pudo reemplazar el APK temporal." ) ;
                }

                if ( !manifestApk.renameTo( buildUnsignedApk ) ) {
                    throw new Exception(
                            "No se pudo finalizar el APK con el manifest modificado." ) ;
                }

                // =================================================
                // TERMINADO
                // =================================================

                runOnUiThread( () -> {

                    if ( hasAutomaticSigningConfig() ) {

                        statusText.setText(
                                "🔐 APK reconstruido.\n\n" + "Firmando automáticamente..." ) ;

                        // IMPORTANTE:
                        // Ya no aparece el diálogo de firma.
                        // Firma directamente.
                        signAutomatically() ;

                    } else {

                        statusText.setText(
                                "✅ APK reconstruido.\n\n" + "Configura la firma para continuar." ) ;

                        showBuildFinishedDialog( buildUnsignedApk ) ;
                    }
                } ) ;

            } catch ( Exception e ) {

                final String error = getErrorMessage( e ) ;

                runOnUiThread( () -> statusText.setText( "❌ ERROR DE COMPILACIÓN:\n\n" + error ) ) ;
            }

        } ).start() ;
    }

    // =========================================================
    // BUSCAR ARCHIVO SMALI
    // =========================================================

    private File findSmaliClassFile( File smaliDirectory, String className ) {

        if ( smaliDirectory == null || className == null ) {

            return null ;
        }

        String value = className ;

        if ( value.startsWith( "L" ) ) {

            value = value.substring( 1 ) ;
        }

        if ( value.endsWith( ";" ) ) {

            value = value.substring( 0, value.length() - 1 ) ;
        }

        String expectedPath = value + ".smali" ;

        return findFileRecursive( smaliDirectory, expectedPath ) ;
    }

    private File findFileRecursive( File directory, String expectedPath ) {

        if ( directory == null || !directory.exists() ) {

            return null ;
        }

        File[] files = directory.listFiles() ;

        if ( files == null ) {
            return null ;
        }

        for ( File file : files ) {

            if ( file == null ) {
                continue ;
            }

            if ( file.isDirectory() ) {

                File result = findFileRecursive( file, expectedPath ) ;

                if ( result != null ) {
                    return result ;
                }

            } else {

                String absolute = file.getAbsolutePath() ;

                String normalized = absolute.replace( File.separatorChar, '/' ) ;

                if ( normalized.endsWith( "/" + expectedPath ) ) {

                    return file ;
                }
            }
        }

        return null ;
    }

    // =========================================================
    // REEMPLAZAR MÉTODO
    // =========================================================

    private String replaceMethodInClass( String classSmali, String newMethodSmali, Method method )
            throws Exception {

        if ( classSmali == null || classSmali.trim().isEmpty() ) {

            throw new Exception( "La clase Smali está vacía." ) ;
        }

        if ( newMethodSmali == null || newMethodSmali.trim().isEmpty() ) {

            throw new Exception( "El método modificado está vacío." ) ;
        }

        if ( method == null ) {

            throw new Exception( "Método inválido." ) ;
        }

        String methodName = method.getName() ;

        String methodPrototype = buildMethodSignature( method ) ;

        String target = methodName + methodPrototype ;

        String[] lines = classSmali.split( "\\r?\\n", -1 ) ;

        StringBuilder result = new StringBuilder() ;

        boolean insideMethod = false ;
        boolean found = false ;

        for ( int i = 0; i < lines.length; i++ ) {

            String line = lines[i] ;

            String trimmed = line.trim() ;

            if ( !insideMethod && trimmed.startsWith( ".method" ) ) {

                String declaration = trimmed.substring( ".method".length() ).trim() ;

                if ( methodDeclarationMatches( declaration, methodPrototype, methodName ) ) {

                    found = true ;
                    insideMethod = true ;

                    String replacement = normalizeMethodText( newMethodSmali ) ;

                    result.append( replacement ) ;

                    if ( !replacement.endsWith( "\n" ) ) {

                        result.append( "\n" ) ;
                    }

                    continue ;
                }
            }

            if ( insideMethod ) {

                if ( trimmed.equals( ".end method" ) ) {

                    insideMethod = false ;
                }

                continue ;
            }

            result.append( line ) ;

            if ( i < lines.length - 1 ) {

                result.append( "\n" ) ;
            }
        }

        if ( !found ) {

            throw new Exception( "No se encontró el método.\n\n" + "Nombre: " + methodName + "\n\n"
                    + "Prototipo: " + methodPrototype + "\n\n" + "Buscado exactamente:\n"
                    + target ) ;
        }

        return result.toString() ;
    }

    // =========================================================
    // COMPROBAR DECLARACIÓN
    // =========================================================

    private boolean methodDeclarationMatches( String declaration, String prototype,
            String methodName ) {

        if ( declaration == null || prototype == null || methodName == null ) {

            return false ;
        }

        String normalized = declaration.trim().replace( "\t", " " ).replaceAll( "\\s+", " " ) ;

        String target = methodName + prototype ;

        if ( normalized.contains( target ) ) {

            return true ;
        }

        String compact = normalized.replace( " ", "" ) ;

        if ( compact.contains( target ) ) {

            return true ;
        }

        int namePosition = normalized.indexOf( methodName ) ;

        if ( namePosition >= 0 ) {

            String afterName = normalized.substring( namePosition ) ;

            if ( afterName.startsWith( target ) ) {

                return true ;
            }
        }

        return false ;
    }

    // =========================================================
    // NORMALIZAR SMALI
    // =========================================================

    private String normalizeMethodText( String value ) {

        if ( value == null ) {
            return "" ;
        }

        String result = value.replace( "\r\n", "\n" ).replace( "\r", "\n" ).trim() ;

        if ( !result.startsWith( ".method" ) ) {

            throw new IllegalArgumentException( "El contenido debe comenzar con .method" ) ;
        }

        if ( !result.contains( ".end method" ) ) {

            throw new IllegalArgumentException( "El contenido no contiene .end method" ) ;
        }

        return result ;
    }

    // =========================================================
    // LEER TEXTO
    // =========================================================

    private String readTextFile( File file ) throws Exception {

        if ( file == null || !file.exists() ) {

            throw new Exception( "Archivo no encontrado." ) ;
        }

        StringBuilder builder = new StringBuilder() ;

        BufferedReader reader = new BufferedReader(
                new InputStreamReader( new FileInputStream( file ), "UTF-8" ) ) ;

        try {

            String line ;

            while ( ( line = reader.readLine() ) != null ) {

                builder.append( line ) ;
                builder.append( "\n" ) ;
            }

        } finally {

            reader.close() ;
        }

        return builder.toString() ;
    }

    // =========================================================
    // ESCRIBIR TEXTO
    // =========================================================

    private void writeTextFile( File file, String content ) throws Exception {

        if ( file == null ) {

            throw new Exception( "Archivo inválido." ) ;
        }

        File parent = file.getParentFile() ;

        if ( parent != null && !parent.exists() ) {

            parent.mkdirs() ;
        }

        Writer writer = new OutputStreamWriter( new FileOutputStream( file ), "UTF-8" ) ;

        try {

            writer.write( content == null ? "" : content ) ;

        } finally {

            writer.close() ;
        }
    }

    // =========================================================
    // COPIAR APK A CACHE
    // =========================================================

    private File copyApkToCache() throws Exception {

        File file = new File( getCacheDir(), "editor_source.apk" ) ;

        InputStream input = getContentResolver().openInputStream( apkUri ) ;

        if ( input == null ) {

            throw new Exception( "No se pudo abrir el APK." ) ;
        }

        try {

            FileOutputStream output = new FileOutputStream( file ) ;

            try {

                byte[] buffer = new byte[8192] ;

                int read ;

                while ( ( read = input.read( buffer ) ) != -1 ) {

                    output.write( buffer, 0, read ) ;
                }

            } finally {

                output.close() ;
            }

        } finally {

            input.close() ;
        }

        return file ;
    }

    // =========================================================
    // COPIAR URI A ARCHIVO
    // =========================================================

    private void copyUriToFile( Uri source, File destination ) throws Exception {

        if ( source == null ) {

            throw new Exception( "URI de origen inválida." ) ;
        }

        if ( destination == null ) {

            throw new Exception( "Archivo destino inválido." ) ;
        }

        InputStream input = getContentResolver().openInputStream( source ) ;

        if ( input == null ) {

            throw new Exception( "No se pudo abrir el archivo." ) ;
        }

        File parent = destination.getParentFile() ;

        if ( parent != null && !parent.exists() ) {

            parent.mkdirs() ;
        }

        try {

            FileOutputStream output = new FileOutputStream( destination ) ;

            try {

                byte[] buffer = new byte[8192] ;

                int read ;

                while ( ( read = input.read( buffer ) ) != -1 ) {

                    output.write( buffer, 0, read ) ;
                }

            } finally {

                output.close() ;
            }

        } finally {

            input.close() ;
        }
    }

    // =========================================================
    // EXTRAER DEX
    // =========================================================

    private void extractDexFromApk( File apk, String dexName, File outputDex ) throws Exception {

        if ( apk == null || !apk.exists() ) {

            throw new Exception( "APK original no encontrado." ) ;
        }

        ZipFile zip = new ZipFile( apk ) ;

        try {

            ZipEntry entry = zip.getEntry( dexName ) ;

            if ( entry == null ) {

                throw new Exception( "No se encontró " + dexName + " dentro del APK." ) ;
            }

            InputStream input = zip.getInputStream( entry ) ;

            try {

                FileOutputStream output = new FileOutputStream( outputDex ) ;

                try {

                    byte[] buffer = new byte[8192] ;

                    int read ;

                    while ( ( read = input.read( buffer ) ) != -1 ) {

                        output.write( buffer, 0, read ) ;
                    }

                } finally {

                    output.close() ;
                }

            } finally {

                input.close() ;
            }

        } finally {

            zip.close() ;
        }
    }

    // =========================================================
    // ESTADO BUILD
    // =========================================================

    private void updateBuildStatus( final String text ) {

        runOnUiThread( () -> statusText.setText( text ) ) ;
    }

    // =========================================================
    // PREFERENCIAS DE FIRMA
    // =========================================================

    private SharedPreferences getSigningPrefs() {

        return getSharedPreferences( PREFS_SIGNING, MODE_PRIVATE ) ;
    }

    private boolean hasAutomaticSigningConfig() {

        SharedPreferences prefs = getSigningPrefs() ;

        String uri = prefs.getString( PREF_KEYSTORE_URI, "" ) ;

        String alias = prefs.getString( PREF_ALIAS, "" ) ;

        String storePassword = prefs.getString( PREF_STORE_PASSWORD, "" ) ;

        String keyPassword = prefs.getString( PREF_KEY_PASSWORD, "" ) ;

        return uri != null && !uri.isEmpty() && alias != null && !alias.isEmpty()
                && storePassword != null && !storePassword.isEmpty() && keyPassword != null
                && !keyPassword.isEmpty() ;
    }

    private void saveSigningConfig( Uri keystoreUri, String alias, String storePassword,
            String keyPassword ) {

        if ( keystoreUri == null ) {
            return ;
        }

        getSigningPrefs().edit().putString( PREF_KEYSTORE_URI, keystoreUri.toString() )
                .putString( PREF_ALIAS, alias ).putString( PREF_STORE_PASSWORD, storePassword )
                .putString( PREF_KEY_PASSWORD, keyPassword ).apply() ;
    }

    private void clearSigningConfig() {

        getSigningPrefs().edit().clear().apply() ;
    }

    private Uri getSavedKeystoreUri() {

        String value = getSigningPrefs().getString( PREF_KEYSTORE_URI, "" ) ;

        if ( value == null || value.isEmpty() ) {

            return null ;
        }

        try {

            return Uri.parse( value ) ;

        } catch ( Exception e ) {

            return null ;
        }
    }

    // =========================================================
    // APK RECONSTRUIDO
    // =========================================================

    private void showBuildFinishedDialog( File unsignedApk ) {

        if ( unsignedApk == null || !unsignedApk.exists() ) {

            Toast.makeText( this, "❌ APK no encontrado.", Toast.LENGTH_LONG ).show() ;

            return ;
        }

        if ( hasAutomaticSigningConfig() ) {

            signAutomatically() ;

            return ;
        }

        new AlertDialog.Builder( this ).setTitle( "🔐 CONFIGURAR FIRMA" )
                .setMessage( "Todavía no hay una firma configurada.\n\n"
                        + "Configúrala una vez y quedará guardada "
                        + "para las próximas compilaciones." )
                .setPositiveButton( "CONFIGURAR", ( dialog, which ) -> chooseKeystore() )
                .setNeutralButton( "GUARDAR SIN FIRMA", ( dialog, which ) -> saveUnsignedApk() )
                .setNegativeButton( "CERRAR", null ).show() ;
    }

    // =========================================================
    // FIRMA AUTOMÁTICA
    // =========================================================

    private void signAutomatically() {

        if ( !hasAutomaticSigningConfig() ) {

            Toast.makeText( this, "❌ No hay configuración de firma guardada.", Toast.LENGTH_LONG )
                    .show() ;

            chooseKeystore() ;

            return ;
        }

        Uri keystoreUri = getSavedKeystoreUri() ;

        if ( keystoreUri == null ) {

            Toast.makeText( this, "❌ No se encontró el keystore guardado.", Toast.LENGTH_LONG )
                    .show() ;

            chooseKeystore() ;

            return ;
        }

        SharedPreferences prefs = getSigningPrefs() ;

        pendingAlias = prefs.getString( PREF_ALIAS, "" ) ;

        pendingKeystorePassword = prefs.getString( PREF_STORE_PASSWORD, "" ) ;

        pendingKeyPassword = prefs.getString( PREF_KEY_PASSWORD, "" ) ;

        if ( pendingKeyPassword == null || pendingKeyPassword.isEmpty() ) {

            pendingKeyPassword = pendingKeystorePassword ;
        }

        statusText.setText( "🔐 Firmando automáticamente..." ) ;

        signBuiltApk( keystoreUri ) ;
    }

    // =========================================================
    // ELEGIR KEYSTORE
    // =========================================================

    private void chooseKeystore() {

        Intent intent = new Intent( Intent.ACTION_OPEN_DOCUMENT ) ;

        intent.addCategory( Intent.CATEGORY_OPENABLE ) ;

        intent.setType( "*/*" ) ;

        intent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION ) ;

        startActivityForResult( intent, REQUEST_KEYSTORE ) ;
    }

    // =========================================================
    // GUARDAR APK SIN FIRMA
    // =========================================================

    private void saveUnsignedApk() {

        if ( buildUnsignedApk == null || !buildUnsignedApk.exists() ) {

            Toast.makeText( this, "❌ APK sin firma no encontrado.", Toast.LENGTH_LONG ).show() ;

            return ;
        }

        pendingSaveApk = buildUnsignedApk ;

        Intent intent = new Intent( Intent.ACTION_CREATE_DOCUMENT ) ;

        intent.addCategory( Intent.CATEGORY_OPENABLE ) ;

        intent.setType( "application/vnd.android.package-archive" ) ;

        intent.putExtra( Intent.EXTRA_TITLE, "modificado_unsigned.apk" ) ;

        startActivityForResult( intent, REQUEST_SAVE_APK ) ;
    }

    // =========================================================
    // KEYSTORE SELECCIONADO
    // =========================================================

    private void showKeystoreDialog( Uri uri ) {

        if ( uri == null ) {

            Toast.makeText( this, "❌ Keystore inválido.", Toast.LENGTH_LONG ).show() ;

            return ;
        }

        selectedKeystoreUri = uri ;

        LinearLayout layout = new LinearLayout( this ) ;

        layout.setOrientation( LinearLayout.VERTICAL ) ;

        layout.setPadding( dp( 20 ), dp( 10 ), dp( 20 ), dp( 10 ) ) ;

        EditText alias = new EditText( this ) ;

        alias.setHint( "Alias de la clave" ) ;

        alias.setSingleLine( true ) ;

        EditText oldAlias = null ;

        try {

            oldAlias = alias ;

            String savedAlias = getSigningPrefs().getString( PREF_ALIAS, "" ) ;

            if ( savedAlias != null && !savedAlias.isEmpty() ) {

                alias.setText( savedAlias ) ;
            }

        } catch ( Exception ignored ) {
        }

        layout.addView( alias, params( -1, -2 ) ) ;

        EditText storePassword = new EditText( this ) ;

        storePassword.setHint( "Contraseña del keystore" ) ;

        storePassword.setSingleLine( true ) ;

        storePassword.setInputType(
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD ) ;

        String savedStorePassword = getSigningPrefs().getString( PREF_STORE_PASSWORD, "" ) ;

        if ( savedStorePassword != null && !savedStorePassword.isEmpty() ) {

            storePassword.setText( savedStorePassword ) ;
        }

        layout.addView( storePassword, marginParams( -1, -2, 0, 10, 0, 0 ) ) ;

        EditText keyPassword = new EditText( this ) ;

        keyPassword.setHint( "Contraseña de la clave" ) ;

        keyPassword.setSingleLine( true ) ;

        keyPassword.setInputType(
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD ) ;

        String savedKeyPassword = getSigningPrefs().getString( PREF_KEY_PASSWORD, "" ) ;

        if ( savedKeyPassword != null && !savedKeyPassword.isEmpty() ) {

            keyPassword.setText( savedKeyPassword ) ;
        }

        layout.addView( keyPassword, marginParams( -1, -2, 0, 10, 0, 0 ) ) ;

        new AlertDialog.Builder( this ).setTitle( "🔐 DATOS DEL KEYSTORE" )
                .setMessage( "Introduce los datos de tu firma.\n\n"
                        + "Estos datos se guardarán para que "
                        + "las próximas compilaciones se firmen " + "automáticamente." )
                .setView( layout ).setPositiveButton( "FIRMAR", ( dialog, which ) -> {

                    String aliasText = alias.getText().toString().trim() ;

                    String storePass = storePassword.getText().toString() ;

                    String keyPass = keyPassword.getText().toString() ;

                    if ( aliasText.isEmpty() ) {

                        Toast.makeText( this, "❌ Alias vacío.", Toast.LENGTH_SHORT ).show() ;

                        return ;
                    }

                    if ( storePass.isEmpty() ) {

                        Toast.makeText( this, "❌ Contraseña del keystore vacía.",
                                Toast.LENGTH_SHORT ).show() ;

                        return ;
                    }

                    // Si ambas claves usan la misma contraseña
                    // se puede dejar vacía la contraseña individual.
                    if ( keyPass.isEmpty() ) {

                        keyPass = storePass ;
                    }

                    pendingAlias = aliasText ;

                    pendingKeystorePassword = storePass ;

                    pendingKeyPassword = keyPass ;

                    signBuiltApk( selectedKeystoreUri ) ;
                } ).setNegativeButton( "CANCELAR", null ).show() ;
    }

    // =========================================================
    // FIRMAR APK
    // =========================================================

    private void signBuiltApk( Uri keystoreUri ) {

        if ( buildUnsignedApk == null || !buildUnsignedApk.exists() ) {

            Toast.makeText( this, "❌ APK sin firma no encontrado.", Toast.LENGTH_LONG ).show() ;

            return ;
        }

        if ( keystoreUri == null ) {

            Toast.makeText( this, "❌ Keystore no seleccionado.", Toast.LENGTH_LONG ).show() ;

            return ;
        }

        if ( pendingAlias == null || pendingAlias.trim().isEmpty() ) {

            Toast.makeText( this, "❌ Alias de firma vacío.", Toast.LENGTH_LONG ).show() ;

            return ;
        }

        if ( pendingKeystorePassword == null || pendingKeystorePassword.isEmpty() ) {

            Toast.makeText( this, "❌ Contraseña del keystore vacía.", Toast.LENGTH_LONG ).show() ;

            return ;
        }

        if ( pendingKeyPassword == null || pendingKeyPassword.isEmpty() ) {

            pendingKeyPassword = pendingKeystorePassword ;
        }

        new Thread( () -> {

            try {

                if ( buildWorkDir == null ) {

                    buildWorkDir = new File( getCacheDir(), "apkeditor_build" ) ;

                    if ( !buildWorkDir.exists() ) {
                        buildWorkDir.mkdirs() ;
                    }
                }

                updateBuildStatus( "🔐 Preparando firma..." ) ;

                // =================================================
                // COPIAR KEYSTORE AL WORKSPACE
                // =================================================

                File keystoreFile = new File( buildWorkDir, "signing.keystore" ) ;

                if ( keystoreFile.exists() ) {
                    keystoreFile.delete() ;
                }

                copyUriToFile( keystoreUri, keystoreFile ) ;

                if ( !keystoreFile.exists() || keystoreFile.length() == 0 ) {

                    throw new Exception( "El keystore copiado está vacío." ) ;
                }

                // =================================================
                // APK FIRMADO
                // =================================================

                buildSignedApk = new File( buildWorkDir, "signed.apk" ) ;

                if ( buildSignedApk.exists() ) {
                    buildSignedApk.delete() ;
                }

                updateBuildStatus( "🔐 Firmando APK..." ) ;

                ApkSigner signer = new ApkSigner() ;

                signer.sign( buildUnsignedApk, buildSignedApk, keystoreFile,
                        pendingKeystorePassword, pendingAlias, pendingKeyPassword ) ;

                // =================================================
                // VERIFICAR RESULTADO
                // =================================================

                if ( !buildSignedApk.exists() || buildSignedApk.length() == 0 ) {

                    throw new IOException( "El APK firmado no fue generado." ) ;
                }

                // =================================================
                // GUARDAR CONFIGURACIÓN
                //
                // SOLO después de que la firma funcionó.
                // =================================================

                saveSigningConfig( keystoreUri, pendingAlias, pendingKeystorePassword,
                        pendingKeyPassword ) ;

                updateBuildStatus( "✅ APK firmado automáticamente." ) ;

                runOnUiThread( () -> showSignedApkDialog() ) ;

            } catch ( Exception e ) {

                final String error = getErrorMessage( e ) ;

                runOnUiThread( () -> {

                    statusText.setText( "❌ ERROR AL FIRMAR:\n\n" + error ) ;

                    new AlertDialog.Builder( this ).setTitle( "❌ ERROR DE FIRMA" )
                            .setMessage( "No se pudo firmar el APK.\n\n" + error + "\n\n"
                                    + "Si el problema es el keystore, "
                                    + "puedes volver a configurarlo." )
                            .setPositiveButton( "CONFIGURAR DE NUEVO", ( dialog, which ) -> {

                                clearSigningConfig() ;

                                chooseKeystore() ;
                            } ).setNegativeButton( "CERRAR", null ).show() ;
                } ) ;
            }

        } ).start() ;
    }

    // =========================================================
    // APK FIRMADO
    // =========================================================

    private void showSignedApkDialog() {

        if ( buildSignedApk == null || !buildSignedApk.exists() ) {

            return ;
        }

        new AlertDialog.Builder( this ).setTitle( "✅ APK FIRMADO" )
                .setMessage( "El APK fue firmado correctamente.\n\n" + "Tamaño: "
                        + buildSignedApk.length() + " bytes.\n\n"
                        + "La configuración de firma quedó guardada.\n\n"
                        + "La próxima vez que compiles, " + "se firmará automáticamente." )
                .setPositiveButton( "GUARDAR APK", ( dialog, which ) -> saveSignedApk() )
                .setNegativeButton( "CERRAR", null ).show() ;
    }

    // =========================================================
    // GUARDAR APK FIRMADO
    // =========================================================

    private void saveSignedApk() {

        if ( buildSignedApk == null || !buildSignedApk.exists() ) {

            Toast.makeText( this, "❌ APK firmado no encontrado.", Toast.LENGTH_LONG ).show() ;

            return ;
        }

        pendingSaveApk = buildSignedApk ;

        Intent intent = new Intent( Intent.ACTION_CREATE_DOCUMENT ) ;

        intent.addCategory( Intent.CATEGORY_OPENABLE ) ;

        intent.setType( "application/vnd.android.package-archive" ) ;

        intent.putExtra( Intent.EXTRA_TITLE, "modificado_signed.apk" ) ;

        startActivityForResult( intent, REQUEST_SAVE_APK ) ;
    }

    // =========================================================
    // RESULTADOS SAF
    // =========================================================

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {

        super.onActivityResult( requestCode, resultCode, data ) ;

        // =====================================================
        // KEYSTORE
        // =====================================================

        if ( requestCode == REQUEST_KEYSTORE ) {

            if ( resultCode != RESULT_OK ) {
                return ;
            }

            if ( data == null || data.getData() == null ) {

                return ;
            }

            Uri uri = data.getData() ;

            // =================================================
            // GUARDAR PERMISO DEL DOCUMENTO
            // =================================================

            try {

                getContentResolver().takePersistableUriPermission( uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION ) ;

            } catch ( Exception ignored ) {
            }

            showKeystoreDialog( uri ) ;

            return ;
        }

        // =====================================================
        // GUARDAR APK
        // =====================================================

        if ( requestCode == REQUEST_SAVE_APK ) {

            if ( resultCode != RESULT_OK ) {
                return ;
            }

            if ( data == null || data.getData() == null ) {

                return ;
            }

            Uri destination = data.getData() ;

            final File source = pendingSaveApk ;

            pendingSaveApk = null ;

            if ( source == null || !source.exists() ) {

                Toast.makeText( this, "❌ No se encontró el APK que se debe guardar.",
                        Toast.LENGTH_LONG ).show() ;

                return ;
            }

            new Thread( () -> {

                try {

                    OutputStream output = getContentResolver().openOutputStream( destination ) ;

                    if ( output == null ) {

                        throw new Exception( "No se pudo abrir el destino." ) ;
                    }

                    InputStream input = new FileInputStream( source ) ;

                    try {

                        byte[] buffer = new byte[8192] ;

                        int read ;

                        while ( ( read = input.read( buffer ) ) != -1 ) {

                            output.write( buffer, 0, read ) ;
                        }

                        output.flush() ;

                    } finally {

                        input.close() ;
                        output.close() ;
                    }

                    runOnUiThread( () -> Toast
                            .makeText( this, "✅ APK guardado correctamente.", Toast.LENGTH_LONG )
                            .show() ) ;

                } catch ( Exception e ) {

                    final String error = getErrorMessage( e ) ;

                    runOnUiThread( () -> Toast
                            .makeText( this, "❌ Error guardando APK:\n" + error, Toast.LENGTH_LONG )
                            .show() ) ;
                }

            } ).start() ;

            return ;
        }

        // =====================================================
        // GUARDAR SMALI
        // =====================================================

        if ( requestCode == REQUEST_SAVE_SMALI ) {

            if ( resultCode != RESULT_OK ) {
                return ;
            }

            if ( data == null || data.getData() == null ) {

                return ;
            }

            Uri uri = data.getData() ;

            try {

                OutputStream output = getContentResolver().openOutputStream( uri ) ;

                if ( output == null ) {

                    throw new Exception( "No se pudo abrir el archivo de destino." ) ;
                }

                try {

                    output.write( pendingSmaliContent.getBytes( "UTF-8" ) ) ;

                    output.flush() ;

                } finally {

                    output.close() ;
                }

                Toast.makeText( this, "✓ Archivo .smali guardado.", Toast.LENGTH_LONG ).show() ;

            } catch ( Exception e ) {

                Toast.makeText( this, "❌ Error guardando:\n" + getErrorMessage( e ),
                        Toast.LENGTH_LONG ).show() ;
            }

            return ;
        }
    }

    // =========================================================
    // GUARDAR SMALI
    // =========================================================

    private void saveSmaliFile( String content, String methodName ) {

        if ( content == null ) {
            content = "" ;
        }

        String safeName = methodName ;

        if ( safeName == null || safeName.trim().isEmpty() ) {

            safeName = "method" ;
        }

        safeName = safeName.replaceAll( "[^a-zA-Z0-9_$.-]", "_" ) ;

        Intent intent = new Intent( Intent.ACTION_CREATE_DOCUMENT ) ;

        intent.addCategory( Intent.CATEGORY_OPENABLE ) ;

        intent.setType( "text/plain" ) ;

        intent.putExtra( Intent.EXTRA_TITLE, safeName + ".smali" ) ;

        pendingSmaliContent = content ;

        startActivityForResult( intent, REQUEST_SAVE_SMALI ) ;
    }

    // =========================================================
    // BÚSQUEDA
    // =========================================================

    private void showSearchDialog() {

        if ( apkUri == null ) {

            Toast.makeText( this, "❌ No hay APK seleccionado.", Toast.LENGTH_SHORT ).show() ;

            return ;
        }

        final EditText input = new EditText( this ) ;

        input.setHint( "Ejemplo: isVip" ) ;

        input.setSingleLine( true ) ;

        new AlertDialog.Builder( this ).setTitle( "BUSCAR EN APK" )
                .setMessage( "Busca clases, métodos o campos." ).setView( input )
                .setPositiveButton( "BUSCAR", ( dialog, which ) -> {

                    String query = input.getText().toString().trim() ;

                    if ( query.isEmpty() ) {

                        Toast.makeText( this, "Introduce algo.", Toast.LENGTH_SHORT ).show() ;

                        return ;
                    }

                    searchInApk( query ) ;
                } ).setNegativeButton( "CANCELAR", null ).show() ;
    }

    private void searchInApk( String query ) {

        statusText.setText( "🔎 Buscando: " + query + "..." ) ;

        new Thread( () -> {

            File apk = null ;

            try {

                apk = copyApkToCache() ;

                ArrayList<String> matches = new ArrayList<String>() ;

                String q = query.toLowerCase( Locale.ROOT ) ;

                ZipFile zip = new ZipFile( apk ) ;

                try {

                    java.util.Enumeration<? extends ZipEntry> entries = zip.entries() ;

                    while ( entries.hasMoreElements() ) {

                        ZipEntry entry = entries.nextElement() ;

                        String name = entry.getName() ;

                        if ( !name.matches( "classes(\\d*)?\\.dex" ) ) {

                            continue ;
                        }

                        File tempDex = new File( getCacheDir(), "search_" + name ) ;

                        InputStream input = zip.getInputStream( entry ) ;

                        try {

                            FileOutputStream output = new FileOutputStream( tempDex ) ;

                            try {

                                byte[] buffer = new byte[8192] ;

                                int read ;

                                while ( ( read = input.read( buffer ) ) != -1 ) {

                                    output.write( buffer, 0, read ) ;
                                }

                            } finally {

                                output.close() ;
                            }

                        } finally {

                            input.close() ;
                        }

                        try {

                            DexFile dex = DexFileFactory.loadDexFile( tempDex, null ) ;

                            for ( ClassDef cls : dex.getClasses() ) {

                                if ( cls == null ) {
                                    continue ;
                                }

                                String className = cls.getType() ;

                                if ( className != null
                                        && className.toLowerCase( Locale.ROOT ).contains( q ) ) {

                                    matches.add( name + "\nCLASE: " + className ) ;
                                }

                                for ( Field field : cls.getFields() ) {

                                    if ( field == null ) {
                                        continue ;
                                    }

                                    String fieldName = field.getName() ;

                                    if ( fieldName != null && fieldName.toLowerCase( Locale.ROOT )
                                            .contains( q ) ) {

                                        matches.add( name + "\nCAMPO: " + className + " -> "
                                                + fieldName ) ;
                                    }
                                }

                                for ( Method method : cls.getMethods() ) {

                                    if ( method == null ) {
                                        continue ;
                                    }

                                    String methodName = method.getName() ;

                                    if ( methodName != null && methodName.toLowerCase( Locale.ROOT )
                                            .contains( q ) ) {

                                        matches.add( name + "\nMÉTODO: " + className + " -> "
                                                + methodName ) ;
                                    }
                                }
                            }

                        } finally {

                            if ( tempDex.exists() ) {
                                tempDex.delete() ;
                            }
                        }
                    }

                } finally {

                    zip.close() ;
                }

                if ( apk.exists() ) {
                    apk.delete() ;
                }

                runOnUiThread( () -> showSearchResults( query, matches ) ) ;

            } catch ( Exception e ) {

                if ( apk != null && apk.exists() ) {

                    apk.delete() ;
                }

                final String error = getErrorMessage( e ) ;

                runOnUiThread( () -> statusText.setText( "❌ Error:\n" + error ) ) ;
            }

        } ).start() ;
    }

    // =========================================================
    // RESULTADOS
    // =========================================================

    // =========================================================
    // RESULTADOS + FILTRO INTERNO
    // =========================================================

    private void showSearchResults( String query, ArrayList<String> matches ) {

        editorContainer.removeAllViews() ;

        statusText.setText( "✓ Búsqueda terminada." ) ;

        TextView title = text( "RESULTADOS\n\n" + "Consulta: " + query, 18 ) ;

        title.setTypeface( android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD ) ;

        editorContainer.addView( title, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;

        // =====================================================
        // SEGUNDO BUSCADOR
        // =====================================================

        EditText filter = new EditText( this ) ;

        filter.setHint( "🔎 Buscar dentro de los resultados..." ) ;

        filter.setSingleLine( true ) ;

        filter.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS ) ;

        filter.setPadding( dp( 12 ), dp( 12 ), dp( 12 ), dp( 12 ) ) ;

        editorContainer.addView( filter, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;

        // =====================================================
        // CONTADOR
        // =====================================================

        final TextView resultCounter = text( "Coincidencias: " + matches.size(), 14 ) ;

        resultCounter.setTextIsSelectable( true ) ;

        editorContainer.addView( resultCounter, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;

        // =====================================================
        // CONTENEDOR DE RESULTADOS
        // =====================================================

        final LinearLayout resultsContainer = new LinearLayout( this ) ;

        resultsContainer.setOrientation( LinearLayout.VERTICAL ) ;

        editorContainer.addView( resultsContainer, new LinearLayout.LayoutParams( -1, -2 ) ) ;

        // =====================================================
        // MOSTRAR RESULTADOS
        // =====================================================

        final Runnable updateResults = new Runnable() {

            @Override
            public void run() {

                String filterText = filter.getText().toString().trim().toLowerCase( Locale.ROOT ) ;

                resultsContainer.removeAllViews() ;

                int count = 0 ;

                for ( String match : matches ) {

                    if ( match == null ) {
                        continue ;
                    }

                    if ( !filterText.isEmpty()
                            && !match.toLowerCase( Locale.ROOT ).contains( filterText ) ) {

                        continue ;
                    }

                    TextView item = text( "━━━━━━━━━━━━━━━━━━\n" + match + "\n", 13 ) ;

                    item.setTextIsSelectable( true ) ;

                    resultsContainer.addView( item, marginParams( -1, -2, 0, 0, 0, 8 ) ) ;

                    count++ ;
                }

                resultCounter.setText( "Mostrando: " + count + " / " + matches.size() ) ;

                if ( count == 0 ) {

                    resultsContainer
                            .addView(
                                    text( filterText.isEmpty() ? "No se encontraron coincidencias."
                                            : "❌ No hay resultados que coincidan con \""
                                                    + filterText + "\".",
                                            14 ),
                                    marginParams( -1, -2, 0, 8, 0, 8 ) ) ;
                }
            }
        } ;

        // Mostrar todos inicialmente
        updateResults.run() ;

        // =====================================================
        // FILTRO EN TIEMPO REAL
        // =====================================================

        filter.addTextChangedListener( new android.text.TextWatcher() {

            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {
            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count ) {

                updateResults.run() ;
            }

            @Override
            public void afterTextChanged( android.text.Editable s ) {
            }
        } ) ;

        createBackToToolsButton() ;
    }

    // =========================================================
    // INFORMACIÓN
    // =========================================================

    private void showApkDetails() {

        if ( apkUri == null ) {
            return ;
        }

        String name = getDisplayName( apkUri ) ;

        new AlertDialog.Builder( this ).setTitle( "INFORMACIÓN DEL APK" )
                .setMessage( "Nombre:\n" + name + "\n\nURI:\n" + apkUri.toString() )
                .setPositiveButton( "CERRAR", null ).show() ;
    }

    // =========================================================
    // VOLVER A HERRAMIENTAS
    // =========================================================

    private void createBackToToolsButton() {

        Button back = new Button( this ) ;

        back.setText( "← VOLVER A HERRAMIENTAS" ) ;

        back.setOnClickListener( v -> createEditorOptions() ) ;

        editorContainer.addView( back, marginParams( -1, -2, 0, 16, 0, 0 ) ) ;
    }

    // =========================================================
    // VOLVER A CLASES
    // =========================================================

    private void createBackToDexButton() {

        Button back = new Button( this ) ;

        back.setText( "← VOLVER A CLASES" ) ;

        back.setOnClickListener( v -> {

            if ( currentDexFile == null || currentDexName == null ) {

                createEditorOptions() ;

                return ;
            }

            new Thread( () -> {

                try {

                    DexFile dex = DexFileFactory.loadDexFile( currentDexFile, null ) ;

                    ArrayList<String> classes = new ArrayList<String>() ;

                    for ( ClassDef cls : dex.getClasses() ) {

                        if ( cls != null ) {

                            classes.add( cls.getType() ) ;
                        }
                    }

                    runOnUiThread(
                            () -> showClassList( currentDexName, classes, currentDexFile ) ) ;

                } catch ( Exception e ) {

                    final String error = getErrorMessage( e ) ;

                    runOnUiThread( () -> statusText.setText( "❌ Error:\n" + error ) ) ;
                }

            } ).start() ;
        } ) ;

        editorContainer.addView( back, marginParams( -1, -2, 0, 16, 0, 0 ) ) ;
    }

    // =========================================================
    // ELIMINAR DIRECTORIO
    // =========================================================

    private void deleteDirectory( File file ) {

        if ( file == null || !file.exists() ) {

            return ;
        }

        if ( file.isDirectory() ) {

            File[] children = file.listFiles() ;

            if ( children != null ) {

                for ( File child : children ) {

                    deleteDirectory( child ) ;
                }
            }
        }

        file.delete() ;
    }

    // =========================================================
    // FLAGS
    // =========================================================

    private String formatAccessFlags( int flags ) {

        StringBuilder result = new StringBuilder() ;

        if ( ( flags & 0x1 ) != 0 )
            result.append( "public " ) ;

        if ( ( flags & 0x2 ) != 0 )
            result.append( "private " ) ;

        if ( ( flags & 0x4 ) != 0 )
            result.append( "protected " ) ;

        if ( ( flags & 0x8 ) != 0 )
            result.append( "static " ) ;

        if ( ( flags & 0x10 ) != 0 )
            result.append( "final " ) ;

        if ( ( flags & 0x20 ) != 0 )
            result.append( "synchronized " ) ;

        if ( ( flags & 0x40 ) != 0 )
            result.append( "bridge " ) ;

        if ( ( flags & 0x80 ) != 0 )
            result.append( "varargs " ) ;

        if ( ( flags & 0x100 ) != 0 )
            result.append( "native " ) ;

        if ( ( flags & 0x400 ) != 0 )
            result.append( "abstract " ) ;

        if ( ( flags & 0x1000 ) != 0 )
            result.append( "synthetic " ) ;

        return result.toString().trim() ;
    }

    // =========================================================
    // FORMATEAR CLASE
    // =========================================================

    private String formatClassName( String className ) {

        if ( className == null ) {
            return "" ;
        }

        String value = className ;

        if ( value.startsWith( "L" ) ) {

            value = value.substring( 1 ) ;
        }

        if ( value.endsWith( ";" ) ) {

            value = value.substring( 0, value.length() - 1 ) ;
        }

        return value.replace( "/", "." ) ;
    }

    // =========================================================
    // PORTAPAPELES
    // =========================================================

    private void copyTextSafely( String label, String value ) {

        try {

            ClipboardManager clipboard = ( ClipboardManager ) getSystemService(
                    Context.CLIPBOARD_SERVICE ) ;

            if ( clipboard == null ) {
                return ;
            }

            ClipData clip = ClipData.newPlainText( label, value ) ;

            clipboard.setPrimaryClip( clip ) ;

            Toast.makeText( this, "✓ Copiado correctamente.", Toast.LENGTH_SHORT ).show() ;

        } catch ( Exception e ) {

            Toast.makeText( this, "❌ No se pudo copiar.", Toast.LENGTH_SHORT ).show() ;
        }
    }

    // =========================================================
    // NOMBRE APK
    // =========================================================

    private String getDisplayName( Uri uri ) {

        if ( uri == null ) {
            return "desconocido" ;
        }

        String name = null ;

        try {

            android.database.Cursor cursor = getContentResolver().query( uri,
                    new String[] { android.provider.OpenableColumns.DISPLAY_NAME }, null, null,
                    null ) ;

            if ( cursor != null ) {

                try {

                    if ( cursor.moveToFirst() ) {

                        int index = cursor
                                .getColumnIndex( android.provider.OpenableColumns.DISPLAY_NAME ) ;

                        if ( index >= 0 ) {

                            name = cursor.getString( index ) ;
                        }
                    }

                } finally {

                    cursor.close() ;
                }
            }

        } catch ( Exception ignored ) {
        }

        if ( name == null || name.trim().isEmpty() ) {

            name = uri.toString() ;
        }

        return name ;
    }

    // =========================================================
    // ERROR
    // =========================================================

    private String getErrorMessage( Exception e ) {

        if ( e == null ) {
            return "Error desconocido." ;
        }

        String message = e.getMessage() ;

        if ( message == null || message.trim().isEmpty() ) {

            return e.toString() ;
        }

        return message ;
    }

    // =========================================================
    // UI
    // =========================================================

    private int dp( float value ) {

        return ( int ) ( value * getResources().getDisplayMetrics().density ) ;
    }

    private LinearLayout.LayoutParams params( int width, int height ) {

        return new LinearLayout.LayoutParams( width, height ) ;
    }

    private LinearLayout.LayoutParams marginParams( int width, int height, int left, int top,
            int right, int bottom ) {

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( width, height ) ;

        p.setMargins( dp( left ), dp( top ), dp( right ), dp( bottom ) ) ;

        return p ;
    }

    private TextView text( String value, float size ) {

        TextView view = new TextView( this ) ;

        view.setText( value ) ;
        view.setTextSize( size ) ;

        return view ;
    }
            }
