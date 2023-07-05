package com.example.bottomnavigationdemo;

public class UserId {
    public static String uid;
    public static String username;
    public static String idregister;


    public UserId() {


    }
    public UserId(String id) {
        // Constructeur privé pour empêcher l'instanciation directe
        this.uid=id;

    }
    public UserId(String id,String username){
        this.username=username;
    }





    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setIdregister(String register){
        this.idregister=register;
    }
}
