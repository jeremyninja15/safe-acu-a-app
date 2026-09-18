//
// Decompiled by Jadx - 51

// By developer-krushna (https://github.com/developer-krushna/)

package com.lib.data.membership;

import com.lib.data.ShareDrama;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata
public final class MembershipCardInfo {
    private String avatarUrl;
    private final Boolean claimedPoints;
    private final Boolean hitNewPointsUI;
    private final Integer isUnsubscribe;
    private final String joinMembershipTitle;
    private Long memberExpireTimeStamp;
    private Integer memberType;
    private String membershipCardShareDescription;
    private String membershipCardShareLink;
    private Integer membershipStatus;
    private String membershipTitle;
    private String nickName;
    private Integer points;
    private final Integer pointsReceiveStatus;
    private final Integer remainExpireDays;
    private List<String> shareApps;
    private String shareId;
    private ShareDrama shareMembershipCardInfo;

    public MembershipCardInfo(String str, Long l, Integer num, String str2, Integer num2, String str3, Integer num3, ShareDrama shareDrama, String str4, String str5, String str6, List<String> list, Boolean bool, Boolean bool2, Integer num4, Integer num5, String str7, Integer num6) {
        this.avatarUrl = str;
        this.memberExpireTimeStamp = l;
        this.memberType = num;
        this.nickName = str2;
        this.points = num2;
        this.membershipTitle = str3;
        this.membershipStatus = num3;
        this.shareMembershipCardInfo = shareDrama;
        this.shareId = str4;
        this.membershipCardShareLink = str5;
        this.membershipCardShareDescription = str6;
        this.shareApps = list;
        this.claimedPoints = bool;
        this.hitNewPointsUI = bool2;
        this.isUnsubscribe = num4;
        this.remainExpireDays = num5;
        this.joinMembershipTitle = str7;
        this.pointsReceiveStatus = num6;
    }

    public /* synthetic */ MembershipCardInfo(String str, Long l, Integer num, String str2, Integer num2, String str3, Integer num3, ShareDrama shareDrama, String str4, String str5, String str6, List list, Boolean bool, Boolean bool2, Integer num4, Integer num5, String str7, Integer num6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        int i2 = i;
        String str8 = "";
        this((i2 & 1) != 0 ? str8 : str, (i2 & 2) != 0 ? Long.valueOf(0) : l, (i2 & 4) != 0 ? Integer.valueOf(0) : num, (i2 & 8) != 0 ? str8 : str2, (i2 & 16) != 0 ? Integer.valueOf(0) : num2, (i2 & 32) != 0 ? str8 : str3, (i2 & 64) != 0 ? Integer.valueOf(MembershipStatus.NoMem.ordinal()) : num3, shareDrama, (i2 & 256) != 0 ? str8 : str4, (i2 & 512) != 0 ? str8 : str5, (i2 & 1024) != 0 ? str8 : str6, (i2 & 2048) != 0 ? new ArrayList() : list, (i2 & 4096) != 0 ? Boolean.FALSE : bool, (i2 & 8192) != 0 ? Boolean.FALSE : bool2, (i2 & 16384) != 0 ? Integer.valueOf(0) : num4, (32768 & i2) != 0 ? Integer.valueOf(0) : num5, (65536 & i2) != 0 ? null : str7, (i2 & 131072) != 0 ? Integer.valueOf(0) : num6);
    }

