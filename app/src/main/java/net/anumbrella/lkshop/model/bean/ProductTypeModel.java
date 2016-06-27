package net.anumbrella.lkshop.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author：Anumbrella
 * Date：16/5/26 下午7:17
 */
public class ProductTypeModel implements Parcelable {
    private String productName;
    private int id;
    private int icon;
    private int phoneType;
    private String titleName;


    public ProductTypeModel(int id, int icon, String productName, String titleName, int phoneType) {
        this.id = id;
        this.icon = icon;
        this.productName = productName;
        this.titleName = titleName;
        this.phoneType = phoneType;
    }

    protected ProductTypeModel(Parcel in) {
        productName = in.readString();
        id = in.readInt();
        icon = in.readInt();
        phoneType = in.readInt();
        titleName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeInt(id);
        dest.writeInt(icon);
        dest.writeInt(phoneType);
        dest.writeString(titleName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductTypeModel> CREATOR = new Creator<ProductTypeModel>() {
        @Override
        public ProductTypeModel createFromParcel(Parcel in) {
            return new ProductTypeModel(in);
        }

        @Override
        public ProductTypeModel[] newArray(int size) {
            return new ProductTypeModel[size];
        }
    };

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(int phoneType) {
        this.phoneType = phoneType;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
