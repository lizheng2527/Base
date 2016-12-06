
package com.zdhx.androidbase.entity;

import java.io.Serializable;

/**
 * 用户实体类
 */
public class User implements Serializable {

	private String loginName;
	private String loginPsw;
    private String memberName;
    private String memberTruename;
    private String memberSex;
    private String memberMobile;
    private String memberEmail;
    private String memberId;
    private String memberAvatar; //头像
    private String isBuy;
    private String signCodeState;
    private String memberTime;
    private String availablePredeposit;
    private String isAllowtalk;
    private String memberConsumePoints;//积分
    private String memberLoginNum;
    private String memberSnsvisitnum;
    private String freezePredeposit;
    private String memberCredit;
    private String informAllow;
    private String noEvaluationOrder;
    private String isDel;
    private String favStoreCount;
    private String favGoodsCount;

    private String noPayOrder; //待支付
    private String noFilledOrder;//待发货
    private String noReceiveOrder; //
    private String finishOrder;
    private String memberAreainfo;
    private String memberBirthday;

    private String memberNameCode;
    
    private boolean isLogin;

    public String getMemberNameCode() {
        return memberNameCode;
    }

    public void setMemberNameCode(String memberNameCode) {
        this.memberNameCode = memberNameCode;
    }

    public String getMemberBirthday() {
        return memberBirthday;
    }

    public void setMemberBirthday(String memberBirthday) {
        this.memberBirthday = memberBirthday;
    }

    public String getMemberAreainfo() {
        return memberAreainfo;
    }

    public void setMemberAreainfo(String memberAreainfo) {
        this.memberAreainfo = memberAreainfo;
    }

    public String getMemberTruename() {
        return memberTruename;
    }

    public void setMemberTruename(String memberTruename) {
        this.memberTruename = memberTruename;
    }

    public String getNoPayOrder() {
        return noPayOrder;
    }

    public void setNoPayOrder(String noPayOrder) {
        this.noPayOrder = noPayOrder;
    }

    public String getNoFilledOrder() {
        return noFilledOrder;
    }

    public void setNoFilledOrder(String noFilledOrder) {
        this.noFilledOrder = noFilledOrder;
    }

    public String getFinishOrder() {
        return finishOrder;
    }

    public void setFinishOrder(String finishOrder) {
        this.finishOrder = finishOrder;
    }

    public String getFavStoreCount() {
        return favStoreCount;
    }

    public void setFavStoreCount(String favStoreCount) {
        this.favStoreCount = favStoreCount;
    }

    public String getFavGoodsCount() {
        return favGoodsCount;
    }

    public void setFavGoodsCount(String favGoodsCount) {
        this.favGoodsCount = favGoodsCount;
    }

    public String getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(String memberSex) {
        this.memberSex = memberSex;
    }

    public String getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    public String getSignCodeState() {
        return signCodeState;
    }

    public void setSignCodeState(String signCodeState) {
        this.signCodeState = signCodeState;
    }

    public String getMemberTime() {
        return memberTime;
    }

    public void setMemberTime(String memberTime) {
        this.memberTime = memberTime;
    }

    public String getNoReceiveOrder() {
        return noReceiveOrder;
    }

    public void setNoReceiveOrder(String noReceiveOrder) {
        this.noReceiveOrder = noReceiveOrder;
    }

    public String getAvailablePredeposit() {
        return availablePredeposit;
    }

    public void setAvailablePredeposit(String availablePredeposit) {
        this.availablePredeposit = availablePredeposit;
    }

    public String getIsAllowtalk() {
        return isAllowtalk;
    }

    public void setIsAllowtalk(String isAllowtalk) {
        this.isAllowtalk = isAllowtalk;
    }

    public String getMemberConsumePoints() {
        return memberConsumePoints;
    }

    public void setMemberConsumePoints(String memberConsumePoints) {
        this.memberConsumePoints = memberConsumePoints;
    }

    public String getMemberLoginNum() {
        return memberLoginNum;
    }

    public void setMemberLoginNum(String memberLoginNum) {
        this.memberLoginNum = memberLoginNum;
    }

    public String getMemberSnsvisitnum() {
        return memberSnsvisitnum;
    }

    public void setMemberSnsvisitnum(String memberSnsvisitnum) {
        this.memberSnsvisitnum = memberSnsvisitnum;
    }

    public String getFreezePredeposit() {
        return freezePredeposit;
    }

    public void setFreezePredeposit(String freezePredeposit) {
        this.freezePredeposit = freezePredeposit;
    }

    public String getMemberCredit() {
        return memberCredit;
    }

    public void setMemberCredit(String memberCredit) {
        this.memberCredit = memberCredit;
    }

    public String getInformAllow() {
        return informAllow;
    }

    public void setInformAllow(String informAllow) {
        this.informAllow = informAllow;
    }

    public String getNoEvaluationOrder() {
        return noEvaluationOrder;
    }

    public void setNoEvaluationOrder(String noEvaluationOrder) {
        this.noEvaluationOrder = noEvaluationOrder;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }


    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		if (!isLogin) {
			this.memberAvatar = "";
			this.memberTruename = "";
			this.memberId = "";
			this.memberMobile = "";
		}
		this.isLogin = isLogin;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPsw() {
		return loginPsw;
	}

	public void setLoginPsw(String loginPsw) {
		this.loginPsw = loginPsw;
	}
}
