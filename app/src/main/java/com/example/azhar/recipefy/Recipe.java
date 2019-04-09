package com.example.azhar.recipefy;

/**
 * Created by Azhar on 3/29/2019.
 */

public class Recipe {
    String title;
    String url;
    String imgUrl;

    public Recipe(String title, String url, String imgUrl) {
        this.title = title;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
