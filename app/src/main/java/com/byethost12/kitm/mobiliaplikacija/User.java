package com.byethost12.kitm.mobiliaplikacija;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mokytojas on 2018-01-31.
 */

public class User {
    private int id;
    private String userlevel;
    private String username;
    private String password;
    private String email;
    private static final String PREFERENCES_FILE_NAME = "com.byethost12.kitm.mobiliaplikacija";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String REMEMBER_ME_KEY = "rememberMe";

    private SharedPreferences sharedPreferences;


    // konstruktorius skirtas sqlLite
    public User() {

    }
    //skirtas register activity registruojant nauja vartotoja
    public User(String userlevel, String username, String password, String email) {
        this.userlevel = userlevel;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    //konstruktorius skirtas login langui
    public User(Context context){
        this.sharedPreferences = context.getSharedPreferences
                (User.PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
    }
//geteriai seteriai skirti login activity prisijungimo langui
    public String getUsernameForLogin() {
        return this.sharedPreferences.getString(USERNAME_KEY,"");
    }
    public void setUsernameForLogin(String username) {
        this.sharedPreferences.edit().putString(USERNAME_KEY,username).commit();
    }
    public String getPasswordForLogin() {
        return this.sharedPreferences.getString(PASSWORD_KEY,"");
    }
    public void setPasswordForLogin(String password) {
        this.sharedPreferences.edit().putString(PASSWORD_KEY,password).commit();
    }
    public boolean isRemembered(){
        return this.sharedPreferences.getBoolean(REMEMBER_ME_KEY,false);
    }
    public void setRememberMeKeyForLogin(boolean rememberMe){
      this.sharedPreferences.edit().putBoolean(REMEMBER_ME_KEY,rememberMe).commit();
    }
    //sitie geteriai seteriai skirti register activity langui
    public String getUsernameForRegister() {
        return username;
    }
    public void setUsernameForRegister(String username) {
        this.username = username;
    }

    public String getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordForRegister() {
        return password;
    }
    public void setPasswordForRegister(String password) {
        this.password = password;
    }
    public String getEmailForRegister() {
        return email;
    }
    public void setEmailForRegister(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userlevel='" + userlevel + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
