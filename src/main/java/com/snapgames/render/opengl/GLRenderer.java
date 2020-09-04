package com.snapgames.render.opengl;

import java.util.ArrayList;
import java.util.List;

import com.snapgames.core.GameConfig;
import com.snapgames.core.GameObject;
import com.snapgames.core.GameSystem;
import com.snapgames.sample.Game;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class GLRenderer extends GameSystem implements IRenderer {
	long window;

	public int width;
	public int height;
	public String title; 

	private List<GameObject> objects;

	public GLRenderer() {
	}

	public GLRenderer(Game g, GameConfig config) {
		super(g, config);
	}

	@Override
	public void initialize(Game g, GameConfig c) {
		objects = new ArrayList<>();
		width = config.getInteger("game.screen.width", 640);
		height = config.getInteger("game.screen.height", 480);
		title = config.getString("game.screen.title", "OpenGl Window");
		createWindow();
	}

	private void createWindow() {

		if (GLFW.glfwInit()) {
			window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
			GLFW.glfwShowWindow(window);
			GLFW.glfwMakeContextCurrent(window);
			GL.createCapabilities();
			GL11.glEnable(GL11.GL_TEXTURE_2D);

		} else {
			System.err.println("Unable to create GL window");
			System.exit(1);
		}
	}

	public void add(GameObject g) {
		objects.add(g);
	}

	public void render() {
		GL11.glMatrixMode(GL11.GL_2D);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glPushMatrix();

		// draw a GameObject
		for (GameObject g : objects) {
			drawGameObject(g);
		}
		// end draw a GameObject

		GL11.glPopMatrix();

		GLFW.glfwSwapBuffers(window);
	}

	private void drawGameObject(GameObject g) {
		game.tm.bind(g.texture);
		GL11.glTranslated(g.pos.x, g.pos.y, 0);
		GL11.glScaled(g.direction, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(-0.1f, 0.1f);

		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(0.1f, 0.1f);

		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(0.1f, -0.1f);

		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(-0.1f, -0.1f);

		GL11.glEnd();
		GL11.glTranslated(-g.pos.x, -g.pos.y, 0);
	}

	public long getWindow() {
		return window;
	}

	@Override
	public void dispose() {
		this.objects.clear();
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

}
