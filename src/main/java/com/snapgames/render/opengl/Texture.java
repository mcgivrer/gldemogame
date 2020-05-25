package com.snapgames.render.opengl;

public class Texture {
    public String name;
    public int id;
    public int width;
    public int height;

    public Texture(String name, int width, int height) {
       this.name = name;
       this.width  =width;
       this.height= height;
    }

}