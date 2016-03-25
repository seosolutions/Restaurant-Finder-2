package me.jgao.restaurant_finder.model;

/**
 * Created by jianxin on 3/24/16.
 */
public class Restaurant {

    private int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    private String name;
    private String displayPhone;
    private Double distance;
    private String imageUrl;
    private String ratingImgUrl;
    private Double rating;
    private int reviewCount;
    private String snippetImgUrl;
    private String snippetText;
    private Double latitude;
    private Double longitude;
    private String displayAddress;

    public Restaurant(String name, String displayPhone, Double distance, String imageUrl,
                      String ratingImgUrl, Double rating, int reviewCount,
                      String snippetImgUrl, String snippetText, Double latitude, Double longitude,
                      String displayAddress, int pos) {
        this.name = name;
        this.displayPhone = displayPhone;
        this.distance = distance;
        this.imageUrl = imageUrl;
        this.ratingImgUrl = ratingImgUrl;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.snippetImgUrl = snippetImgUrl;
        this.snippetText = snippetText;
        this.latitude = latitude;
        this.longitude = longitude;
        this.displayAddress = displayAddress;
        this.pos = pos;
    }

    public String getDisplayAddress() {
        return displayAddress;
    }

    public void setDisplayAddress(String displayAddress) {
        this.displayAddress = displayAddress;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRatingImgUrl() {
        return ratingImgUrl;
    }

    public void setRatingImgUrl(String ratingImgUrl) {
        this.ratingImgUrl = ratingImgUrl;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getSnippetImgUrl() {
        return snippetImgUrl;
    }

    public void setSnippetImgUrl(String snippetImgUrl) {
        this.snippetImgUrl = snippetImgUrl;
    }

    public String getSnippetText() {
        return snippetText;
    }

    public void setSnippetText(String snippetText) {
        this.snippetText = snippetText;
    }



}
