package com.snapgames.sample;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import com.snapgames.core.GOManager;
import com.snapgames.core.GameConfig;
import com.snapgames.core.GameObject;
import com.snapgames.core.Vec2d;
import com.snapgames.render.opengl.GLRenderer;
import com.snapgames.render.opengl.TextureManager;

import org.lwjgl.glfw.GLFW;
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

    public GameConfig config;
    public TextureManager tm;
    public GLRenderer glr;
    public GOManager gom;

    // internal window identifier.
    public long window;

    public double UPS = 60;
    public double timeUps = 1000 / UPS;

    public boolean exit = false;

    public Game() {

        config = new GameConfig("res.gldemogame");
        tm = new TextureManager();
        glr = new GLRenderer(this, config);
        gom = new GOManager(this);
        initialize(config);
    }

    private void initialize(GameConfig config) {
        this.UPS = config.getDoule("game.ups", 60);
        this.timeUps = 1000 / UPS;
    }

    private void load() {
        Map<String, Rectangle> textures = new HashMap<>();
        textures.put("player1", new Rectangle(0, 48, 32, 32));
        textures.put("player2", new Rectangle(32, 48, 32, 32));
        textures.put("player3", new Rectangle(64, 48, 32, 32));
        tm.loadTexturesFrom("/res/images/tileset-1.png", textures);

        GameObject player = new GameObject("player", new Vec2d(0, 0), tm.get("player1"));
        player.rugosity = 0.23;
        gom.add(player);
        glr.add(player);
    }

    public boolean isExit() {
        return GLFW.glfwWindowShouldClose(glr.getWindow()) || exit;
    }

    /**
     * the main entry for the game string point.
     */
    public void run() {
        load();
        try {
            loop();
        } catch (InterruptedException e) {
            System.err.println("An Error occured :" + e.getMessage());
        }
        quit();
    }

    private void loop() throws InterruptedException {
        double prevTime = GLFW.glfwGetTime();
        while (!isExit()) {
            double time = GLFW.glfwGetTime();

            beginLoop();

            input();
            double elapsed = (time - prevTime);

            update(elapsed);
            render();

            endLoop();
            if (elapsed > timeUps) {
                Thread.sleep((long) (timeUps - elapsed));
            }
            prevTime = time;
        }
    }

    private void beginLoop() {
        GLFW.glfwPollEvents();
    }

    public void input() {
        window = glr.getWindow();
        GameObject player = gom.get("player");

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == GL11.GL_TRUE) {
            player.forces.add(new Vec2d(20, 0.0));
            player.direction = 1;
        } else if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT) == GL11.GL_TRUE) {
            player.forces.add(new Vec2d(-20, 0.0));
            player.direction = -1;
        }

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_UP) == GL11.GL_TRUE) {
            player.forces.add(new Vec2d(0.0, 20));
        } else if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DOWN) == GL11.GL_TRUE) {
            player.forces.add(new Vec2d(0.0, -20));
        }

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GL11.GL_TRUE) {
            this.exit = true;
        }
    }

    public void update(double time) {
        gom.update(time);
    }

    private void render() {

        glr.render();

    }

    private void endLoop() {

        // Nothing special on end loop to be performed.

    }

    private void quit() {
        GLFW.glfwTerminate();
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.run();
    }

}