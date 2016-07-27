package kr.songjun51.kkobugi.models;

/**
 * Created by Chad on 7/25/16.
 */
public class StatusData {
    String ok, nModified, n;

    public StatusData(String ok, String nModified, String n) {
        this.ok = ok;
        this.nModified = nModified;
        this.n = n;
    }

    public String getOk() {
        return ok;
    }

    public String getnModified() {
        return nModified;
    }

    public String getN() {
        return n;
    }
}
