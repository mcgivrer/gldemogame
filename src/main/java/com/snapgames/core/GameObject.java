package com.snapgames.core;

import java.util.ArrayList;
import java.util.List;

import com.snapgames.render.opengl.Texture;

public class GameObject {

    public static int index = 0;
    public int id = (index++);

    public String name = String.format("%s_%d", "gameObject_", id);

    public Vec2d pos = new Vec2d();
    public Vec2d vel = new Vec2d();
    public Vec2d acc = new Vec2d();

    public List<Vec2d> forces = new ArrayList<>();

    public double rugosity = 0;
    public int direction = 1;

    public Texture texture;

    public GameObject(String name, Vec2d pos, Texture texture) {
        this.name = name;
        this.pos = pos;
        this.texture = texture;
    }

    public void update(double elapsed) {
        Vec2d r = new Vec2d().addAll(forces);
        vel = vel.add(r.multiply(elapsed));
        pos.add(vel.multiply(elapsed));
        vel = vel.multiply(rugosity);
    }
}