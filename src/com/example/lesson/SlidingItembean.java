package com.example.lesson;

public class SlidingItembean {
    private int image;
    private String num;
    private String name;
    private String path;
    private String setTop;

    public SlidingItembean() {
        super();
    }

    public SlidingItembean(String num,int image, String name, String path, String setTop) {
        super();
        this.image = image;
        this.num = num;
        this.name = name;
        this.path = path;
        this.setTop = setTop;
    }



    public String getSetTop() {
        return setTop;
    }



    public void setSetTop(String setTop) {
        this.setTop = setTop;
    }

    public int getImage(){
        return image;
    }
    public void setImage(int image){
        this.image = image;
    }

    public String getNum() {
        return num;
    }

//    public void setNum(String num) {
//        this.num = num;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
