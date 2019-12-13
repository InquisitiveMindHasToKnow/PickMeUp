package org.ohmstheresistance.pickmeup.model;

public class UserInfo {

    private int _id;
    private String userName;

    public UserInfo(int _id, String userName) {
        this._id = _id;
        this.userName = userName;
    }

    public UserInfo() {

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "_id = " + _id +
                "user_name=" + userName + '\'' +
                '}';
    }

    public static UserInfo from(String name) {

        UserInfo userInfo = new UserInfo();
        userInfo.userName = name;

        return userInfo;
    }
}
