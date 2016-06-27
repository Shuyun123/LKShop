package net.anumbrella.lkshop.model.bean;

/**
 * author：Anumbrella
 * Date：16/6/13 下午9:53
 */
public class CommentOrderDataModel {

    private int uid;
    private int pid;
    private int bid;
    private String commentContent;
    private int describe;
    private int deliver;
    private int service;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getDescribe() {
        return describe;
    }

    public void setDescribe(int describe) {
        this.describe = describe;
    }

    public int getDeliver() {
        return deliver;
    }

    public void setDeliver(int deliver) {
        this.deliver = deliver;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }
}
