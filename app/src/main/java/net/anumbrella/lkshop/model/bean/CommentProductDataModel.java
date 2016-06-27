package net.anumbrella.lkshop.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author：Anumbrella
 * Date：16/6/2 上午8:53
 */
public class CommentProductDataModel implements Parcelable {
    private int uid;
    private String commentContent;
    private int pid;
    private int likeNumber;
    private String commentTime;
    private int describe;
    private int service;
    private int deliver;
    private String userName;
    private String userIcon;

    protected CommentProductDataModel(Parcel in) {
        uid = in.readInt();
        commentContent = in.readString();
        pid = in.readInt();
        likeNumber = in.readInt();
        commentTime = in.readString();
        describe = in.readInt();
        service = in.readInt();
        deliver = in.readInt();
        userName = in.readString();
        userIcon = in.readString();
        img = in.readString();
        type = in.readInt();
        carrieroperator = in.readInt();
        color = in.readInt();
        storage = in.readInt();
        cid = in.readInt();
        productName = in.readString();
        subCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(commentContent);
        dest.writeInt(pid);
        dest.writeInt(likeNumber);
        dest.writeString(commentTime);
        dest.writeInt(describe);
        dest.writeInt(service);
        dest.writeInt(deliver);
        dest.writeString(userName);
        dest.writeString(userIcon);
        dest.writeString(img);
        dest.writeInt(type);
        dest.writeInt(carrieroperator);
        dest.writeInt(color);
        dest.writeInt(storage);
        dest.writeInt(cid);
        dest.writeString(productName);
        dest.writeInt(subCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentProductDataModel> CREATOR = new Creator<CommentProductDataModel>() {
        @Override
        public CommentProductDataModel createFromParcel(Parcel in) {
            return new CommentProductDataModel(in);
        }

        @Override
        public CommentProductDataModel[] newArray(int size) {
            return new CommentProductDataModel[size];
        }
    };

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    private String img;
    private int type;
    private int carrieroperator;
    private int color;
    private int storage;
    private int cid;
    private String productName;
    private int subCount;

    public CommentProductDataModel() {
    }


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getDescribe() {
        return describe;
    }

    public void setDescribe(int describe) {
        this.describe = describe;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getDeliver() {
        return deliver;
    }

    public void setDeliver(int deliver) {
        this.deliver = deliver;
    }

    public int getSubCount() {
        return subCount;
    }

    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }


    public static ProductDetailData getData() {
        return data;
    }

    public static void setData(ProductDetailData data) {
        CommentProductDataModel.data = data;
    }

    private static ProductDetailData data;

    public static ProductDetailData getProductDetailData() {
        if (data == null) {
            synchronized (CommentProductDataModel.class) {
                data = new ProductDetailData();
            }
        }
        return data;
    }


    public void setProductDetailData(ProductDetailData productDetailData) {
        data = productDetailData;
    }

    public static class ProductDetailData {
        public String productName;
        public float price;
        public String img;
        public int phoneStorage;
        public int phoneColor;
        public int phoneCarrieroperator;

        public int getPhoneStorage() {
            return phoneStorage;
        }

        public void setPhoneStorage(int phoneStorage) {
            this.phoneStorage = phoneStorage;
        }

        public int getPhoneColor() {
            return phoneColor;
        }

        public void setPhoneColor(int phoneColor) {
            this.phoneColor = phoneColor;
        }

        public int getPhoneCarrieroperator() {
            return phoneCarrieroperator;
        }

        public void setPhoneCarrieroperator(int phoneCarrieroperator) {
            this.phoneCarrieroperator = phoneCarrieroperator;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }


}
