package com.snapgames.core;

import com.snapgames.render.opengl.Texture;

public class GameObject {
    public int id;
    public String name;
    public float x = 0;
    public float y = 0;
    public float dx = 0;
    public float dy = 0;
    public int direction = 1;
    public Texture texture;

    public GameObject(String name, int x, int y, Texture texture) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    public void update(){
        x += dx;
        y += dy;
    }

}