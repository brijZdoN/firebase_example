package com.example.brijj.jsonexample;

import android.net.Uri;

import com.google.firebase.database.Exclude;

public class Post {
    String data,uname,uri,key;
    public Post(){}

    public Post(String uname,String data,String uri) {
        this.uname=uname;
        this.data = data;
        this.uri=uri;
    }


    public Post(String user, String s) {
        this.uname=user;
        this.data=s;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
