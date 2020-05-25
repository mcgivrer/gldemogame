package com.snapgames.render.opengl;

import java.util.Map;

public interface IRenderer {

    void render(Game g, Map<String,Object> attributes);
    
}