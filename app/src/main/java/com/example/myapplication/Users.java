package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("firstname")
    @Expose
    private String firstname;

    @SerializedName("age")
    @Expose
    private Integer age;

    @SerializedName("password")
    @Expose
    private String password;

    public Users(String name, String login, String email, String firstname, Integer age, String password) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.firstname = firstname;
        this.age = age;
        this.password = password;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
