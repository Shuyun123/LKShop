package net.anumbrella.lkshop.model.bean;

/**
 * author：Anumbrella
 * Date：16/5/29 下午4:01
 */
public class UserDataModel {

    private String phoneNumber;
    private String userName;
    private String password;
    private String password_again;
    public String getPassword_again() {
        return password_again;
    }
    public void setPassword_again(String password_again) {
        this.password_again = password_again;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
