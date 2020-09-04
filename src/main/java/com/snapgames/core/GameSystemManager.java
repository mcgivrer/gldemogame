package com.snapgames.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.snapgames.sample.Game;

public class GameSystemManager {

    private Game game;
    private GameConfig config;

    private Map<Class<?>, GameSystem> systems;

    public GameSystemManager(Game game, GameConfig config) {
        this.game = game;
        this.config = config;
        systems = new ConcurrentHashMap<>();
    }

    public void add(GameSystem s) {
        if (s != null) {
            Class<? extends GameSystem> systemType = s.getClass();
            systems.put(systemType, s);
            s.initialize(game, config);
        }
    }

	@SuppressWarnings("unchecked")
    public <T extends GameSystem> T get(Class<T> systemName) {
        return (T) systems.get(systemName);
    }

    public void dispose() {
        for (GameSystem s : systems.values()) {
            s.dispose();
        }
    }
}
