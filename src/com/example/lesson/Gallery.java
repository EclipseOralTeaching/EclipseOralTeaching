package com.example.lesson;


import java.util.ArrayList;

/**
 * Created by TianYuan on 2016/7/7.
 */
public class Gallery {
    private String GalleryName;
    private int PicAmount;
    private ArrayList<Integer> Pics;

//    public Gallery(ArrayList<Integer> pics,String name){
//        GalleryName="new Gallery";
//        Pics=pics;
//        PicAmount=Pics.size();
//        GalleryName=name;
//    }

    //重载Gallery的构造函数
    public Gallery(ArrayList<Integer> pics){
        GalleryName="new Gallery";
        Pics=pics;
        PicAmount=Pics.size();
    }

    public void setGalleryName(String name){
        GalleryName=name;
    }

    public String getGalleryName(){
        return GalleryName;
    }

    public int getPicsAmount(){
        return PicAmount;
    }

    public void addPics(int[] pics){
        for(int i=0;i<pics.length;i++){
            Pics.add(Pics.size()+1,pics[i]);
        }
        PicAmount+=pics.length;
    }

    public void delPics(int[] index){
        for(int i=0;i<index.length;i++){
            Pics.remove(index[i]);
        }
        PicAmount-=index.length;
    }

    public Gallery(Gallery gallery){
        this.GalleryName=gallery.GalleryName;
        this.Pics=gallery.Pics;
        this.PicAmount=gallery.Pics.size();
    }
}