    public static /* synthetic */ MembershipCardInfo copy$default(MembershipCardInfo membershipCardInfo, String str, Long l, Integer num, String str2, Integer num2, String str3, Integer num3, ShareDrama shareDrama, String str4, String str5, String str6, List list, Boolean bool, Boolean bool2, Integer num4, Integer num5, String str7, Integer num6, int i, Object obj) {
        MembershipCardInfo membershipCardInfo2 = membershipCardInfo;
        int i2 = i;
        return membershipCardInfo.copy((i2 & 1) != 0 ? membershipCardInfo2.avatarUrl : str, (i2 & 2) != 0 ? membershipCardInfo2.memberExpireTimeStamp : l, (i2 & 4) != 0 ? membershipCardInfo2.memberType : num, (i2 & 8) != 0 ? membershipCardInfo2.nickName : str2, (i2 & 16) != 0 ? membershipCardInfo2.points : num2, (i2 & 32) != 0 ? membershipCardInfo2.membershipTitle : str3, (i2 & 64) != 0 ? membershipCardInfo2.membershipStatus : num3, (i2 & 128) != 0 ? membershipCardInfo2.shareMembershipCardInfo : shareDrama, (i2 & 256) != 0 ? membershipCardInfo2.shareId : str4, (i2 & 512) != 0 ? membershipCardInfo2.membershipCardShareLink : str5, (i2 & 1024) != 0 ? membershipCardInfo2.membershipCardShareDescription : str6, (i2 & 2048) != 0 ? membershipCardInfo2.shareApps : list, (i2 & 4096) != 0 ? membershipCardInfo2.claimedPoints : bool, (i2 & 8192) != 0 ? membershipCardInfo2.hitNewPointsUI : bool2, (i2 & 16384) != 0 ? membershipCardInfo2.isUnsubscribe : num4, (i2 & 32768) != 0 ? membershipCardInfo2.remainExpireDays : num5, (i2 & 65536) != 0 ? membershipCardInfo2.joinMembershipTitle : str7, (i2 & 131072) != 0 ? membershipCardInfo2.pointsReceiveStatus : num6);
    }

    public final String component1() {
        return this.avatarUrl;
    }

    public final String component10() {
        return this.membershipCardShareLink;
    }

    public final String component11() {
        return this.membershipCardShareDescription;
    }

    public final List<String> component12() {
        return this.shareApps;
    }

    public final Boolean component13() {
        return this.claimedPoints;
    }

    public final Boolean component14() {
        return this.hitNewPointsUI;
    }

    public final Integer component15() {
        return this.isUnsubscribe;
    }

    public final Integer component16() {
        return this.remainExpireDays;
    }

    public final String component17() {
        return this.joinMembershipTitle;
    }

    public final Integer component18() {
        return this.pointsReceiveStatus;
    }

    public final Long component2() {
        return this.memberExpireTimeStamp;
    }

    public final Integer component3() {
        return this.memberType;
    }

    public final String component4() {
        return this.nickName;
    }

    public final Integer component5() {
        return this.points;
    }

    public final String component6() {
        return this.membershipTitle;
    }

    public final Integer component7() {
        return this.membershipStatus;
    }

    public final ShareDrama component8() {
        return this.shareMembershipCardInfo;
    }

    public final String component9() {
        return this.shareId;
    }

