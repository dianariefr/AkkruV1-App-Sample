package com.akkru.user.akkru;

public class RecommendationModel {

    private int cafeImage;
    private String cafeTitle;
    private String cafeLocation;
    private String cafeStatus;

    public RecommendationModel() {

    }

    public RecommendationModel(int cafeImage, String cafeTitle, String cafeLocation, String cafeStatus) {
        this.cafeImage = cafeImage;
        this.cafeTitle = cafeTitle;
        this.cafeLocation = cafeLocation;
        this.cafeStatus = cafeStatus;
    }

    public int getCafeImage() {
        return cafeImage;
    }

    public void setCafeImage(int cafeImage) {
        this.cafeImage = cafeImage;
    }

    public String getCafeTitle() {
        return cafeTitle;
    }

    public void setCafeTitle(String cafeTitle) {
        this.cafeTitle = cafeTitle;
    }

    public String getCafeLocation() {
        return cafeLocation;
    }

    public void setCafeLocation(String cafeLocation) {
        this.cafeLocation = cafeLocation;
    }

    public String getCafeStatus() {
        return cafeStatus;
    }

    public void setCafeStatus(String cafeStatus) {
        this.cafeStatus = cafeStatus;
    }
}
