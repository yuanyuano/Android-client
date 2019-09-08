package com.example.news.Bean;

public class MenuList {
    private int image;
    private String menu_name;

    public MenuList(int image, String menu_name) {
        this.image = image;
        this.menu_name = menu_name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }
}
