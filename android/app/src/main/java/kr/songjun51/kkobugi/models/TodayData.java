package kr.songjun51.kkobugi.models;

/**
 * Created by Chad on 7/25/16.
 */
public class TodayData {
    String user_id, percent,date;

    public TodayData(String user_id, String percent, String date) {
        this.user_id = user_id;
        this.percent = percent;
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPercent() {
        return percent;
    }

    public String getDate() {
        return date;
    }
}
