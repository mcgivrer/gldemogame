package com.snapgames.sample;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.snapgames.render.opengl.GLRenderer;
import com.snapgames.core.GameObject;
import com.snapgames.render.opengl.TextureManager;

/**
 * A simple class to create a Game with OpenGL rendering capabilities.
 * 
 * @author Frédéric Delorme<frederic.delorme@gmail.com>
 * @since 2020
 * 
 * @see org.lwjgl.glfw.GLFW
 */
public class Game {

    public ResourceBundle config = ResourceBundle.getBundle("res.gldemogame");
    public TextureManager tm = new TextureManager();
    public GLRenderer  glr ;
    // internal window identifier.
    public long window;

    GameObject player;

    public Game() {
        initializeGraphicContext(config);

    }

    private void initializeGraphicContext(ResourceBundle config) {
        glr = new GLRenderer(this);
    }

    private void load() {
        Map<String,Rectangle> textures = new HashMap<>();
        textures.put("player1",new Rectangle(0,48,32,32));
        textures.put("player2",new Rectangle(32,48,32,32));
        textures.put("player3",new Rectangle(64,48,32,32));
        tm.loadTexturesFrom("/res/images/tileset-1.png", textures);

        player = new GameObject("perso",0,0,tm.get("player2"));
        glr.add(player);
    }

    public boolean isExit() {
        return GLFW.glfwWindowShouldClose(glr.getWindow());
    }

    /**
     * the main entry for the game string point.
     */
    public void run() {
        load();
        loop();
        quit();
    }

    private void loop() {
        while (!isExit()) {

            beginLoop();

            input();
            update();
            render();

            endLoop();
        }
    }

    private void beginLoop() {
        GLFW.glfwPollEvents();
    }

    public void input() {
        long window = glr.getWindow();

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == GL11.GL_TRUE) {
            player.dx = 0.01f;
            player.direction=1;
        }else if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT) == GL11.GL_TRUE) {
            player.dx =- 0.01f;
            player.direction=-1;
        }else{
            player.dx*=0.3f;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_UP) == GL11.GL_TRUE) {
            player.dy = 0.01f;
        } else if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DOWN) == GL11.GL_TRUE) {
            player.dy = -0.01f;
        }else{
            player.dy*=0.3f;
        }

    }

    public void update() {
        player.update();
    }

    private void render() {

        glr.render();

    }

    private void endLoop() {

    }

    private void quit() {
        GLFW.glfwTerminate();
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.run();
    }

}