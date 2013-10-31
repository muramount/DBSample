package com.example.dbsample.entity;

import java.io.Serializable;

public class StudentEntity implements Serializable {

    private int id = 0;
    private String name = "";
    private int age = 0;
    private String memo = "";
    private byte[] image = null;

    /**
     * Constructor
     * @param id
     * @param name
     * @param age
     * @param memo
     * @param image
     */
    public StudentEntity(int id, String name, int age, String memo, byte[] image) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.memo = memo;
        this.image = image;
    }

    /**
     * getter, setter
     */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
}
