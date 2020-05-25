package com.snapgames.render.opengl;

public class GameObject {
    public int id;
    public String name;
    public float x = 0;
    public float y = 0;
    public Texture texture;

    public GameObject(String name, int x, int y, Texture texture) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

}