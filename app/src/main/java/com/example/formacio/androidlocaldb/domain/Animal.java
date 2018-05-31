package com.example.formacio.androidlocaldb.domain;

import java.util.Date;

public class Animal {
  private  String name;
    private  int age;
    private String type;
    private Date date;
    private String photoB64;

    public Animal() {
    }

    public Animal(String name) {
        this.name = name;
    }

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Animal(String name, int age, String type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public Animal(String name, int age, String type, Date date) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.date = date;
    }

    public Animal(String name, int age, String type, Date date, String photoB64) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.date = date;
        this.photoB64 = photoB64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhotoB64() {
        return photoB64;
    }

    public void setPhotoB64(String photoB64) {
        this.photoB64 = photoB64;
    }
}
