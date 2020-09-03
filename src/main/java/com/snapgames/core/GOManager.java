package com.snapgames.core;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.snapgames.sample.Game;

public class GOManager {

    Map<String, GameObject> objects = new ConcurrentHashMap<>();

    private Game parentGame;

    public GOManager(Game g) {
        parentGame = g;
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
}
