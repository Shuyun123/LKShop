package net.anumbrella.lkshop.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author：Anumbrella
 * Date：16/6/9 下午5:50
 */
public class OrderDataModel implements Parcelable {

    private String img;

    private float price;

    private int sum;

    private int isDeliver;

    private int isComment;

    private int isPay;

    private int type;

    private int carrieroperator;

    private int color;

    private int storage;

    private int bid;

    private int pid;

    private int uid;

    private String productName;

    private float total;

    protected OrderDataModel(Parcel in) {
        img = in.readString();
        price = in.readFloat();
        sum = in.readInt();
        isDeliver = in.readInt();
        isComment = in.readInt();
        isPay = in.readInt();
        type = in.readInt();
        carrieroperator = in.readInt();
        color = in.readInt();
        storage = in.readInt();
        bid = in.readInt();
        pid = in.readInt();
        uid = in.readInt();
        productName = in.readString();
        total = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeFloat(price);
        dest.writeInt(sum);
        dest.writeInt(isDeliver);
        dest.writeInt(isComment);
        dest.writeInt(isPay);
        dest.writeInt(type);
        dest.writeInt(carrieroperator);
        dest.writeInt(color);
        dest.writeInt(storage);
        dest.writeInt(bid);
        dest.writeInt(pid);
        dest.writeInt(uid);
        dest.writeString(productName);
        dest.writeFloat(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderDataModel> CREATOR = new Creator<OrderDataModel>() {
        @Override
        public OrderDataModel createFromParcel(Parcel in) {
            return new OrderDataModel(in);
        }

        @Override
        public OrderDataModel[] newArray(int size) {
            return new OrderDataModel[size];
        }
    };

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(int isDeliver) {
        this.isDeliver = isDeliver;
    }

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCarrieroperator() {
        return carrieroperator;
    }

    public void setCarrieroperator(int carrieroperator) {
        this.carrieroperator = carrieroperator;
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

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
