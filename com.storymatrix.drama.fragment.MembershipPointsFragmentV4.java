//
// Decompiled by Jadx - 19

// By developer-krushna (https://github.com/developer-krushna/)

package com.storymatrix.drama.fragment;

import G9.O;
import I8.N;
import I8.m;
import I8.x0;
import J9.w;
import Uf.lO;
import W8.V;
import W8.W;
import W8.X;
import W8.Y;
import W8.Z;
import W8.a0;
import W8.b0;
import W8.c0;
import W8.d0;
import W8.e0;
import W8.f0;
import W8.g0;
import W8.h0;
import W8.i0;
import W8.j0;
import W8.k0;
import W8.l0;
import W8.m0;
import W8.n0;
import W8.o0;
import W8.p0;
import W8.q0;
import W8.r0;
import W8.s0;
import W8.t0;
import W8.u0;
import W8.v0;
import W8.w0;
import W8.y0;
import W8.z0;
import W9.I;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lib.data.AcceptTask;
import com.lib.data.BillingParamsInfo;
import com.lib.data.CalendarReminder;
import com.lib.data.OperationActivities;
import com.lib.data.OperationActivity;
import com.lib.data.PurchaseScene;
import com.lib.data.RechargeInfo;
import com.lib.data.RechargePopUp;
import com.lib.data.RechargeUiGroup;
import com.lib.data.ReportInfo;
import com.lib.data.membership.MembershipCardInfo;
import com.lib.data.membership.MembershipChannel;
import com.lib.data.membership.MembershipChoiceInfo;
import com.lib.data.membership.MembershipPrivilegesItem;
import com.lib.data.membership.MembershipReserveInfo;
import com.lib.data.membership.MembershipStatus;
import com.lib.data.membership.PointsBean;
import com.lib.data.membership.PointsChannel;
import com.lib.data.membership.PointsExchangeResult;
import com.lib.data.membership.PointsRedemptionInfo;
import com.lib.data.membership.PointsRedemptionInfoList;
import com.lib.data.membership.PointsRuleInfo;
import com.lib.data.membership.PointsTask;
import com.lib.data.membership.UserLayerInfo;
import com.lib.language.R;
import com.lib.log.XlogUtils;
import com.mbridge.msdk.videocommon.setting.lP.GXechcBTBrmdPe;
import com.storymatrix.drama.activity.GiftCenterActivity;
import com.storymatrix.drama.activity.MainActivity;
import com.storymatrix.drama.adapter.MemberPointsTaskAdapter;
import com.storymatrix.drama.base.BaseActivity;
import com.storymatrix.drama.base.BaseFragment;
import com.storymatrix.drama.base.BaseViewModel;
import com.storymatrix.drama.databinding.FragmentMembershipPointsV3Binding;
import com.storymatrix.drama.dialog.push.PushDialogManager;
import com.storymatrix.drama.dialog.push.PushDialogVM;
import com.storymatrix.drama.fragment.MembershipPointsFragmentV4$initViewObservable$.inlined.collectFlow.default;
import com.storymatrix.drama.fragment.MembershipPointsFragmentV4$special$.inlined.viewModels.default.1;
import com.storymatrix.drama.fragment.MembershipPointsFragmentV4$special$.inlined.viewModels.default.2;
import com.storymatrix.drama.fragment.MembershipPointsFragmentV4$special$.inlined.viewModels.default.3;
import com.storymatrix.drama.fragment.MembershipPointsFragmentV4$special$.inlined.viewModels.default.4;
import com.storymatrix.drama.fragment.MembershipPointsFragmentV4$special$.inlined.viewModels.default.5;
import com.storymatrix.drama.fragment.MembershipPointsFragmentV4$special$.inlined.viewModels.default.6;
import com.storymatrix.drama.fragment.MembershipPointsFragmentV4$special$.inlined.viewModels.default.8;
import com.storymatrix.drama.log.SensorLog;
import com.storymatrix.drama.membership.view.PrivilegesStyle;
import com.storymatrix.drama.utils.JumpUtils;
import com.storymatrix.drama.utils.LifecycleUtilKt;
import com.storymatrix.drama.utils.RechargeUtils;
import com.storymatrix.drama.utils.TaskReportUtil;
import com.storymatrix.drama.utils.ViewExtKt;
import com.storymatrix.drama.utils.calendar.ReserveCalendarDelegate;
import com.storymatrix.drama.utils.calendar.SignInCalendarDelegate;
import com.storymatrix.drama.view.DramaTextView;
import com.storymatrix.drama.view.RoundImageView;
import com.storymatrix.drama.view.itemdecoration.DividerItemDecoration;
import com.storymatrix.drama.view.membership.MemberChoiceComponentV3;
import com.storymatrix.drama.view.membership.PointsRedemptionComponent.dramabox;
import com.storymatrix.drama.view.membership.PointsRedemptionComponentV3;
import com.storymatrix.drama.view.rollingtextview.RollingTextView;
import com.storymatrix.drama.view.rollingtextview.strategy.Direction;
import com.storymatrix.drama.viewmodel.MembershipPointsVM;
import com.storymatrix.drama.viewmodel.TpVm;
import com.storymatrix.framework.rxbus.BusEvent;
import com.storymatrix.framework.rxbus.RxBus;
import com.tracklog.annotation.Page;
import e9.l1;
import j9.dramaboxapp;
import j9.yhj;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.l;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.SharedFlow;
import x9.implements;
import x9.lo;
import x9.swe;
import x9.swr;

@Metadata
@Page(name = "new_member_awards")
public final class MembershipPointsFragmentV4 extends Hilt_MembershipPointsFragmentV4<FragmentMembershipPointsV3Binding, MembershipPointsVM> implements dramabox, dramaboxapp, ReserveCalendarDelegate.dramabox, SignInCalendarDelegate.dramabox {
    private int channel = PointsChannel.SelectTab.ordinal();
    private boolean isFirstRequest;
    private boolean isStickyButtonEnabled;
    private final O performanceDelegate;
    private PointsBean pointsBean;
    private m pointsRedemptionDialog;
    private PushDialogManager pushDialogManager;
    private final lO pushDialogVM$delegate;
    private String pushTaskId = "";
    private j9.O rechargeDialog;
    private final ReserveCalendarDelegate reserveCalendarDelegate;
    private N rewardsRulesDialog;
    private final SignInCalendarDelegate signInCalendarDelegate;
    public k9.dramaboxapp skuLocalCurrency;
    private x0 subFailCouponDialog;
    private final lO taskAdapter$delegate;
    public e9.O tpPaymentAdapter;
    private final lO tpVm$delegate;

