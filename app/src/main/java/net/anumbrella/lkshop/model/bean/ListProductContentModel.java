package net.anumbrella.lkshop.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;

/**
 * author：Anumbrella
 * Date：16/5/26 上午10:43
 */
public class ListProductContentModel implements Parcelable {

    private int pid;

    private String title;

    private int type;

    private String imageUrl;

    private float price;

    private int color;

    private int storage;

    private int carrieroperator;

    private int sum;

    private int uid;


    public ListProductContentModel() {

    }

    protected ListProductContentModel(Parcel in) {
        pid = in.readInt();
        title = in.readString();
        type = in.readInt();
        imageUrl = in.readString();
        price = in.readFloat();
        color = in.readInt();
        storage = in.readInt();
        carrieroperator = in.readInt();
        sum = in.readInt();
        uid = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pid);
        dest.writeString(title);
        dest.writeInt(type);
        dest.writeString(imageUrl);
        dest.writeFloat(price);
        dest.writeInt(color);
        dest.writeInt(storage);
        dest.writeInt(carrieroperator);
        dest.writeInt(sum);
        dest.writeInt(uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListProductContentModel> CREATOR = new Creator<ListProductContentModel>() {
        @Override
        public ListProductContentModel createFromParcel(Parcel in) {
            return new ListProductContentModel(in);
        }

        @Override
        public ListProductContentModel[] newArray(int size) {
            return new ListProductContentModel[size];
        }
    };

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getCarrieroperator() {
        return carrieroperator;
    }

    public void setCarrieroperator(int carrieroperator) {
        this.carrieroperator = carrieroperator;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
