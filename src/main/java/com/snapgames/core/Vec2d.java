package com.snapgames.core;

import java.util.List;

public class Vec2d {

    public double x = 0;
    public double y = 0;

    public Vec2d() {
        super();
    }

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2d add(Vec2d v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vec2d multiply(double m) {
        return new Vec2d(x * m, y * m);
    }

    public Vec2d addAll(List<Vec2d> forces) {
        Vec2d r = new Vec2d();
        for (Vec2d f : forces) {
            r.add(f);
        }
        return r;
    }

}
