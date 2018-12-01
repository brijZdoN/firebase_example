package com.example.brijj.jsonexample;
public class User
{  String username,emailid,phoneNo;
    public User()
    {

    }

    public User(String username, String emailid, String phoneNo) {
        this.username = username;
        this.emailid = emailid;
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}

