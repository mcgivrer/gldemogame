package com.snapgames.core;

import com.snapgames.sample.Game;

public abstract class GameSystem {

    protected Game game;
    protected GameConfig config;

    protected GameSystem() {

    }

    public GameSystem(Game g, GameConfig config) {
        this.game = g;
        this.config = config;
    }

    public abstract void initialize(Game game, GameConfig config);

    public abstract void dispose();

    public String getName() {
        return this.getClass().getName();
    };

}
