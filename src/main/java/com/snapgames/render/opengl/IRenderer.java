package com.snapgames.render.opengl;

import java.util.Map;

import com.snapgames.sample.Game;

public interface IRenderer {

    void render(Game g, Map<String,Object> attributes);
    
}