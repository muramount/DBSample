package com.example.dbsample.entity;

import java.io.Serializable;
import java.lang.reflect.Constructor;

public class SchoolEntity implements Serializable {

    private int id = 0;
    private String name = "";
    private String phone = "";
    private String address ="";
    private String memo = "";
    private byte[] image = null;


    /**
     * {@link Constructor}
     * @param id
     * @param name
     * @param phone
     * @param address
     * @param memo
     * @param image
     */
    public SchoolEntity(int id, String name, String phone, String address, String memo, byte[] image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
