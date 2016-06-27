package net.anumbrella.lkshop.model.bean;

/**
 * author：Anumbrella
 * Date：16/5/25 下午7:14
 */

public class NavDrawerItemModel {

    private String title;
    private int icon;
    //drawer菜单图标的背景颜色
    private int tint;


    public NavDrawerItemModel(String title, int icon, int tint) {
        this.title = title;
        this.icon = icon;
        this.tint = tint;
    }


    public String getTitle() {
        return this.title;
    }

    public int getIcon() {
        return this.icon;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    public int getTint() {
        return tint;
    }

    public void setTint(int tint) {
        this.tint = tint;
    }


}