    public MembershipPointsFragmentV4() {
        1 1 = new 1(this);
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        lO dramabox = kotlin.dramaboxapp.dramabox(lazyThreadSafetyMode, new 2(1));
        this.tpVm$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(TpVm.class), new 3(dramabox), new 4(null, dramabox), new 5(this, dramabox));
        dramabox = kotlin.dramaboxapp.dramabox(lazyThreadSafetyMode, new 2(new 6(this)));
        this.pushDialogVM$delegate = FragmentViewModelLazyKt.createViewModelLazy(this, Reflection.getOrCreateKotlinClass(PushDialogVM.class), new 8(dramabox), new 4(null, dramabox), new 5(this, dramabox));
        this.taskAdapter$delegate = kotlin.dramaboxapp.dramaboxapp(new q0());
        this.performanceDelegate = new O("membership_points_v4", false, 2, null);
        this.signInCalendarDelegate = new SignInCalendarDelegate(this);
        this.reserveCalendarDelegate = new ReserveCalendarDelegate(this);
        this.isFirstRequest = true;
    }

    private final void buttonClickSub(String str) {
        SensorLog.tyu(SensorLog.dramaboxapp.O(), getPageAlias(), null, null, str, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, -10, -1, 1, null);
        if (Z6.dramabox.dramabox.d0() == 1) {
            getRechargeList();
        } else {
            JumpUtils.JKi(JumpUtils.dramabox, getContext(), MembershipChannel.Points.getChannel(), null, null, null, null, null, null, false, 508, null);
        }
    }

    public static /* synthetic */ void buttonClickSub$default(MembershipPointsFragmentV4 membershipPointsFragmentV4, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "积分页面激活会员";
        }
        membershipPointsFragmentV4.buttonClickSub(str);
    }

    private final void checkStickyButtonVisibility() {
        if (Z6.dramabox.dramabox.d0() != 1) {
            if (this.isStickyButtonEnabled) {
                if (((FragmentMembershipPointsV3Binding) this.mBinding).final.getGlobalVisibleRect(new Rect())) {
                    if (((FragmentMembershipPointsV3Binding) this.mBinding).I.getVisibility() != 8) {
                        ((FragmentMembershipPointsV3Binding) this.mBinding).I.setVisibility(8);
                    }
                } else if (((FragmentMembershipPointsV3Binding) this.mBinding).I.getVisibility() != 0) {
                    ((FragmentMembershipPointsV3Binding) this.mBinding).I.setVisibility(0);
                }
                return;
            }
            ((FragmentMembershipPointsV3Binding) this.mBinding).I.setVisibility(8);
        }
    }

    private final void createMembershipChoice(MembershipChoiceInfo membershipChoiceInfo) {
        Collection bookSimpleVoList = membershipChoiceInfo.getBookSimpleVoList();
        if (bookSimpleVoList == null) {
            return;
        }
        if (!bookSimpleVoList.isEmpty()) {
            MemberChoiceComponentV3.l1(((FragmentMembershipPointsV3Binding) this.mBinding).pos, getPageAlias(), membershipChoiceInfo, new dramabox(membershipChoiceInfo, this), false, 8, null);
            ((FragmentMembershipPointsV3Binding) this.mBinding).pos.setVisibility(0);
        }
    }

    private final void createMembershipPrivileges(List<MembershipPrivilegesItem> list) {
        ((FragmentMembershipPointsV3Binding) this.mBinding).slo.lO(list, this.mActivity.getString(R.string.str_points_privilege_title), PrivilegesStyle.POINTS_NOT_MEMBER.ordinal(), true);
        ((FragmentMembershipPointsV3Binding) this.mBinding).slo.setVisibility(0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00eb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void createPointsRedemption(PointsRedemptionInfoList pointsRedemptionInfoList, MembershipCardInfo membershipCardInfo) {
        Collection pointsRedemptionInfoItemList = pointsRedemptionInfoList.getPointsRedemptionInfoItemList();
        if (pointsRedemptionInfoItemList == null) {
            return;
        }
        if (!pointsRedemptionInfoItemList.isEmpty()) {
            int intValue;
            int intValue2;
            Integer points;
            String str;
            LayoutParams layoutParams;
            ((FragmentMembershipPointsV3Binding) this.mBinding).pop.I(this);
            ((FragmentMembershipPointsV3Binding) this.mBinding).pop.setOnRuleClickListener(new a0(this));
            PointsRedemptionInfoList pointsRedemptionInfoList2 = pointsRedemptionInfoList;
            MembershipCardInfo membershipCardInfo2 = membershipCardInfo;
            PointsRedemptionComponentV3.RT(((FragmentMembershipPointsV3Binding) this.mBinding).pop, getPageAlias(), pointsRedemptionInfoList2, membershipCardInfo2, false, false, null, true, true, false, 296, null);
            ((FragmentMembershipPointsV3Binding) this.mBinding).jkk.I(this);
            ((FragmentMembershipPointsV3Binding) this.mBinding).jkk.setOnRuleClickListener(new b0(this));
            PointsRedemptionComponentV3.RT(((FragmentMembershipPointsV3Binding) this.mBinding).jkk, getPageAlias(), pointsRedemptionInfoList2, membershipCardInfo2, false, false, null, true, false, false, 304, null);
            List pointsRedemptionInfoItemList2 = pointsRedemptionInfoList.getPointsRedemptionInfoItemList();
            if (pointsRedemptionInfoItemList2 != null) {
                Object obj;
                Iterator it = pointsRedemptionInfoItemList2.iterator();
                if (it.hasNext()) {
                    Object next = it.next();
                    if (it.hasNext()) {
                        Integer changePoints = ((PointsRedemptionInfo) next).getChangePoints();
                        intValue = changePoints != null ? changePoints.intValue() : Integer.MAX_VALUE;
                        do {
                            Object next2 = it.next();
                            Integer changePoints2 = ((PointsRedemptionInfo) next2).getChangePoints();
                            int intValue3 = changePoints2 != null ? changePoints2.intValue() : Integer.MAX_VALUE;
                            if (intValue > intValue3) {
                                next = next2;
                                intValue = intValue3;
                            }
                        } while (it.hasNext());
                    }
                    obj = next;
                } else {
                    obj = null;
                }
                PointsRedemptionInfo pointsRedemptionInfo = (PointsRedemptionInfo) obj;
                if (pointsRedemptionInfo != null) {
                    Integer changePoints3 = pointsRedemptionInfo.getChangePoints();
                    if (changePoints3 != null) {
                        intValue2 = changePoints3.intValue();
                        if (intValue2 != Integer.MAX_VALUE) {
                            points = membershipCardInfo.getPoints();
                            if ((points != null ? points.intValue() : 0) >= intValue2) {
                                intValue2 = 1;
                                points = membershipCardInfo.getMembershipStatus();
                                intValue = MembershipStatus.PaidMem.ordinal();
                                str = "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams";
                                if (points != null) {
                                    if (points.intValue() == intValue) {
                                        Integer points2 = membershipCardInfo.getPoints();
                                        if ((points2 != null ? points2.intValue() : 0) >= 1000 || r14 != 0) {
                                            ((FragmentMembershipPointsV3Binding) this.mBinding).pop.setVisibility(0);
                                            layoutParams = ((FragmentMembershipPointsV3Binding) this.mBinding).l.getLayoutParams();
                                            Intrinsics.checkNotNull(layoutParams, str);
                                            ((LinearLayout.LayoutParams) layoutParams).topMargin = swe.dramaboxapp(8);
                                            ((FragmentMembershipPointsV3Binding) this.mBinding).jkk.setVisibility(8);
                                            return;
                                        }
                                    }
                                }
                                ((FragmentMembershipPointsV3Binding) this.mBinding).pop.setVisibility(8);
                                layoutParams = ((FragmentMembershipPointsV3Binding) this.mBinding).l.getLayoutParams();
                                Intrinsics.checkNotNull(layoutParams, str);
                                ((LinearLayout.LayoutParams) layoutParams).topMargin = swe.dramaboxapp(20);
                                ((FragmentMembershipPointsV3Binding) this.mBinding).jkk.setVisibility(0);
                            }
                        }
                        intValue2 = 0;
                        points = membershipCardInfo.getMembershipStatus();
                        intValue = MembershipStatus.PaidMem.ordinal();
                        str = "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams";
                        if (points != null) {
                        }
                        ((FragmentMembershipPointsV3Binding) this.mBinding).pop.setVisibility(8);
                        layoutParams = ((FragmentMembershipPointsV3Binding) this.mBinding).l.getLayoutParams();
                        Intrinsics.checkNotNull(layoutParams, str);
                        ((LinearLayout.LayoutParams) layoutParams).topMargin = swe.dramaboxapp(20);
                        ((FragmentMembershipPointsV3Binding) this.mBinding).jkk.setVisibility(0);
                    }
                }
            }
            intValue2 = Integer.MAX_VALUE;
            if (intValue2 != Integer.MAX_VALUE) {
            }
            intValue2 = 0;
            points = membershipCardInfo.getMembershipStatus();
            intValue = MembershipStatus.PaidMem.ordinal();
            str = "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams";
            if (points != null) {
            }
            ((FragmentMembershipPointsV3Binding) this.mBinding).pop.setVisibility(8);
            layoutParams = ((FragmentMembershipPointsV3Binding) this.mBinding).l.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, str);
            ((LinearLayout.LayoutParams) layoutParams).topMargin = swe.dramaboxapp(20);
            ((FragmentMembershipPointsV3Binding) this.mBinding).jkk.setVisibility(0);
        }
    }

    private static final Unit createPointsRedemption$lambda$23(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        membershipPointsFragmentV4.showRuleDialog();
        return Unit.dramabox;
    }

    private static final Unit createPointsRedemption$lambda$24(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        membershipPointsFragmentV4.showRuleDialog();
        return Unit.dramabox;
    }

    private final void createReserveView(MembershipReserveInfo membershipReserveInfo) {
        Collection bookSimpleVoList = membershipReserveInfo.getBookSimpleVoList();
        if (bookSimpleVoList == null) {
            return;
        }
        if (!bookSimpleVoList.isEmpty()) {
            ((FragmentMembershipPointsV3Binding) this.mBinding).skn.lO(getPageAlias(), membershipReserveInfo, Integer.valueOf(swe.dramaboxapp(8)), new 1(this), new dramaboxapp(this), true);
            ((FragmentMembershipPointsV3Binding) this.mBinding).skn.setVisibility(0);
        }
    }

    private final void dismissRechargeDialog() {
        j9.O o = this.rechargeDialog;
        if (!(o == null || o == null || !o.dramabox() || !isResumed() || this.mActivity.isDestroyed() || this.mActivity.isFinishing())) {
            this.mActivity.runOnUiThread(new s0(this));
        }
        this.rechargeDialog = null;
    }

    private static final void dismissRechargeDialog$lambda$46(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        j9.O o = membershipPointsFragmentV4.rechargeDialog;
        if (o != null) {
            o.dismiss();
        }
    }

    private final int getHeadDefault() {
        return Z6.dramabox.dramabox.d0() == 1 ? com.storymatrix.drama.R.drawable.ic_points_avatar : implements.dramabox.l1() ? com.storymatrix.drama.R.drawable.ic_head_default_v2 : com.storymatrix.drama.R.drawable.ic_head_default_v2_light;
    }

    private final PushDialogVM getPushDialogVM() {
        return (PushDialogVM) this.pushDialogVM$delegate.getValue();
    }

    private final MemberPointsTaskAdapter getTaskAdapter() {
        return (MemberPointsTaskAdapter) this.taskAdapter$delegate.getValue();
    }

    private final TpVm getTpVm() {
        return (TpVm) this.tpVm$delegate.getValue();
    }

    private static final Unit initListener$lambda$30(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        membershipPointsFragmentV4.showRuleDialog();
        return Unit.dramabox;
    }

    private static final Unit initListener$lambda$31(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        membershipPointsFragmentV4.showRuleDialog();
        return Unit.dramabox;
    }

    private static final Unit initListener$lambda$32(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        buttonClickSub$default(membershipPointsFragmentV4, null, 1, null);
        return Unit.dramabox;
    }

    private static final Unit initListener$lambda$33(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        buttonClickSub$default(membershipPointsFragmentV4, null, 1, null);
        return Unit.dramabox;
    }

    private static final Unit initListener$lambda$34(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        if (Z6.dramabox.dramabox.d0() == 1) {
            membershipPointsFragmentV4.buttonClickSub("积分页面激活会员-奖励区域");
        }
        return Unit.dramabox;
    }

    private static final void initListener$lambda$35(MembershipPointsFragmentV4 membershipPointsFragmentV4, View view) {
        membershipPointsFragmentV4.refreshPage();
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0022  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0080  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final Unit initListener$lambda$36(MembershipPointsFragmentV4 membershipPointsFragmentV4, PointsTask pointsTask) {
        String str;
        BaseFragment baseFragment = membershipPointsFragmentV4;
        Intrinsics.checkNotNullParameter(pointsTask, "task");
        Integer taskType = pointsTask.getTaskType();
        if (taskType == null) {
            if (taskType == null) {
                if (taskType.intValue() == 2) {
                    str = "播放领取";
                }
            }
            if (taskType == null) {
                if (taskType.intValue() == 3) {
                    taskType = pointsTask.getProgress();
                    if (taskType != null) {
                        if (taskType.intValue() == 0) {
                            taskType = pointsTask.getRewardStatus();
                            if (taskType != null) {
                                if (taskType.intValue() == 0) {
                                    str = "同意保持签约";
                                }
                            }
                        }
                    }
                    str = "保持签约领取";
                }
            }
            if (taskType == null) {
                if (taskType.intValue() == 4) {
                    taskType = pointsTask.getProgress();
                    if (taskType != null) {
                        if (taskType.intValue() == 0) {
                            taskType = pointsTask.getRewardStatus();
                            if (taskType != null) {
                                if (taskType.intValue() == 0) {
                                    str = "续订点击";
                                }
                            }
                        }
                    }
                    str = "续订领取";
                }
            }
            if (taskType == null) {
                if (taskType.intValue() == 6) {
                    taskType = pointsTask.getProgress();
                    if (taskType != null) {
                        if (taskType.intValue() == 0) {
                            taskType = pointsTask.getRewardStatus();
                            if (taskType != null) {
                                if (taskType.intValue() == 0) {
                                    str = "Push授权点击";
                                }
                            }
                        }
                    }
                    str = "Push授权领取";
                }
            }
            str = "";
        } else {
            if (taskType.intValue() == 1) {
                str = "签到领取";
            }
            if (taskType == null) {
            }
            if (taskType == null) {
            }
            if (taskType == null) {
            }
            if (taskType == null) {
            }
            str = "";
        }
        SensorLog.tyu(SensorLog.dramaboxapp.O(), membershipPointsFragmentV4.getPageAlias(), null, null, "新版会员积分福利", str, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, -26, -1, 1, null);
        taskType = pointsTask.getTaskType();
        if (taskType != null) {
            if (taskType.intValue() == 4) {
                taskType = pointsTask.getProgress();
                if (taskType != null) {
                    if (taskType.intValue() == 0) {
                        taskType = pointsTask.getRewardStatus();
                        if (taskType != null) {
                            if (taskType.intValue() == 0) {
                                if (Z6.dramabox.dramabox.d0() == 1) {
                                    membershipPointsFragmentV4.getRechargeList();
                                } else {
                                    JumpUtils.JKi(JumpUtils.dramabox, membershipPointsFragmentV4.getContext(), MembershipChannel.Points.getChannel(), null, null, null, null, null, null, false, 508, null);
                                }
                                return Unit.dramabox;
                            }
                        }
                    }
                }
            }
        }
        taskType = pointsTask.getTaskType();
        if (taskType != null) {
            if (taskType.intValue() == 6) {
                taskType = pointsTask.getProgress();
                if (taskType != null) {
                    if (taskType.intValue() == 0) {
                        if (membershipPointsFragmentV4.getActivity() != null) {
                            swr swr = swr.dramabox;
                            AppCompatActivity appCompatActivity = baseFragment.mActivity;
                            Intrinsics.checkNotNullExpressionValue(appCompatActivity, "mActivity");
                            if (swr.dramaboxapp(appCompatActivity)) {
                                TaskReportUtil.dramabox.dramaboxapp(new ReportInfo(14, 1, 0), new O(baseFragment));
                                return Unit.dramabox;
                            }
                        }
                        swr.dramabox.tyu(baseFragment.mActivity);
                        return Unit.dramabox;
                    }
                }
            }
        }
        taskType = pointsTask.getTaskType();
        if (taskType != null) {
            if (taskType.intValue() == 3) {
                taskType = pointsTask.getProgress();
                if (taskType != null) {
                    if (taskType.intValue() == 0) {
                        ((MembershipPointsVM) baseFragment.mViewModel).io(pointsTask.getSeriesId(), pointsTask.getTaskId());
                        return Unit.dramabox;
                    }
                }
            }
        }
        ((MembershipPointsVM) baseFragment.mViewModel).ppo(pointsTask.getSeriesId(), pointsTask.getTaskId(), membershipPointsFragmentV4.getPageAlias());
        return Unit.dramabox;
    }

    private static final Unit initListener$lambda$37(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        JumpUtils.dramabox.Jui(membershipPointsFragmentV4.mActivity);
        return Unit.dramabox;
    }

    private static final Unit initListener$lambda$38(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        JumpUtils.dramabox.Jui(membershipPointsFragmentV4.mActivity);
        return Unit.dramabox;
    }

    private static final void initListener$lambda$39(int i, MembershipPointsFragmentV4 membershipPointsFragmentV4, View view, int i2, int i3, int i4, int i5) {
        i = ColorUtils.setAlphaComponent(i, (int) (l.RT(((float) i3) / ((float) swe.dramaboxapp(60)), 0.0f, 1.0f) * ((float) 255)));
        ImageView imageView = ((FragmentMembershipPointsV3Binding) membershipPointsFragmentV4.mBinding).yu0;
        Mode mode = Mode.SRC_ATOP;
        imageView.setColorFilter(i, mode);
        Drawable background = ((FragmentMembershipPointsV3Binding) membershipPointsFragmentV4.mBinding).l1.getBackground();
        if (background != null) {
            background = background.mutate();
            if (background != null) {
                background.setColorFilter(i, mode);
            }
        }
        membershipPointsFragmentV4.checkStickyButtonVisibility();
    }

    private static final Unit initListener$lambda$40(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        membershipPointsFragmentV4.signInCalendarDelegate.ppo();
        return Unit.dramabox;
    }

    private final void initPointsRollingView() {
        RollingTextView rollingTextView;
        FragmentMembershipPointsV3Binding fragmentMembershipPointsV3Binding = (FragmentMembershipPointsV3Binding) this.mBinding;
        if (fragmentMembershipPointsV3Binding != null) {
            rollingTextView = fragmentMembershipPointsV3Binding.class;
            if (rollingTextView != null) {
                rollingTextView.setCharStrategy(I.O(Direction.SCROLL_DOWN));
            }
        }
        fragmentMembershipPointsV3Binding = (FragmentMembershipPointsV3Binding) this.mBinding;
        if (fragmentMembershipPointsV3Binding != null) {
            rollingTextView = fragmentMembershipPointsV3Binding.class;
            if (rollingTextView != null) {
                rollingTextView.setAnimationDuration(400);
            }
        }
        fragmentMembershipPointsV3Binding = (FragmentMembershipPointsV3Binding) this.mBinding;
        if (fragmentMembershipPointsV3Binding != null) {
            rollingTextView = fragmentMembershipPointsV3Binding.class;
            if (rollingTextView != null) {
                rollingTextView.l1("0123456789");
            }
        }
        fragmentMembershipPointsV3Binding = (FragmentMembershipPointsV3Binding) this.mBinding;
        if (fragmentMembershipPointsV3Binding != null) {
            rollingTextView = fragmentMembershipPointsV3Binding.class;
            if (rollingTextView != null) {
                rollingTextView.setAnimationInterpolator(new AccelerateDecelerateInterpolator());
            }
        }
    }

    private static final Unit initViewObservable$lambda$11(MembershipPointsFragmentV4 membershipPointsFragmentV4, PointsExchangeResult pointsExchangeResult) {
        BaseActivity baseActivity = (BaseActivity) membershipPointsFragmentV4.mActivity;
        if (baseActivity != null) {
            baseActivity.cancelLoadingDialogDelay();
        }
        String str = null;
        Context context;
        if (pointsExchangeResult == null) {
            context = membershipPointsFragmentV4.getContext();
            if (context != null) {
                str = context.getString(R.string.str_the_network_seems_unstable);
            }
            Y6.I.lO(str);
            return Unit.dramabox;
        }
        Integer exchangeResult = pointsExchangeResult.getExchangeResult();
        if (exchangeResult != null) {
            if (exchangeResult.intValue() == 0) {
                Context context2 = membershipPointsFragmentV4.getContext();
                if (context2 != null) {
                    str = context2.getString(R.string.str_mymem_redemption_toast_3);
                }
                Y6.I.lO(str);
                membershipPointsFragmentV4.refreshPage();
                RxBus.getDefault().post(new BusEvent(10002));
                return Unit.dramabox;
            }
        }
        if (exchangeResult != null) {
            if (exchangeResult.intValue() == 1) {
                context = membershipPointsFragmentV4.getContext();
                if (context != null) {
                    str = context.getString(R.string.str_mymem_redemption_toast_1);
                }
                Y6.I.lO(str);
                return Unit.dramabox;
            }
        }
        if (exchangeResult != null) {
            if (exchangeResult.intValue() == 2) {
                context = membershipPointsFragmentV4.getContext();
                if (context != null) {
                    str = context.getString(R.string.str_mymem_redemption_toast_2);
                }
                Y6.I.lO(str);
                return Unit.dramabox;
            }
        }
        context = membershipPointsFragmentV4.getContext();
        if (context != null) {
            str = context.getString(R.string.str_the_network_seems_unstable);
        }
        Y6.I.lO(str);
        return Unit.dramabox;
    }

    private static final Unit initViewObservable$lambda$13(MembershipPointsFragmentV4 membershipPointsFragmentV4, OperationActivities operationActivities) {
        List activityList = operationActivities != null ? operationActivities.getActivityList() : null;
        Collection collection = activityList;
        if (collection != null) {
            if (!collection.isEmpty()) {
                int size = collection.size();
                for (int i = 0; i < size; i++) {
                    if (Intrinsics.areEqual("SUB_FAIL_POP_UP", ((OperationActivity) activityList.get(i)).getPosition())) {
                        RechargePopUp rechargePopUp = ((OperationActivity) activityList.get(i)).getRechargePopUp();
                        if (rechargePopUp != null) {
                            membershipPointsFragmentV4.showSubFailDialog(((OperationActivity) activityList.get(i)).getId(), rechargePopUp);
                        }
                    }
                }
                return Unit.dramabox;
            }
        }
        return Unit.dramabox;
    }

    private static final Unit initViewObservable$lambda$17(MembershipPointsFragmentV4 membershipPointsFragmentV4, s7.dramabox dramabox) {
        if (dramabox instanceof s7.dramabox.O) {
            j9.O o = membershipPointsFragmentV4.rechargeDialog;
            if (o != null) {
                List dramaboxapp = o.dramaboxapp();
                if (dramaboxapp != null) {
                    Object obj = null;
                    if (dramaboxapp.isEmpty()) {
                        dramaboxapp = null;
                    }
                    if (dramaboxapp != null) {
                        for (Object next : dramaboxapp) {
                            if (Intrinsics.areEqual(((BillingParamsInfo) next).getProductId(), ((MembershipPointsVM) membershipPointsFragmentV4.mViewModel).lks())) {
                                obj = next;
                                break;
                            }
                        }
                        BillingParamsInfo billingParamsInfo = (BillingParamsInfo) obj;
                        o = membershipPointsFragmentV4.rechargeDialog;
                        boolean l1 = o != null ? o.l1() : false;
                        if (billingParamsInfo != null && l1) {
                            MembershipPointsVM membershipPointsVM = (MembershipPointsVM) membershipPointsFragmentV4.mViewModel;
                            String bookId = billingParamsInfo.getBookId();
                            String chapterId = billingParamsInfo.getChapterId();
                            String str = "";
                            if (chapterId == null) {
                                chapterId = str;
                            }
                            membershipPointsVM.aew(bookId, chapterId, str, true);
                        }
                    }
                }
            }
        }
        return Unit.dramabox;
    }

    private static final Unit initViewObservable$lambda$18(MembershipPointsFragmentV4 membershipPointsFragmentV4, List list) {
        Collection collection = list;
        String str = "PushDialogManager";
        if (collection != null) {
            if (!collection.isEmpty()) {
                for (OperationActivity operationActivity : list) {
                    if (Intrinsics.areEqual(operationActivity.getPosition(), "MEMBER_POINTS") && Intrinsics.areEqual(operationActivity.getActType(), "POP_UP_WINDOW")) {
                        XlogUtils.dramabox.dramaboxapp(str, "MEMBERSHIP_POINTS showOpenNotificationDialog");
                        showOpenNotificationDialog$default(membershipPointsFragmentV4, operationActivity, null, 2, null);
                        break;
                    }
                }
                return Unit.dramabox;
            }
        }
        XlogUtils.dramabox.dramaboxapp(str, "MEMBERSHIP_POINTS activities.isNullOrEmpty()");
        return Unit.dramabox;
    }

    private static final Unit initViewObservable$lambda$20(MembershipPointsFragmentV4 membershipPointsFragmentV4, s7.dramabox dramabox) {
        if (!((dramabox instanceof s7.dramabox.dramabox) || Intrinsics.areEqual(dramabox, s7.dramabox.dramaboxapp.dramabox))) {
            if (dramabox instanceof s7.dramabox.O) {
                AppCompatActivity appCompatActivity = membershipPointsFragmentV4.mActivity;
                Intrinsics.checkNotNull(appCompatActivity, "null cannot be cast to non-null type com.storymatrix.drama.base.BaseActivity<*, *>");
                ((BaseActivity) appCompatActivity).cancelLoadingDialogDelay();
                AcceptTask acceptTask = (AcceptTask) ((s7.dramabox.O) dramabox).dramabox();
                if (acceptTask == null) {
                    return Unit.dramabox;
                }
                Integer status = acceptTask.getStatus();
                if (status != null) {
                    if (status.intValue() == 1) {
                        membershipPointsFragmentV4.refreshPage();
                    }
                }
                String toast = acceptTask.getToast();
                if (toast != null) {
                    Y6.I.lO(toast);
                }
            } else {
                throw new NoWhenBranchMatchedException();
            }
        }
        return Unit.dramabox;
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x010d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final Unit onRecharge$lambda$44(MembershipPointsFragmentV4 membershipPointsFragmentV4, BillingParamsInfo billingParamsInfo, BillingParamsInfo billingParamsInfo2) {
        String str;
        String str2;
        double realPrice;
        double promotionPrice;
        double d;
        String str3;
        double d2;
        double d3;
        double d4;
        W6.O o;
        String str4;
        SensorLog O;
        String pageAlias;
        String layerId;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        int id;
        Intrinsics.checkNotNullParameter(billingParamsInfo2, GXechcBTBrmdPe.TlwrAQEDNkP);
        MembershipPointsVM membershipPointsVM = (MembershipPointsVM) membershipPointsFragmentV4.mViewModel;
        FragmentActivity activity = membershipPointsFragmentV4.getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.storymatrix.drama.base.BaseActivity<*, *>");
        membershipPointsVM.Jkl((BaseActivity) activity, billingParamsInfo);
        String str10 = "";
        if (billingParamsInfo.getGearType() == 3) {
            str = "金币包订阅";
        } else if (billingParamsInfo.getGearType() == 2) {
            str = "VIP订阅";
        } else {
            str2 = str10;
            if (billingParamsInfo.getSubType() != 3) {
                realPrice = billingParamsInfo.getRealPrice();
                promotionPrice = billingParamsInfo.getPromotionPrice();
                d = realPrice - promotionPrice;
                str = "首期折扣";
            } else if (billingParamsInfo.getSubType() == 4) {
                realPrice = billingParamsInfo.getRealPrice();
                promotionPrice = billingParamsInfo.getPromotionPrice();
                d = realPrice - promotionPrice;
                str = "订阅管理复订折扣";
            } else {
                if (billingParamsInfo.getGearType() == 2) {
                    promotionPrice = billingParamsInfo.getRealPrice();
                    d = billingParamsInfo.getRealPrice();
                    str3 = "普通订阅";
                } else {
                    promotionPrice = billingParamsInfo.getRealPrice();
                    d = billingParamsInfo.getRealPrice();
                    str3 = str10;
                }
                d2 = 0.0d;
                d3 = promotionPrice;
                d4 = d;
                o = (W6.O) fa.dramabox.dramabox(W6.O.class);
                if (o != null) {
                    str = o.O0l();
                    if (str != null) {
                        str4 = str;
                        O = SensorLog.dramaboxapp.O();
                        pageAlias = membershipPointsFragmentV4.getPageAlias();
                        layerId = billingParamsInfo.getLayerId();
                        str5 = layerId != null ? str10 : layerId;
                        layerId = billingParamsInfo.getGroupId();
                        str6 = layerId != null ? str10 : layerId;
                        if (billingParamsInfo.getGearType() != 2) {
                            if (billingParamsInfo.getGearType() != 3) {
                                layerId = "recharge";
                                str7 = layerId;
                                layerId = billingParamsInfo.getActivityId();
                                str8 = layerId == null ? str10 : layerId;
                                layerId = billingParamsInfo.getActivityTitle();
                                str9 = layerId == null ? str10 : layerId;
                                id = billingParamsInfo.getId();
                                SensorLog.o0(O, pageAlias, str4, "", "", "", "", "", false, "", str5, str6, str7, str8, str9, id == 0 ? str10 : id, billingParamsInfo.getProductId(), d4, billingParamsInfo.getCoins(), billingParamsInfo.getBonus(), false, billingParamsInfo.getDefaultGear() == 1, str2, "points", str3, d3, d2, null, null, null, null, null, null, -67108864, null);
                                return Unit.dramabox;
                            }
                        }
                        layerId = "sub";
                        str7 = layerId;
                        layerId = billingParamsInfo.getActivityId();
                        if (layerId == null) {
                        }
                        layerId = billingParamsInfo.getActivityTitle();
                        if (layerId == null) {
                        }
                        id = billingParamsInfo.getId();
                        if (id == 0) {
                        }
                        if (billingParamsInfo.getDefaultGear() == 1) {
                        }
                        SensorLog.o0(O, pageAlias, str4, "", "", "", "", "", false, "", str5, str6, str7, str8, str9, id == 0 ? str10 : id, billingParamsInfo.getProductId(), d4, billingParamsInfo.getCoins(), billingParamsInfo.getBonus(), false, billingParamsInfo.getDefaultGear() == 1, str2, "points", str3, d3, d2, null, null, null, null, null, null, -67108864, null);
                        return Unit.dramabox;
                    }
                }
                str4 = str10;
                O = SensorLog.dramaboxapp.O();
                pageAlias = membershipPointsFragmentV4.getPageAlias();
                layerId = billingParamsInfo.getLayerId();
                if (layerId != null) {
                }
                layerId = billingParamsInfo.getGroupId();
                if (layerId != null) {
                }
                if (billingParamsInfo.getGearType() != 2) {
                }
                layerId = "sub";
                str7 = layerId;
                layerId = billingParamsInfo.getActivityId();
                if (layerId == null) {
                }
                layerId = billingParamsInfo.getActivityTitle();
                if (layerId == null) {
                }
                id = billingParamsInfo.getId();
                if (id == 0) {
                }
                if (billingParamsInfo.getDefaultGear() == 1) {
                }
                SensorLog.o0(O, pageAlias, str4, "", "", "", "", "", false, "", str5, str6, str7, str8, str9, id == 0 ? str10 : id, billingParamsInfo.getProductId(), d4, billingParamsInfo.getCoins(), billingParamsInfo.getBonus(), false, billingParamsInfo.getDefaultGear() == 1, str2, "points", str3, d3, d2, null, null, null, null, null, null, -67108864, null);
                return Unit.dramabox;
            }
            str3 = str;
            d3 = realPrice;
            d4 = promotionPrice;
            d2 = d;
            o = (W6.O) fa.dramabox.dramabox(W6.O.class);
            if (o != null) {
            }
            str4 = str10;
            O = SensorLog.dramaboxapp.O();
            pageAlias = membershipPointsFragmentV4.getPageAlias();
            layerId = billingParamsInfo.getLayerId();
            if (layerId != null) {
            }
            layerId = billingParamsInfo.getGroupId();
            if (layerId != null) {
            }
            if (billingParamsInfo.getGearType() != 2) {
            }
            layerId = "sub";
            str7 = layerId;
            layerId = billingParamsInfo.getActivityId();
            if (layerId == null) {
            }
            layerId = billingParamsInfo.getActivityTitle();
            if (layerId == null) {
            }
            id = billingParamsInfo.getId();
            if (id == 0) {
            }
            if (billingParamsInfo.getDefaultGear() == 1) {
            }
            SensorLog.o0(O, pageAlias, str4, "", "", "", "", "", false, "", str5, str6, str7, str8, str9, id == 0 ? str10 : id, billingParamsInfo.getProductId(), d4, billingParamsInfo.getCoins(), billingParamsInfo.getBonus(), false, billingParamsInfo.getDefaultGear() == 1, str2, "points", str3, d3, d2, null, null, null, null, null, null, -67108864, null);
            return Unit.dramabox;
        }
        str2 = str;
        if (billingParamsInfo.getSubType() != 3) {
        }
        str3 = str;
        d3 = realPrice;
        d4 = promotionPrice;
        d2 = d;
        o = (W6.O) fa.dramabox.dramabox(W6.O.class);
        if (o != null) {
        }
        str4 = str10;
        O = SensorLog.dramaboxapp.O();
        pageAlias = membershipPointsFragmentV4.getPageAlias();
        layerId = billingParamsInfo.getLayerId();
        if (layerId != null) {
        }
        layerId = billingParamsInfo.getGroupId();
        if (layerId != null) {
        }
        if (billingParamsInfo.getGearType() != 2) {
        }
        layerId = "sub";
        str7 = layerId;
        layerId = billingParamsInfo.getActivityId();
        if (layerId == null) {
        }
        layerId = billingParamsInfo.getActivityTitle();
        if (layerId == null) {
        }
        id = billingParamsInfo.getId();
        if (id == 0) {
        }
        if (billingParamsInfo.getDefaultGear() == 1) {
        }
        SensorLog.o0(O, pageAlias, str4, "", "", "", "", "", false, "", str5, str6, str7, str8, str9, id == 0 ? str10 : id, billingParamsInfo.getProductId(), d4, billingParamsInfo.getCoins(), billingParamsInfo.getBonus(), false, billingParamsInfo.getDefaultGear() == 1, str2, "points", str3, d3, d2, null, null, null, null, null, null, -67108864, null);
        return Unit.dramabox;
    }

    private final void pointsFlyAnimator(int[] iArr, int i) {
        int[] iArr2 = new int[2];
        Object obj = getActivity() instanceof MainActivity ? ((FragmentMembershipPointsV3Binding) this.mBinding).oiu : ((FragmentMembershipPointsV3Binding) this.mBinding).JOp;
        Intrinsics.checkNotNull(obj);
        obj.getLocationOnScreen(iArr2);
        int[] iArr3 = new int[2];
        ViewGroup viewGroup = ((FragmentMembershipPointsV3Binding) this.mBinding).l1;
        Intrinsics.checkNotNullExpressionValue(viewGroup, "clContentParent");
        viewGroup.getLocationOnScreen(iArr3);
        int i2 = iArr[0];
        int i3 = iArr3[0];
        float f = (float) (i2 - i3);
        i2 = iArr[1];
        int i4 = iArr3[1];
        float f2 = (float) (i2 - i4);
        float f3 = (float) (iArr2[0] - i3);
        float f4 = (float) (iArr2[1] - i4);
        float f5 = implements.dramabox.lO() ? ((f + f3) / ((float) 2)) - ((float) 200) : ((f + f3) / ((float) 2)) + ((float) 200);
        float min = Math.min(f2, f4) - ((float) 300);
        ArrayList arrayList = new ArrayList();
        Context context = getContext();
        if (context != null) {
            ArrayList arrayList2;
            i2 = 0;
            while (i2 < 8) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(com.storymatrix.drama.R.drawable.ic_redeem_points);
                int dramaboxapp = swe.dramaboxapp(20);
                imageView.setLayoutParams(new FrameLayout.LayoutParams(dramaboxapp, dramaboxapp));
                imageView.setX(f);
                imageView.setY(f2);
                imageView.setVisibility(4);
                viewGroup.addView(imageView);
                Path path = new Path();
                path.moveTo(f, f2);
                path.quadTo(f5, min, f3, f4);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(imageView, View.X, View.Y, path);
                Context context2 = context;
                ArrayList arrayList3 = arrayList;
                ofFloat.setDuration(500);
                ofFloat.setInterpolator(new AccelerateDecelerateInterpolator());
                long j = ((long) i2) * 50;
                ofFloat.setStartDelay(j);
                ImageView imageView2 = imageView;
                AnimatorListener animatorListener = r0;
                int i5 = i2;
                long j2 = j;
                ViewGroup viewGroup2 = viewGroup;
                float f6 = f;
                arrayList2 = arrayList3;
                f = min;
                io ioVar = new io(imageView, viewGroup, i2, 8, i, this);
                ofFloat.addListener(animatorListener);
                Intrinsics.checkNotNull(ofFloat);
                arrayList2.add(ofFloat);
                ImageView imageView3 = imageView2;
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(imageView3, "scaleX", new float[]{1.5f, 1.0f});
                ofFloat2.setDuration(500);
                long j3 = j2;
                ofFloat2.setStartDelay(j3);
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(imageView3, "scaleY", new float[]{1.5f, 1.0f});
                ofFloat3.setDuration(500);
                ofFloat3.setStartDelay(j3);
                Intrinsics.checkNotNull(ofFloat2);
                arrayList2.add(ofFloat2);
                Intrinsics.checkNotNull(ofFloat3);
                arrayList2.add(ofFloat3);
                i2 = i5 + 1;
                int i6 = 1;
                int i7 = 2;
                arrayList = arrayList2;
                min = f;
                context = context2;
                viewGroup = viewGroup2;
                f = f6;
            }
            arrayList2 = arrayList;
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList2);
            animatorSet.start();
        }
    }

    private final void setupTpMethod() {
        getTpPaymentAdapter().lO(this, getTpVm().lO(), getTpVm().ll(), new l1(new k0(this), new l0(this), new m0(this), new n0(this), new o0(this), new p0(this), null, 64, null));
    }

    private static final Unit setupTpMethod$lambda$48(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        BaseActivity baseActivity = (BaseActivity) membershipPointsFragmentV4.mActivity;
        if (baseActivity != null) {
            baseActivity.showLoadingDialog();
        }
        return Unit.dramabox;
    }

    private static final Unit setupTpMethod$lambda$49(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        BaseActivity baseActivity = (BaseActivity) membershipPointsFragmentV4.mActivity;
        if (baseActivity != null) {
            baseActivity.dismissLoadingDialog();
        }
        return Unit.dramabox;
    }

    private static final Unit setupTpMethod$lambda$50(MembershipPointsFragmentV4 membershipPointsFragmentV4, String str) {
        Intrinsics.checkNotNullParameter(str, "url");
        x9.I i = x9.I.dramabox;
        AppCompatActivity appCompatActivity = membershipPointsFragmentV4.mActivity;
        Intrinsics.checkNotNullExpressionValue(appCompatActivity, "mActivity");
        i.LLk(appCompatActivity, str);
        return Unit.dramabox;
    }

    private static final Unit setupTpMethod$lambda$51(MembershipPointsFragmentV4 membershipPointsFragmentV4, boolean z) {
        membershipPointsFragmentV4.getTpVm().lo(z);
        return Unit.dramabox;
    }

    private static final Unit setupTpMethod$lambda$52(MembershipPointsFragmentV4 membershipPointsFragmentV4, BillingParamsInfo billingParamsInfo) {
        Intrinsics.checkNotNullParameter(billingParamsInfo, "item");
        membershipPointsFragmentV4.getTpVm().l1(billingParamsInfo, membershipPointsFragmentV4.getPageAlias());
        return Unit.dramabox;
    }

    private static final Unit setupTpMethod$lambda$54(MembershipPointsFragmentV4 membershipPointsFragmentV4, BillingParamsInfo billingParamsInfo) {
        if (membershipPointsFragmentV4.getContext() == null) {
            return Unit.dramabox;
        }
        membershipPointsFragmentV4.dismissRechargeDialog();
        RechargeUtils rechargeUtils = RechargeUtils.dramabox;
        AppCompatActivity appCompatActivity = membershipPointsFragmentV4.mActivity;
        Intrinsics.checkNotNull(appCompatActivity, "null cannot be cast to non-null type com.storymatrix.drama.base.BaseActivity<*, *>");
        RechargeUtils.tyu(rechargeUtils, (BaseActivity) appCompatActivity, null, new j0(membershipPointsFragmentV4), null, 10, null);
        return Unit.dramabox;
    }

    private static final Unit setupTpMethod$lambda$54$lambda$53(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        membershipPointsFragmentV4.refreshPage();
        return Unit.dramabox;
    }

    private final void showContentView() {
        ((FragmentMembershipPointsV3Binding) this.mBinding).swe.setVisibility(0);
        if (getActivity() instanceof GiftCenterActivity) {
            ((FragmentMembershipPointsV3Binding) this.mBinding).const.setVisibility(0);
        }
        ((FragmentMembershipPointsV3Binding) this.mBinding).swq.Jvf();
    }

    private final void showErrorView(int i) {
        if (i == 0) {
            ((FragmentMembershipPointsV3Binding) this.mBinding).swq.Jqq();
        } else if (i != 1) {
            ((FragmentMembershipPointsV3Binding) this.mBinding).swq.ygn(getString(R.string.str_there_are_no_reward_tasks_currently));
        } else {
            ((FragmentMembershipPointsV3Binding) this.mBinding).swq.O0l(1);
        }
        ((FragmentMembershipPointsV3Binding) this.mBinding).swe.setVisibility(8);
        ((FragmentMembershipPointsV3Binding) this.mBinding).const.setVisibility(8);
    }

    private final void showOpenNotificationDialog(OperationActivity operationActivity, String str) {
        PushDialogManager pushDialogManager = this.pushDialogManager;
        if (pushDialogManager != null) {
            PushDialogManager.opn(pushDialogManager, operationActivity, str, null, null, null, null, 60, null);
        }
    }

    public static /* synthetic */ void showOpenNotificationDialog$default(MembershipPointsFragmentV4 membershipPointsFragmentV4, OperationActivity operationActivity, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = swr.dramabox.IO();
        }
        membershipPointsFragmentV4.showOpenNotificationDialog(operationActivity, str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0043  */
    /* JADX WARNING: Missing block: B:4:0x000a, code:
            if (r0 == null) goto L_0x000c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void showRechargeDialog(RechargeInfo rechargeInfo) {
        if (this.rechargeDialog == null) {
            RechargeUiGroup rechargeUiType;
            int intValue;
            int i;
            j9.l dramabox;
            AppCompatActivity appCompatActivity;
            String purchaseSceneType;
            j9.O dramabox2;
            if (rechargeInfo != null) {
                rechargeUiType = rechargeInfo.getRechargeUiType();
            }
            rechargeUiType = RechargeUiGroup.DEFAULT;
            if (rechargeInfo != null) {
                Integer coinsFoldUp = rechargeInfo.getCoinsFoldUp();
                if (coinsFoldUp != null) {
                    intValue = coinsFoldUp.intValue();
                    i = intValue;
                    dramabox = yhj.dramabox.dramabox(rechargeUiType);
                    appCompatActivity = this.mActivity;
                    Intrinsics.checkNotNullExpressionValue(appCompatActivity, "mActivity");
                    if (rechargeInfo != null) {
                        purchaseSceneType = rechargeInfo.getPurchaseSceneType();
                        if (purchaseSceneType == null) {
                        }
                        dramabox2 = dramabox.dramabox(appCompatActivity, true, this, purchaseSceneType, i);
                        this.rechargeDialog = dramabox2;
                        if (dramabox2 != null) {
                            dramabox2.O(new i0(this));
                        }
                    }
                    purchaseSceneType = "";
                    dramabox2 = dramabox.dramabox(appCompatActivity, true, this, purchaseSceneType, i);
                    this.rechargeDialog = dramabox2;
                    if (dramabox2 != null) {
                    }
                }
            }
            intValue = 2;
            i = intValue;
            dramabox = yhj.dramabox.dramabox(rechargeUiType);
            appCompatActivity = this.mActivity;
            Intrinsics.checkNotNullExpressionValue(appCompatActivity, "mActivity");
            if (rechargeInfo != null) {
            }
            purchaseSceneType = "";
            dramabox2 = dramabox.dramabox(appCompatActivity, true, this, purchaseSceneType, i);
            this.rechargeDialog = dramabox2;
            if (dramabox2 != null) {
            }
        }
        j9.O o = this.rechargeDialog;
        if (o != null) {
            j9.O.dramabox.dramabox(o, getPageAlias(), rechargeInfo, null, null, null, null, false, null, 124, null);
        }
        j9.O o2 = this.rechargeDialog;
        if (o2 != null) {
            o2.lO();
        }
    }

    private static final Unit showRechargeDialog$lambda$47(MembershipPointsFragmentV4 membershipPointsFragmentV4) {
        membershipPointsFragmentV4.rechargeDialog = null;
        return Unit.dramabox;
    }

    /* JADX WARNING: Missing block: B:13:0x0027, code:
            if (r1 == null) goto L_0x0029;
     */
    /* JADX WARNING: Missing block: B:20:0x0038, code:
            if (r3 == null) goto L_0x003a;
     */
    /* JADX WARNING: Missing block: B:27:0x0049, code:
            if (r4 == null) goto L_0x004b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void showRuleDialog() {
        try {
            if (this.rewardsRulesDialog == null) {
                AppCompatActivity appCompatActivity = this.mActivity;
                Intrinsics.checkNotNullExpressionValue(appCompatActivity, "mActivity");
                this.rewardsRulesDialog = new N(appCompatActivity);
            }
            N n = this.rewardsRulesDialog;
            if (n != null) {
                String string;
                String rules;
                String highLightStr;
                Context context = getContext();
                String str = "";
                if (context != null) {
                    string = context.getString(R.string.str_points_rules_title);
                }
                string = str;
                PointsBean pointsBean = this.pointsBean;
                if (pointsBean != null) {
                    PointsRuleInfo pointsRuleInfo = pointsBean.getPointsRuleInfo();
                    if (pointsRuleInfo != null) {
                        rules = pointsRuleInfo.getRules();
                    }
                }
                rules = str;
                PointsBean pointsBean2 = this.pointsBean;
                if (pointsBean2 != null) {
                    PointsRuleInfo pointsRuleInfo2 = pointsBean2.getPointsRuleInfo();
                    if (pointsRuleInfo2 != null) {
                        highLightStr = pointsRuleInfo2.getHighLightStr();
                    }
                }
                highLightStr = str;
                PointsBean pointsBean3 = this.pointsBean;
                if (pointsBean3 != null) {
                    PointsRuleInfo pointsRuleInfo3 = pointsBean3.getPointsRuleInfo();
                    if (pointsRuleInfo3 != null) {
                        String jumpUrl = pointsRuleInfo3.getJumpUrl();
                        if (jumpUrl != null) {
                            str = jumpUrl;
                        }
                    }
                }
                n.yiu(string, rules, highLightStr, str);
            }
        } catch (Exception e) {
            XlogUtils.dramabox.O(e);
        }
    }

    private final void showSubFailDialog(int i, RechargePopUp rechargePopUp) {
        x0 x0Var;
        if (rechargePopUp == null || rechargePopUp.getCouponValidity() <= 0) {
            x0Var = this.subFailCouponDialog;
            if (x0Var != null) {
                x0Var.dismiss();
                return;
            }
            return;
        }
        if (this.subFailCouponDialog == null) {
            AppCompatActivity appCompatActivity = this.mActivity;
            Intrinsics.checkNotNullExpressionValue(appCompatActivity, "mActivity");
            x0 x0Var2 = new x0(appCompatActivity, getSkuLocalCurrency());
            this.subFailCouponDialog = x0Var2;
            x0Var2.O0l(new V(this, i));
            x0Var = this.subFailCouponDialog;
            if (x0Var != null) {
                x0Var.setOnDismissListener(new g0(this));
            }
        }
        x0Var = this.subFailCouponDialog;
        if (x0Var != null && !x0Var.isShowing()) {
            x0Var = this.subFailCouponDialog;
            if (x0Var != null) {
                j9.O o = this.rechargeDialog;
                x0Var.JKi(rechargePopUp, o != null ? o.dramaboxapp() : null);
            }
        }
    }

    private static final Unit showSubFailDialog$lambda$28(MembershipPointsFragmentV4 membershipPointsFragmentV4, int i, int i2) {
        ((MembershipPointsVM) membershipPointsFragmentV4.mViewModel).Ok1(0);
        ((MembershipPointsVM) membershipPointsFragmentV4.mViewModel).Jhg(String.valueOf(i), 7, Integer.valueOf(i2));
        return Unit.dramabox;
    }

    private static final void showSubFailDialog$lambda$29(MembershipPointsFragmentV4 membershipPointsFragmentV4, DialogInterface dialogInterface) {
        MembershipPointsVM membershipPointsVM = (MembershipPointsVM) membershipPointsFragmentV4.mViewModel;
        AppCompatActivity appCompatActivity = membershipPointsFragmentV4.mActivity;
        Intrinsics.checkNotNull(appCompatActivity, "null cannot be cast to non-null type com.storymatrix.drama.base.BaseActivity<*, *>");
        membershipPointsVM.yiu((BaseActivity) appCompatActivity, 3);
        membershipPointsFragmentV4.subFailCouponDialog = null;
    }

    private static final MemberPointsTaskAdapter taskAdapter_delegate$lambda$0() {
        return new MemberPointsTaskAdapter();
    }

    private final void updateCalendarReminderButton() {
        ConstraintLayout constraintLayout = ((FragmentMembershipPointsV3Binding) this.mBinding).static;
        Intrinsics.checkNotNullExpressionValue(constraintLayout, "vipLayout");
        if (constraintLayout.getVisibility() == 0) {
            PointsBean pointsBean = this.pointsBean;
            MarginLayoutParams marginLayoutParams = null;
            LayoutParams layoutParams;
            if ((pointsBean != null ? pointsBean.getPointReminderSwitch() : null) == null) {
                ((FragmentMembershipPointsV3Binding) this.mBinding).Ok1.setVisibility(8);
                layoutParams = ((FragmentMembershipPointsV3Binding) this.mBinding).public.getLayoutParams();
                if (layoutParams instanceof ConstraintLayout.LayoutParams) {
                    marginLayoutParams = (ConstraintLayout.LayoutParams) layoutParams;
                }
                if (marginLayoutParams != null) {
                    marginLayoutParams.width = -2;
                    marginLayoutParams.endToStart = -1;
                    marginLayoutParams.endToEnd = 0;
                    marginLayoutParams.horizontalBias = 0.5f;
                    marginLayoutParams.setMarginEnd(0);
                    marginLayoutParams.setMarginStart(0);
                    ((FragmentMembershipPointsV3Binding) this.mBinding).public.setLayoutParams(marginLayoutParams);
                }
                return;
            }
            layoutParams = ((FragmentMembershipPointsV3Binding) this.mBinding).public.getLayoutParams();
            if (layoutParams instanceof ConstraintLayout.LayoutParams) {
                marginLayoutParams = (ConstraintLayout.LayoutParams) layoutParams;
            }
            if (marginLayoutParams != null) {
                marginLayoutParams.width = 0;
                marginLayoutParams.endToEnd = -1;
                marginLayoutParams.endToStart = com.storymatrix.drama.R.id.llCalendarReminder;
                marginLayoutParams.horizontalBias = 0.0f;
                marginLayoutParams.setMarginEnd(getResources().getDimensionPixelSize(com.storymatrix.drama.R.dimen.dz_dp_12));
                marginLayoutParams.setMarginStart(getResources().getDimensionPixelSize(com.storymatrix.drama.R.dimen.dz_dp_16));
                ((FragmentMembershipPointsV3Binding) this.mBinding).public.setLayoutParams(marginLayoutParams);
            }
            if (Z6.dramabox.dramabox.c0()) {
                ((FragmentMembershipPointsV3Binding) this.mBinding).Ikl.setText(getString(R.string.str_schedule_reminded));
                ((FragmentMembershipPointsV3Binding) this.mBinding).Ikl.setTextColor(ContextCompat.getColor(requireActivity(), com.storymatrix.drama.R.color.color_0C1014_FFFFFF));
                ((FragmentMembershipPointsV3Binding) this.mBinding).Ok1.setBackgroundResource(com.storymatrix.drama.R.drawable.shape_calendar_reminder_enabled_bg);
                ((FragmentMembershipPointsV3Binding) this.mBinding).yyy.setVisibility(8);
                ((FragmentMembershipPointsV3Binding) this.mBinding).opn.setVisibility(0);
            } else {
                ((FragmentMembershipPointsV3Binding) this.mBinding).Ikl.setText(getString(R.string.str_schedule_remind));
                ((FragmentMembershipPointsV3Binding) this.mBinding).Ikl.setTextColor(-59555);
                ((FragmentMembershipPointsV3Binding) this.mBinding).Ok1.setBackgroundResource(com.storymatrix.drama.R.drawable.shape_calendar_reminder_border_bg);
                ((FragmentMembershipPointsV3Binding) this.mBinding).yyy.setVisibility(0);
                ((FragmentMembershipPointsV3Binding) this.mBinding).opn.setVisibility(8);
            }
            return;
        }
        ((FragmentMembershipPointsV3Binding) this.mBinding).Ok1.setVisibility(8);
    }

    private final void updatePageBtnStyle() {
        String str = "bottomLayout";
        String str2 = "tvSubBtnTop";
        String str3 = "shadow";
        RoundImageView roundImageView;
        DramaTextView dramaTextView;
        FrameLayout frameLayout;
        if (Z6.dramabox.dramabox.d0() == 1) {
            roundImageView = ((FragmentMembershipPointsV3Binding) this.mBinding).sqs;
            Intrinsics.checkNotNullExpressionValue(roundImageView, str3);
            roundImageView.setVisibility(8);
            dramaTextView = ((FragmentMembershipPointsV3Binding) this.mBinding).final;
            Intrinsics.checkNotNullExpressionValue(dramaTextView, str2);
            dramaTextView.setVisibility(8);
            frameLayout = ((FragmentMembershipPointsV3Binding) this.mBinding).I;
            Intrinsics.checkNotNullExpressionValue(frameLayout, str);
            frameLayout.setVisibility(0);
            return;
        }
        roundImageView = ((FragmentMembershipPointsV3Binding) this.mBinding).sqs;
        Intrinsics.checkNotNullExpressionValue(roundImageView, str3);
        roundImageView.setVisibility(0);
        dramaTextView = ((FragmentMembershipPointsV3Binding) this.mBinding).final;
        Intrinsics.checkNotNullExpressionValue(dramaTextView, str2);
        dramaTextView.setVisibility(0);
        frameLayout = ((FragmentMembershipPointsV3Binding) this.mBinding).I;
        Intrinsics.checkNotNullExpressionValue(frameLayout, str);
        frameLayout.setVisibility(8);
    }

    private final void updateTvPoints(String str, boolean z) {
        if (getActivity() instanceof MainActivity) {
            ((FragmentMembershipPointsV3Binding) this.mBinding).LLk.ppo(str, z);
        } else {
            ((FragmentMembershipPointsV3Binding) this.mBinding).class.ppo(str, z);
        }
    }

    public void close() {
    }

    /* JADX WARNING: Missing block: B:11:0x0022, code:
            if (r3.intValue() != 10053) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dealWithAction(BusEvent busEvent) {
        Integer valueOf = busEvent != null ? Integer.valueOf(busEvent.action) : null;
        if (valueOf != null) {
            if (valueOf.intValue() == 10061) {
                dismissRechargeDialog();
                return;
            }
        }
        if (valueOf == null) {
        }
        if (valueOf != null) {
            if (valueOf.intValue() != 10104) {
                return;
            }
            refreshPage();
        }
    }

    public CalendarReminder getCalendarReminder() {
        PointsBean pointsBean = this.pointsBean;
        return pointsBean != null ? pointsBean.getCalendarReminder() : null;
    }

    public String getCloseButtonName() {
        return "积分提醒_关闭";
    }

    public String getCurrentBookCover() {
        return ((MembershipPointsVM) this.mViewModel).yu0();
    }

    public String getCurrentBookId() {
        return ((MembershipPointsVM) this.mViewModel).yyy();
    }

    public String getCurrentBookName() {
        return ((MembershipPointsVM) this.mViewModel).opn();
    }

    public Integer getDialogIllustrationRes() {
        return Integer.valueOf(com.storymatrix.drama.R.drawable.bg_calendar_dialog_points);
    }

    public long getEventId() {
        return Z6.dramabox.dramabox.b0();
    }

    public Activity getHostActivity() {
        return getActivity();
    }

    public Context getHostContext() {
        return getContext();
    }

    public LifecycleOwner getHostLifecycleOwner() {
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
        return viewLifecycleOwner;
    }

    public String getOpenButtonName() {
        return "积分提醒_开启";
    }

    public String getPageAlias() {
        return getActivity() instanceof MainActivity ? "index_memberpoints" : "new_member_awards";
    }

    public final void getRechargeList() {
        MembershipPointsVM membershipPointsVM = (MembershipPointsVM) this.mViewModel;
        AppCompatActivity appCompatActivity = this.mActivity;
        Intrinsics.checkNotNull(appCompatActivity, "null cannot be cast to non-null type com.storymatrix.drama.base.BaseActivity<*, *>");
        membershipPointsVM.yiu((BaseActivity) appCompatActivity, 3);
    }

    public String getScheduleType() {
        return "会员领积分";
    }

    public final k9.dramaboxapp getSkuLocalCurrency() {
        k9.dramaboxapp dramaboxapp = this.skuLocalCurrency;
        if (dramaboxapp != null) {
            return dramaboxapp;
        }
        Intrinsics.throwUninitializedPropertyAccessException("skuLocalCurrency");
        return null;
    }

    public final e9.O getTpPaymentAdapter() {
        e9.O o = this.tpPaymentAdapter;
        if (o != null) {
            return o;
        }
        Intrinsics.throwUninitializedPropertyAccessException("tpPaymentAdapter");
        return null;
    }

    public int initContentView() {
        return com.storymatrix.drama.R.layout.fragment_membership_points_v3;
    }

    /* JADX WARNING: Missing block: B:22:0x0058, code:
            if (r0 == null) goto L_0x005a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initData() {
        String string;
        this.performanceDelegate.dramabox();
        FragmentActivity activity = getActivity();
        GiftCenterActivity giftCenterActivity = null;
        GiftCenterActivity giftCenterActivity2 = activity instanceof GiftCenterActivity ? (GiftCenterActivity) activity : null;
        if (giftCenterActivity2 != null && giftCenterActivity2.yhj() == 1) {
            activity = getActivity();
            if (activity instanceof GiftCenterActivity) {
                giftCenterActivity = (GiftCenterActivity) activity;
            }
            if (giftCenterActivity != null) {
                long djd = giftCenterActivity.djd();
                if (djd > 0) {
                    this.performanceDelegate.l1(djd);
                }
            }
        }
        Bundle arguments = getArguments();
        this.channel = arguments != null ? arguments.getInt("channel") : PointsChannel.SelectTab.ordinal();
        arguments = getArguments();
        if (arguments != null) {
            string = arguments.getString("pushTaskId");
        }
        string = "";
        this.pushTaskId = string;
        RecyclerView recyclerView = ((FragmentMembershipPointsV3Binding) this.mBinding).hfs;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        Context context = recyclerView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        recyclerView.addItemDecoration(new DividerItemDecoration(context, com.lib.common.R.color.opacity_divider, 0.0f, 4, null));
        recyclerView.setAdapter(getTaskAdapter());
        initPointsRollingView();
        FragmentActivity requireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(requireActivity, "requireActivity(...)");
        PushDialogManager pushDialogManager = new PushDialogManager(this, requireActivity, getPushDialogVM());
        getLifecycle().addObserver(pushDialogManager);
        this.pushDialogManager = pushDialogManager;
        CharSequence text = ((FragmentMembershipPointsV3Binding) this.mBinding).throw.getText();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        stringBuilder.append(text);
        SpannableString spannableString = new SpannableString(stringBuilder.toString());
        Drawable drawable = ContextCompat.getDrawable(this.mActivity, com.storymatrix.drama.R.drawable.ic_points_unlock_sub);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            spannableString.setSpan(new w(drawable, 0, swe.dramaboxapp(4)), 0, 1, 33);
            ((FragmentMembershipPointsV3Binding) this.mBinding).throw.setText(spannableString);
        }
        int JOp = com.gyf.immersionbar.O.JOp(this);
        String str = "tabHeaderLayout";
        String str2 = "tabGroup";
        Group group;
        LinearLayout linearLayout;
        if (getActivity() instanceof MainActivity) {
            DramaTextView dramaTextView = ((FragmentMembershipPointsV3Binding) this.mBinding).import;
            LayoutParams layoutParams = dramaTextView.getLayoutParams();
            String str3 = "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams";
            Intrinsics.checkNotNull(layoutParams, str3);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
            marginLayoutParams.topMargin += JOp;
            dramaTextView.setLayoutParams(marginLayoutParams);
            LinearLayout linearLayout2 = ((FragmentMembershipPointsV3Binding) this.mBinding).l;
            layoutParams = linearLayout2.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, str3);
            marginLayoutParams = (MarginLayoutParams) layoutParams;
            marginLayoutParams.topMargin = swe.dramaboxapp(22);
            linearLayout2.setLayoutParams(marginLayoutParams);
            NestedScrollView nestedScrollView = ((FragmentMembershipPointsV3Binding) this.mBinding).swe;
            layoutParams = nestedScrollView.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, str3);
            marginLayoutParams = (MarginLayoutParams) layoutParams;
            marginLayoutParams.topMargin = JOp + swe.dramaboxapp(54);
            nestedScrollView.setLayoutParams(marginLayoutParams);
            group = ((FragmentMembershipPointsV3Binding) this.mBinding).Sop;
            Intrinsics.checkNotNullExpressionValue(group, str2);
            group.setVisibility(0);
            Z6.dramabox dramabox = Z6.dramabox.dramabox;
            if (TextUtils.isEmpty(dramabox.a2()) || !dramabox.K()) {
                ((FragmentMembershipPointsV3Binding) this.mBinding).JKi.setImageResource(getHeadDefault());
            } else {
                RoundImageView roundImageView = ((FragmentMembershipPointsV3Binding) this.mBinding).JKi;
                Intrinsics.checkNotNullExpressionValue(roundImageView, "ivHead");
                x1.O.l(roundImageView, dramabox.a2(), 0, 0, null, 14, null);
            }
            ((FragmentMembershipPointsV3Binding) this.mBinding).catch.setText(TextUtils.isEmpty(dramabox.Y1()) ? getString(R.string.str_visitor) : dramabox.Y1());
            linearLayout = ((FragmentMembershipPointsV3Binding) this.mBinding).lml;
            Intrinsics.checkNotNullExpressionValue(linearLayout, str);
            linearLayout.setVisibility(0);
            DramaTextView dramaTextView2 = ((FragmentMembershipPointsV3Binding) this.mBinding).const;
            Intrinsics.checkNotNullExpressionValue(dramaTextView2, "tvRules");
            dramaTextView2.setVisibility(8);
            FrameLayout frameLayout = ((FragmentMembershipPointsV3Binding) this.mBinding).lop;
            Intrinsics.checkNotNullExpressionValue(frameLayout, "dailyAwardsLayout");
            frameLayout.setVisibility(8);
        } else {
            group = ((FragmentMembershipPointsV3Binding) this.mBinding).Sop;
            Intrinsics.checkNotNullExpressionValue(group, str2);
            group.setVisibility(8);
            linearLayout = ((FragmentMembershipPointsV3Binding) this.mBinding).lml;
            Intrinsics.checkNotNullExpressionValue(linearLayout, str);
            linearLayout.setVisibility(8);
        }
        this.performanceDelegate.dramaboxapp();
    }

    public void initListener() {
        DramaTextView dramaTextView = ((FragmentMembershipPointsV3Binding) this.mBinding).const;
        Intrinsics.checkNotNullExpressionValue(dramaTextView, "tvRules");
        ViewExtKt.tyu(dramaTextView, 0, new r0(this), 1, null);
        dramaTextView = ((FragmentMembershipPointsV3Binding) this.mBinding).native;
        Intrinsics.checkNotNullExpressionValue(dramaTextView, "tvTabRules");
        ViewExtKt.tyu(dramaTextView, 0, new u0(this), 1, null);
        dramaTextView = ((FragmentMembershipPointsV3Binding) this.mBinding).while;
        Intrinsics.checkNotNullExpressionValue(dramaTextView, "tvSubscribeButtonCover");
        ViewExtKt.tyu(dramaTextView, 0, new v0(this), 1, null);
        dramaTextView = ((FragmentMembershipPointsV3Binding) this.mBinding).final;
        Intrinsics.checkNotNullExpressionValue(dramaTextView, "tvSubBtnTop");
        ViewExtKt.tyu(dramaTextView, 0, new w0(this), 1, null);
        ConstraintLayout constraintLayout = ((FragmentMembershipPointsV3Binding) this.mBinding).return;
        Intrinsics.checkNotNullExpressionValue(constraintLayout, "unlockVipLayout");
        ViewExtKt.tyu(constraintLayout, 0, new W8.x0(this), 1, null);
        ((FragmentMembershipPointsV3Binding) this.mBinding).swq.setNetErrorClickListener(new y0(this));
        getTaskAdapter().io(new z0(this));
        LinearLayout linearLayout = ((FragmentMembershipPointsV3Binding) this.mBinding).swr;
        Intrinsics.checkNotNullExpressionValue(linearLayout, "pointsLayout");
        ViewExtKt.tyu(linearLayout, 0, new W(this), 1, null);
        linearLayout = ((FragmentMembershipPointsV3Binding) this.mBinding).LLL;
        Intrinsics.checkNotNullExpressionValue(linearLayout, "tabPointsLayout");
        ViewExtKt.tyu(linearLayout, 0, new X(this), 1, null);
        ((FragmentMembershipPointsV3Binding) this.mBinding).swe.setOnScrollChangeListener(new Y(ContextCompat.getColor(this.mActivity, com.lib.common.R.color.background_primary), this));
        linearLayout = ((FragmentMembershipPointsV3Binding) this.mBinding).Ok1;
        Intrinsics.checkNotNullExpressionValue(linearLayout, "llCalendarReminder");
        ViewExtKt.tyu(linearLayout, 0, new t0(this), 1, null);
    }

    public int initVariableId() {
        return 16;
    }

    public BaseViewModel initViewModel() {
        ViewModel fragmentViewModel = getFragmentViewModel(MembershipPointsVM.class);
        Intrinsics.checkNotNullExpressionValue(fragmentViewModel, "getFragmentViewModel(...)");
        return (MembershipPointsVM) fragmentViewModel;
    }

    public void initViewObservable() {
        SharedFlow pop = ((MembershipPointsVM) this.mViewModel).pop();
        State state = State.STARTED;
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new default.1(pop, this, state, null, this), 3, null);
        setupTpMethod();
        LifecycleUtilKt.l(this, null, new 2(this, null), 1, null);
        LifecycleUtilKt.l(this, null, new 2(this, null), 1, null);
        LifecycleUtilKt.l(this, null, new 2(this, null), 1, null);
        ((MembershipPointsVM) this.mViewModel).ygh().observe(this, new l1(new c0(this)));
        LifecycleUtilKt.l(this, null, new 2(this, null), 1, null);
        ((MembershipPointsVM) this.mViewModel).ygn().observe(this, new l1(new d0(this)));
        ((MembershipPointsVM) this.mViewModel).JKi().observe(this, new l1(new e0(this)));
        getPushDialogVM().l1().observe(this, new l1(new f0(this)));
        ((MembershipPointsVM) this.mViewModel).pos().observe(this, new l1(new h0(this)));
        SharedFlow tyu = ((MembershipPointsVM) this.mViewModel).tyu();
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new default.2(tyu, this, state, null, this), 3, null);
    }

    public boolean isReminderEnabled() {
        return Z6.dramabox.dramabox.c0();
    }

    public void onDestroy() {
        PushDialogManager pushDialogManager = this.pushDialogManager;
        if (pushDialogManager != null) {
            getLifecycle().removeObserver(pushDialogManager);
        }
        getTpPaymentAdapter().O();
        super.onDestroy();
        dismissRechargeDialog();
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onRecharge(BillingParamsInfo billingParamsInfo) {
        BillingParamsInfo billingParamsInfo2 = billingParamsInfo;
        BillingParamsInfo billingParamsInfo3 = billingParamsInfo;
        Intrinsics.checkNotNullParameter(billingParamsInfo2, "info");
        e9.O tpPaymentAdapter = getTpPaymentAdapter();
        String fromScene = PurchaseScene.MEMBERSHIP_POINTS.getFromScene();
        String str = this.pushTaskId;
        Function1 function1 = r4;
        Z z = new Z(this, billingParamsInfo2);
        e9.O.I(tpPaymentAdapter, billingParamsInfo3, null, null, null, null, null, 0, null, null, null, null, null, null, null, null, str, fromScene, null, "points", null, false, null, null, null, function1, 16416766, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0059  */
    /* JADX WARNING: Missing block: B:32:0x0063, code:
            if (r10 == null) goto L_0x0065;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onRedemptionConfirmClick(int i) {
        List pointsRedemptionInfoItemList;
        PointsRedemptionInfo pointsRedemptionInfo;
        BaseActivity baseActivity;
        MembershipPointsVM membershipPointsVM;
        long j;
        long longValue;
        long j2;
        String str;
        String str2;
        String layerId;
        PointsBean pointsBean = this.pointsBean;
        if (pointsBean != null) {
            PointsRedemptionInfoList pointsRedemptionInfo2 = pointsBean.getPointsRedemptionInfo();
            if (pointsRedemptionInfo2 != null) {
                pointsRedemptionInfoItemList = pointsRedemptionInfo2.getPointsRedemptionInfoItemList();
                pointsRedemptionInfo = (PointsRedemptionInfo) lo.dramaboxapp(pointsRedemptionInfoItemList, i);
                baseActivity = (BaseActivity) this.mActivity;
                if (baseActivity != null) {
                    baseActivity.showLoadingDialogDelay(300);
                }
                membershipPointsVM = (MembershipPointsVM) this.mViewModel;
                j = 0;
                if (pointsRedemptionInfo != null) {
                    Long productId = pointsRedemptionInfo.getProductId();
                    if (productId != null) {
                        longValue = productId.longValue();
                        if (pointsRedemptionInfo != null) {
                            productId = pointsRedemptionInfo.getExchangeId();
                            if (productId != null) {
                                j = productId.longValue();
                            }
                        }
                        j2 = j;
                        str = "";
                        if (pointsRedemptionInfo != null) {
                            UserLayerInfo userLayerInfo = pointsRedemptionInfo.getUserLayerInfo();
                            if (userLayerInfo != null) {
                                String groupId = userLayerInfo.getGroupId();
                                if (groupId != null) {
                                    str2 = groupId;
                                    if (pointsRedemptionInfo != null) {
                                        UserLayerInfo userLayerInfo2 = pointsRedemptionInfo.getUserLayerInfo();
                                        if (userLayerInfo2 != null) {
                                            layerId = userLayerInfo2.getLayerId();
                                        }
                                    }
                                    layerId = str;
                                    membershipPointsVM.O0l(longValue, j2, str2, layerId, getPageAlias());
                                }
                            }
                        }
                        str2 = str;
                        if (pointsRedemptionInfo != null) {
                        }
                        layerId = str;
                        membershipPointsVM.O0l(longValue, j2, str2, layerId, getPageAlias());
                    }
                }
                longValue = 0;
                if (pointsRedemptionInfo != null) {
                }
                j2 = j;
                str = "";
                if (pointsRedemptionInfo != null) {
                }
                str2 = str;
                if (pointsRedemptionInfo != null) {
                }
                layerId = str;
                membershipPointsVM.O0l(longValue, j2, str2, layerId, getPageAlias());
            }
        }
        pointsRedemptionInfoItemList = null;
        pointsRedemptionInfo = (PointsRedemptionInfo) lo.dramaboxapp(pointsRedemptionInfoItemList, i);
        baseActivity = (BaseActivity) this.mActivity;
        if (baseActivity != null) {
        }
        membershipPointsVM = (MembershipPointsVM) this.mViewModel;
        j = 0;
        if (pointsRedemptionInfo != null) {
        }
        longValue = 0;
        if (pointsRedemptionInfo != null) {
        }
        j2 = j;
        str = "";
        if (pointsRedemptionInfo != null) {
        }
        str2 = str;
        if (pointsRedemptionInfo != null) {
        }
        layerId = str;
        membershipPointsVM.O0l(longValue, j2, str2, layerId, getPageAlias());
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001a A:{RETURN} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onRedemptionItemClick(int i) {
        List pointsRedemptionInfoItemList;
        PointsRedemptionInfo pointsRedemptionInfo;
        PointsBean pointsBean = this.pointsBean;
        String str = null;
        if (pointsBean != null) {
            PointsRedemptionInfoList pointsRedemptionInfo2 = pointsBean.getPointsRedemptionInfo();
            if (pointsRedemptionInfo2 != null) {
                pointsRedemptionInfoItemList = pointsRedemptionInfo2.getPointsRedemptionInfoItemList();
                pointsRedemptionInfo = (PointsRedemptionInfo) lo.dramaboxapp(pointsRedemptionInfoItemList, i);
                if (pointsRedemptionInfo == null) {
                    SensorLog O = SensorLog.dramaboxapp.O();
                    String pageAlias = getPageAlias();
                    UserLayerInfo userLayerInfo = pointsRedemptionInfo.getUserLayerInfo();
                    String layerId = userLayerInfo != null ? userLayerInfo.getLayerId() : null;
                    userLayerInfo = pointsRedemptionInfo.getUserLayerInfo();
                    if (userLayerInfo != null) {
                        str = userLayerInfo.getGroupId();
                    }
                    O.Y(pageAlias, layerId, str, String.valueOf(pointsRedemptionInfo.getProductId()), pointsRedemptionInfo.getChangePoints());
                    Z6.dramabox dramabox = Z6.dramabox.dramabox;
                    if (dramabox.s2()) {
                        int Z1 = dramabox.Z1();
                        Integer changePoints = pointsRedemptionInfo.getChangePoints();
                        if (Z1 < (changePoints != null ? changePoints.intValue() : 0)) {
                            Y6.I.lO(getString(R.string.str_mymem_redemption_toast_2));
                        } else {
                            boolean z;
                            m mVar;
                            if (this.pointsRedemptionDialog == null) {
                                AppCompatActivity appCompatActivity = this.mActivity;
                                Intrinsics.checkNotNullExpressionValue(appCompatActivity, "mActivity");
                                this.pointsRedemptionDialog = new m(appCompatActivity);
                            }
                            int j0 = dramabox.j0();
                            if (j0 != 1) {
                                if (j0 != 2) {
                                    z = false;
                                    mVar = this.pointsRedemptionDialog;
                                    if (mVar != null) {
                                        mVar.yhj(getPageAlias(), pointsRedemptionInfo, i, this, z);
                                    }
                                }
                            }
                            z = true;
                            mVar = this.pointsRedemptionDialog;
                            if (mVar != null) {
                            }
                        }
                        return;
                    }
                    Y6.I.lO(getString(R.string.str_mymem_redemption_toast_1));
                    return;
                }
                return;
            }
        }
        pointsRedemptionInfoItemList = null;
        pointsRedemptionInfo = (PointsRedemptionInfo) lo.dramaboxapp(pointsRedemptionInfoItemList, i);
        if (pointsRedemptionInfo == null) {
        }
    }

    public void onReminderStateChanged() {
        updateCalendarReminderButton();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        Intrinsics.checkNotNullParameter(strArr, "permissions");
        Intrinsics.checkNotNullParameter(iArr, "grantResults");
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (!this.reserveCalendarDelegate.io(i, iArr)) {
            this.signInCalendarDelegate.RT(i, strArr, iArr);
        }
    }

    public void onResume() {
        this.performanceDelegate.io();
        super.onResume();
        this.reserveCalendarDelegate.O();
        if (this.isFirstRequest) {
            this.isFirstRequest = false;
            ((FragmentMembershipPointsV3Binding) this.mBinding).swq.JKi();
        }
        refreshPage();
        getTpPaymentAdapter().io();
        this.signInCalendarDelegate.l1();
    }

    public final void refreshPage() {
        ((MembershipPointsVM) this.mViewModel).djd(this.channel);
    }

    public void requestCalendarPermissions(String[] strArr, int i) {
        Intrinsics.checkNotNullParameter(strArr, "permissions");
        requestPermissions(strArr, i);
    }

    public final void reserve(String str, String str2, String str3, int i, String str4) {
        Intrinsics.checkNotNullParameter(str, "bookId");
        Intrinsics.checkNotNullParameter(str2, "bookName");
        Intrinsics.checkNotNullParameter(str3, "bookCover");
        Intrinsics.checkNotNullParameter(str4, "from");
        ((MembershipPointsVM) this.mViewModel).Jvf(str, str2, str3, i, str4);
    }

    public final void setChannel(int i) {
        this.channel = i;
    }

    public void setEventId(long j) {
        Z6.dramabox.dramabox.v5(j);
    }

    public final void setPushTaskId(String str) {
        Intrinsics.checkNotNullParameter(str, "pushTaskId");
        this.pushTaskId = str;
    }

    public void setReminderEnabled(boolean z) {
        Z6.dramabox.dramabox.w5(z);
    }

    public final void setSkuLocalCurrency(k9.dramaboxapp dramaboxapp) {
        Intrinsics.checkNotNullParameter(dramaboxapp, "<set-?>");
        this.skuLocalCurrency = dramaboxapp;
    }

    public final void setTpPaymentAdapter(e9.O o) {
        Intrinsics.checkNotNullParameter(o, "<set-?>");
        this.tpPaymentAdapter = o;
    }

    public void updateReminderSwitch(boolean z, Long l, Function0<Unit> function0, Function0<Unit> function02) {
        Intrinsics.checkNotNullParameter(function0, "onSuccess");
        Intrinsics.checkNotNullParameter(function02, "onError");
        ((MembershipPointsVM) this.mViewModel).slo(z, l, function0, function02);
    }
}

// By developer-krushna (https://github.com/developer-krushna/)