    public final MembershipCardInfo copy(String str, Long l, Integer num, String str2, Integer num2, String str3, Integer num3, ShareDrama shareDrama, String str4, String str5, String str6, List<String> list, Boolean bool, Boolean bool2, Integer num4, Integer num5, String str7, Integer num6) {
        return new MembershipCardInfo(str, l, num, str2, num2, str3, num3, shareDrama, str4, str5, str6, list, bool, bool2, num4, num5, str7, num6);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MembershipCardInfo)) {
            return false;
        }
        MembershipCardInfo membershipCardInfo = (MembershipCardInfo) obj;
        return Intrinsics.areEqual(this.avatarUrl, membershipCardInfo.avatarUrl) && Intrinsics.areEqual(this.memberExpireTimeStamp, membershipCardInfo.memberExpireTimeStamp) && Intrinsics.areEqual(this.memberType, membershipCardInfo.memberType) && Intrinsics.areEqual(this.nickName, membershipCardInfo.nickName) && Intrinsics.areEqual(this.points, membershipCardInfo.points) && Intrinsics.areEqual(this.membershipTitle, membershipCardInfo.membershipTitle) && Intrinsics.areEqual(this.membershipStatus, membershipCardInfo.membershipStatus) && Intrinsics.areEqual(this.shareMembershipCardInfo, membershipCardInfo.shareMembershipCardInfo) && Intrinsics.areEqual(this.shareId, membershipCardInfo.shareId) && Intrinsics.areEqual(this.membershipCardShareLink, membershipCardInfo.membershipCardShareLink) && Intrinsics.areEqual(this.membershipCardShareDescription, membershipCardInfo.membershipCardShareDescription) && Intrinsics.areEqual(this.shareApps, membershipCardInfo.shareApps) && Intrinsics.areEqual(this.claimedPoints, membershipCardInfo.claimedPoints) && Intrinsics.areEqual(this.hitNewPointsUI, membershipCardInfo.hitNewPointsUI) && Intrinsics.areEqual(this.isUnsubscribe, membershipCardInfo.isUnsubscribe) && Intrinsics.areEqual(this.remainExpireDays, membershipCardInfo.remainExpireDays) && Intrinsics.areEqual(this.joinMembershipTitle, membershipCardInfo.joinMembershipTitle) && Intrinsics.areEqual(this.pointsReceiveStatus, membershipCardInfo.pointsReceiveStatus);
    }

    public final String getAvatarUrl() {
        return this.avatarUrl;
    }

    public final Boolean getClaimedPoints() {
        return this.claimedPoints;
    }

    public final Boolean getHitNewPointsUI() {
        return this.hitNewPointsUI;
    }

    public final String getJoinMembershipTitle() {
        return this.joinMembershipTitle;
    }

    public final Long getMemberExpireTimeStamp() {
        return this.memberExpireTimeStamp;
    }

    public final Integer getMemberType() {
        return this.memberType;
    }

    public final String getMembershipCardShareDescription() {
        return this.membershipCardShareDescription;
    }

    public final String getMembershipCardShareLink() {
        return this.membershipCardShareLink;
    }

    public final Integer getMembershipStatus() {
        return this.membershipStatus;
    }

    public final String getMembershipTitle() {
        return this.membershipTitle;
    }

    public final String getNickName() {
        return this.nickName;
    }

    public final Integer getPoints() {
        return this.points;
    }

    public final Integer getPointsReceiveStatus() {
        return this.pointsReceiveStatus;
    }

    public final Integer getRemainExpireDays() {
        return this.remainExpireDays;
    }

    public final List<String> getShareApps() {
        return this.shareApps;
    }

    public final String getShareId() {
        return this.shareId;
    }

    public final ShareDrama getShareMembershipCardInfo() {
        return this.shareMembershipCardInfo;
    }

    public int hashCode() {
        String str = this.avatarUrl;
        int i = 0;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        Long l = this.memberExpireTimeStamp;
        hashCode = (hashCode + (l == null ? 0 : l.hashCode())) * 31;
        Integer num = this.memberType;
        hashCode = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        String str2 = this.nickName;
        hashCode = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        num = this.points;
        hashCode = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        str2 = this.membershipTitle;
        hashCode = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        num = this.membershipStatus;
        hashCode = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        ShareDrama shareDrama = this.shareMembershipCardInfo;
        hashCode = (hashCode + (shareDrama == null ? 0 : shareDrama.hashCode())) * 31;
        str2 = this.shareId;
        hashCode = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        str2 = this.membershipCardShareLink;
        hashCode = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        str2 = this.membershipCardShareDescription;
        hashCode = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        List list = this.shareApps;
        hashCode = (hashCode + (list == null ? 0 : list.hashCode())) * 31;
        Boolean bool = this.claimedPoints;
        hashCode = (hashCode + (bool == null ? 0 : bool.hashCode())) * 31;
        bool = this.hitNewPointsUI;
        hashCode = (hashCode + (bool == null ? 0 : bool.hashCode())) * 31;
        num = this.isUnsubscribe;
        hashCode = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        num = this.remainExpireDays;
        hashCode = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        str2 = this.joinMembershipTitle;
        hashCode = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        num = this.pointsReceiveStatus;
        if (num != null) {
            i = num.hashCode();
        }
        return hashCode + i;
    }

    /* JADX WARNING: Missing block: B:4:0x000f, code:
            if (r0.intValue() != r1) goto L_0x0011;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isMem() {
        Integer num = this.membershipStatus;
        int ordinal = MembershipStatus.PaidMem.ordinal();
        if (num == null) {
        }
        num = this.membershipStatus;
        ordinal = MembershipStatus.FreeMem.ordinal();
        if (num != null) {
            if (num.intValue() == ordinal) {
                return true;
            }
        }
        return false;
    }

    public final Integer isUnsubscribe() {
        return this.isUnsubscribe;
    }

    public final void setAvatarUrl(String str) {
        this.avatarUrl = str;
    }

    public final void setMemberExpireTimeStamp(Long l) {
        this.memberExpireTimeStamp = l;
    }

    public final void setMemberType(Integer num) {
        this.memberType = num;
    }

    public final void setMembershipCardShareDescription(String str) {
        this.membershipCardShareDescription = str;
    }

    public final void setMembershipCardShareLink(String str) {
        this.membershipCardShareLink = str;
    }

    public final void setMembershipStatus(Integer num) {
        this.membershipStatus = num;
    }

    public final void setMembershipTitle(String str) {
        this.membershipTitle = str;
    }

    public final void setNickName(String str) {
        this.nickName = str;
    }

    public final void setPoints(Integer num) {
        this.points = num;
    }

    public final void setShareApps(List<String> list) {
        this.shareApps = list;
    }

    public final void setShareId(String str) {
        this.shareId = str;
    }

    public final void setShareMembershipCardInfo(ShareDrama shareDrama) {
        this.shareMembershipCardInfo = shareDrama;
    }

    public String toString() {
        String str = this.avatarUrl;
        Long l = this.memberExpireTimeStamp;
        Integer num = this.memberType;
        String str2 = this.nickName;
        Integer num2 = this.points;
        String str3 = this.membershipTitle;
        Integer num3 = this.membershipStatus;
        ShareDrama shareDrama = this.shareMembershipCardInfo;
        String str4 = this.shareId;
        String str5 = this.membershipCardShareLink;
        String str6 = this.membershipCardShareDescription;
        List list = this.shareApps;
        Boolean bool = this.claimedPoints;
        Boolean bool2 = this.hitNewPointsUI;
        Integer num4 = this.isUnsubscribe;
        Integer num5 = this.remainExpireDays;
        String str7 = this.joinMembershipTitle;
        Integer num6 = this.pointsReceiveStatus;
        StringBuilder stringBuilder = new StringBuilder();
        Integer num7 = num6;
        stringBuilder.append("MembershipCardInfo(avatarUrl=");
        stringBuilder.append(str);
        stringBuilder.append(", memberExpireTimeStamp=");
        stringBuilder.append(l);
        stringBuilder.append(", memberType=");
        stringBuilder.append(num);
        stringBuilder.append(", nickName=");
        stringBuilder.append(str2);
        stringBuilder.append(", points=");
        stringBuilder.append(num2);
        stringBuilder.append(", membershipTitle=");
        stringBuilder.append(str3);
        stringBuilder.append(", membershipStatus=");
        stringBuilder.append(num3);
        stringBuilder.append(", shareMembershipCardInfo=");
        stringBuilder.append(shareDrama);
        stringBuilder.append(", shareId=");
        stringBuilder.append(str4);
        stringBuilder.append(", membershipCardShareLink=");
        stringBuilder.append(str5);
        stringBuilder.append(", membershipCardShareDescription=");
        stringBuilder.append(str6);
        stringBuilder.append(", shareApps=");
        stringBuilder.append(list);
        stringBuilder.append(", claimedPoints=");
        stringBuilder.append(bool);
        stringBuilder.append(", hitNewPointsUI=");
        stringBuilder.append(bool2);
        stringBuilder.append(", isUnsubscribe=");
        stringBuilder.append(num4);
        stringBuilder.append(", remainExpireDays=");
        stringBuilder.append(num5);
        stringBuilder.append(", joinMembershipTitle=");
        stringBuilder.append(str7);
        stringBuilder.append(", pointsReceiveStatus=");
        stringBuilder.append(num7);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

// By developer-krushna (https://github.com/developer-krushna/)

