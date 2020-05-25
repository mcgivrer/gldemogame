package com.snapgames.render.opengl;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

/**
 * A simple class to create a Game with OpenGL rendering capabilities.
 * 
 * @author Frédéric Delorme<frederic.delorme@gmail.com>
 * @since 2020
 * 
 * @see org.lwjgl.glfw.GLFW
 */
public class Game {

    ResourceBundle config = ResourceBundle.getBundle("res.gldemogame");
    TextureManager tm = new TextureManager();
    // internal window identifier.
    long window;

    GameObject player;

    public Game() {
        initializeGraphicContext(config);
    }

    private void initializeGraphicContext(ResourceBundle config) {
        if (GLFW.glfwInit()) {
            window = GLFW.glfwCreateWindow(640, 480, config.getString("game.title"), 0, 0);
            GLFW.glfwShowWindow(window);
            GLFW.glfwMakeContextCurrent(window);
            GL.createCapabilities();
            GL11.glEnable(GL11.GL_TEXTURE_2D);

        } else {
            System.err.println("Unable to create GL window");
            System.exit(1);
        }
    }

    private void load() {
        Map<String,Rectangle> textures = new HashMap<>();
        textures.put("player",new Rectangle(0,48,32,32));
        tm.loadTexturesFrom("/res/images/tileset-1.png", textures);

        player = new GameObject("perso",0,0,tm.get("player"));
    }

    public boolean isExit() {
        return GLFW.glfwWindowShouldClose(window);
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
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == GL11.GL_TRUE) {
            player.x += 0.01f;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT) == GL11.GL_TRUE) {
            player.x -= 0.01f;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_UP) == GL11.GL_TRUE) {
            player.y += 0.01f;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DOWN) == GL11.GL_TRUE) {
            player.y -= 0.01f;
        }
    }

    public void update() {

    }

    private void render() {
        GL11.glMatrixMode(GL11.GL_2D);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);


        GL11.glPushMatrix();

        tm.bind(player.texture);
        GL11.glTranslatef(player.x, player.y, 0);
        GL11.glBegin(GL11.GL_QUADS);
        
        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(-0.5f, 0.5f);

        GL11.glTexCoord2f(1,0);
        GL11.glVertex2f(0.5f, 0.5f);

        GL11.glTexCoord2f(1,1);
        GL11.glVertex2f(0.5f, -0.5f);

        GL11.glTexCoord2f(0,1);
        GL11.glVertex2f(-0.5f, -0.5f);

        GL11.glEnd();

        GL11.glPopMatrix();

        GLFW.glfwSwapBuffers(window);
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