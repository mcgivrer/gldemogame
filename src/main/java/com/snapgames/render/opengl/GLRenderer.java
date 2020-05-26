package com.snapgames.render.opengl;

import java.util.ArrayList;
import java.util.List;

import com.snapgames.core.GameObject;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.snapgames.sample.Game;

public class GLRenderer {
	long window;
	private Game game;
	private List<GameObject> objects = new ArrayList<>();

	public GLRenderer(Game g) {
		game = g;
		if (GLFW.glfwInit()) {
			window = GLFW.glfwCreateWindow(640, 480, g.config.getString("game.title"), 0, 0);
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
        GL11.glTranslatef(g.x, g.y, 0);
        GL11.glScalef(g.direction,1,1);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(-0.1f, 0.1f);

        GL11.glTexCoord2f(1,0);
        GL11.glVertex2f(0.1f, 0.1f);

        GL11.glTexCoord2f(1,1);
        GL11.glVertex2f(0.1f, -0.1f);

        GL11.glTexCoord2f(0,1);
        GL11.glVertex2f(-0.1f, -0.1f);

        GL11.glEnd();
        GL11.glTranslatef(-g.x, -g.y,0);
    }

	public long getWindow() {
		return window;
	}
}
