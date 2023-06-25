package com.example.labourbooking;

public class HelperClass {
   String name;
    String userType;
    String email;
    String phonenumber;
    String location;
    String password;

    String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public HelperClass(String name, String userType, String email,String phonenumber,String location, String password, String uid) {
        this.name = name;
        this.email=email;
        this.phonenumber=phonenumber;
        this.location=location;
        this.password=password;
        this.userType=userType;
        this.uid = uid;
    }

  public HelperClass() {};
}
