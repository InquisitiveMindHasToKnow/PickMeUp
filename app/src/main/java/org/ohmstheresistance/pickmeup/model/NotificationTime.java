package org.ohmstheresistance.pickmeup.model;

public class NotificationTime {

    private int _id;
    private String notificationTime;

    public NotificationTime(int _id, String notificationTime) {
        this._id = _id;
        this.notificationTime = notificationTime;
    }

    public NotificationTime() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    @Override
    public String toString() {
        return "NotificationTime{" +
                "_id = " + _id +
                "notification_time=" + notificationTime + '\'' +
                '}';
    }

    public static NotificationTime from(String time) {

        NotificationTime notificationTime = new NotificationTime();

        notificationTime.notificationTime = time;

        return notificationTime;
    }
}

