package com.akkru.user.akkru.model;

/**
 * Created by dar9617 on 8/25/18.
 *
 * @Author [Dian Arief]
 * @Email sg8.dian@gmail.com
 * @Github https://github.com/dar9617
 * @TIM Akkrue
 */

public class CategoryModel {
    private int barThumbnail;
    private String barName;
    private String barAddress;
    private String barStatus;

    public CategoryModel(int barThumbnail, String barName, String barAddress, String barStatus) {
        this.barThumbnail = barThumbnail;
        this.barName = barName;
        this.barAddress = barAddress;
        this.barStatus = barStatus;
    }

    public int getBarThumbnail() {
        return barThumbnail;
    }

    public void setBarThumbnail(int barThumbnail) {
        this.barThumbnail = barThumbnail;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getBarAddress() {
        return barAddress;
    }

    public void setBarAddress(String barAddress) {
        this.barAddress = barAddress;
    }

    public String getBarStatus() {
        return barStatus;
    }

    public void setBarStatus(String barStatus) {
        this.barStatus = barStatus;
    }
}
