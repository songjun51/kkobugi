package kr.songjun51.kkobugi.models;

/**
 * Created by Chad on 7/9/16.
 */


public class User {
    /*
* 0 Facebook
* 1 Twitter
* */
    String name, profileurl, id;
    boolean isSilhoutte;
    int userType;

    public User(int userType, String name, String id, boolean isSilhouette, String url) {
        this.name = name;
        this.userType = userType;
        this.id = id;
        this.isSilhoutte = isSilhouette;
        this.profileurl = url;
    }

    public int getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }


    public String getProfileurl() {
        return profileurl;
    }

    public String getId() {
        return id;
    }

    public boolean isSilhoutte() {
        return isSilhoutte;
    }
}
