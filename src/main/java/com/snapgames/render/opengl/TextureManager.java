package com.snapgames.render.opengl;

import static org.lwjgl.opengl.GL11.glBindTexture;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class TextureManager {
    Map<String, Texture> textures = new HashMap<>();

    public Texture load(String imagePath) {
        Texture t = null;
        BufferedImage image;
        try {
            image = ImageIO.read(Texture.class.getResourceAsStream(imagePath));
            t = createTextureFromImage(imagePath, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public List<Texture> loadTexturesFrom(String imagePath, Map<String, Rectangle> slices) {
        List<Texture> texs = new ArrayList<>();
        BufferedImage image;
        try {
            image = ImageIO.read(Texture.class.getResourceAsStream(imagePath));
            for (Entry<String, Rectangle> imageSlice : slices.entrySet()) {
                int x = imageSlice.getValue().x;
                int y = imageSlice.getValue().y;
                int w = imageSlice.getValue().width;
                int h = imageSlice.getValue().height;
                texs.add(createTextureFromImage(imageSlice.getKey(), image.getSubimage(x, y, w, h)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return texs;
    }

    public Texture createTextureFromImage(String name, BufferedImage image) {
        Texture t;
        t = new Texture(name, image.getWidth(), image.getHeight());
        int[] pixels_raw = new int[t.width * t.height * 4];
        image.getRGB(0, 0, t.width, t.height, pixels_raw, 0, t.width);
        ByteBuffer pixels = BufferUtils.createByteBuffer(t.width * t.height * 4);

        for (int y = 0; y < t.height; y++) {
            for (int x = 0; x < t.width; x++) {
                Color c = new Color(image.getRGB(x, y));
                pixels.put((byte) (c.getRed())); // RED
                pixels.put((byte) (c.getGreen())); // GREEN
                pixels.put((byte) (c.getBlue())); // BLUE
                pixels.put((byte) (c.getAlpha())); // ALPHA
            }
        }
        pixels.flip();
        t.id = GL11.glGenTextures();
        glBindTexture(GL11.GL_TEXTURE_2D, t.id);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, t.width, t.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,
                pixels);
        addTexture(t);
        image.flush();
        image = null;

        return t;
    }

    private void addTexture(Texture t) {
        if (!textures.containsKey(t.name)) {
            textures.put(t.name, t);
        }
    }

    public void removeTexture(String name) {
        if (textures.containsKey(name)) {
            textures.remove(name);
        }
    }

    public void removeTexture(Texture t) {
        removeTexture(t.name);
    }

    public Texture get(String name) {
        return textures.get(name);
    }

    public void bind(Texture t) {
        glBindTexture(GL11.GL_TEXTURE_2D, textures.get(t.name).id);
    }

}