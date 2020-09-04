package com.snapgames.core;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.snapgames.sample.Game;

public class GOManager extends GameSystem {

    Map<String, GameObject> objects;

    public GOManager(Game g, GameConfig config) {
        super(g, config);
    }

    public void add(GameObject go) {
        objects.put(go.name, go);
    }

    public void remove(String gameObjectName) {
        if (objects.containsKey(gameObjectName)) {
            objects.remove(gameObjectName);
        }
    }

    public GameObject get(String name) {
        return objects.get(name);
    }

    public Collection<GameObject> getObjects() {
        return objects.values();
    }

    public void update(double elapsed) {
        for (Entry<String, GameObject> item : objects.entrySet()) {
            GameObject go = item.getValue();
            go.update(elapsed);
            go.forces.clear();
        }
    }

    @Override
    public void initialize(Game g, GameConfig c) {
        objects = new ConcurrentHashMap<>();
    }

    @Override
    public void dispose() {
        this.objects.clear();
    }
}
