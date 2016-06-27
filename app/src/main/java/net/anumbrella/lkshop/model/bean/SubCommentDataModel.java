package net.anumbrella.lkshop.model.bean;

/**
 * author：Anumbrella
 * Date：16/6/17 下午11:20
 */
public class SubCommentDataModel {
    private int cid;
    private String subCommentContent;
    private String subTime;
    private int likeNumber;
    private int uid;
    private String userImg;
    private String userName;
    private int sid;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getSubCommentContent() {
        return subCommentContent;
    }

    public void setSubCommentContent(String subCommentContent) {
        this.subCommentContent = subCommentContent;
    }

    public String getSubTime() {
        return subTime;
    }

    public void setSubTime(String subTime) {
        this.subTime = subTime;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
