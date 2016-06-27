package net.anumbrella.lkshop.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author：Anumbrella
 * Date：16/5/25 下午9:17
 */
public class RecommendContentModel implements Parcelable {

    private int type;

    private int pid;

    private String title;

    private String tip;

    private boolean isListType;

    private String imageUrl;

    private float price;

    private boolean judgeType;

    private int color;

    private int storage;

    private int carrieroperator;

    private int uid;

    public RecommendContentModel() {
    }

    protected RecommendContentModel(Parcel in) {
        type = in.readInt();
        pid = in.readInt();
        title = in.readString();
        tip = in.readString();
        isListType = in.readByte() != 0;
        imageUrl = in.readString();
        price = in.readFloat();
        judgeType = in.readByte() != 0;
        color = in.readInt();
        storage = in.readInt();
        carrieroperator = in.readInt();
        uid = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(pid);
        dest.writeString(title);
        dest.writeString(tip);
        dest.writeByte((byte) (isListType ? 1 : 0));
        dest.writeString(imageUrl);
        dest.writeFloat(price);
        dest.writeByte((byte) (judgeType ? 1 : 0));
        dest.writeInt(color);
        dest.writeInt(storage);
        dest.writeInt(carrieroperator);
        dest.writeInt(uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecommendContentModel> CREATOR = new Creator<RecommendContentModel>() {
        @Override
        public RecommendContentModel createFromParcel(Parcel in) {
            return new RecommendContentModel(in);
        }

        @Override
        public RecommendContentModel[] newArray(int size) {
            return new RecommendContentModel[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isListType() {
        return isListType;
    }

    public void setListType(boolean listType) {
        isListType = listType;
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

    public boolean isJudgeType() {
        return judgeType;
    }

    public void setJudgeType(boolean judgeType) {
        this.judgeType = judgeType;
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
