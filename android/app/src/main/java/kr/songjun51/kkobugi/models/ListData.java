package kr.songjun51.kkobugi.models;

/**
 * Created by Chad on 7/25/16.
 */
public class ListData {
    public String title, percentage;
    public int ranking;

    public ListData(String title, String percentage) {
        this.title = title;
        this.percentage = percentage;
    }

    public ListData(String title, String percentage, int ranking) {
        this.title = title;
        this.percentage = percentage;
        this.ranking = ranking;
    }

    public String getTitle() {
        return title;
    }

    public String getPercentage() {
        return percentage;
    }

    public int getRanking() {
        return ranking;
    }
}
