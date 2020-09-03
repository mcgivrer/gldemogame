package com.snapgames.render.opengl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.snapgames.core.GameConfig;
import com.snapgames.core.GameObject;
import com.snapgames.sample.Game;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class GLRenderer {
	long window;
	private Game game;
	private List<GameObject> objects = new ArrayList<>();

	public GLRenderer(Game g, GameConfig config) {
		game = g;

		int width = config.getInteger("game.screen.width", 640);
		int height = config.getInteger("game.screen.height", 480);
		String title = config.getString("game.screen.title", "OpenGl Window");
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
}
