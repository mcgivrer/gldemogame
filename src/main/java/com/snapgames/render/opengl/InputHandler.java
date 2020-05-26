package com.snapgames.render.opengl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import com.snapgames.sample.Game;

/**
 * InputHandler gaols consists in managing all input from any connected devices.
 * First is keyboard.
 *
 * @author Frédéric Delorme <frederic.delorme@gmail.com>
 * @since 2020
 */
public class InputHandler implements KeyListener {

	/**
	 * The current key states
	 */
	public boolean[] keys = new boolean[65536];

	// flag to represent state f the CTRL key
	public boolean control;
	// flag to represent state f the SHIFT key
	public boolean shift;
	// flag to represent state f the ALT key
	public boolean alt;
	// flag to represent state f the ALTGr key
	public boolean altGr;

	/**
	 * The previous state of each keys.
	 */
	public boolean[] previousKeys = new boolean[65536];

	/**
	 * Game input key listeners.
	 */
	public List<KeyListener> listeners = new ArrayList<>();

	/**
	 * The parent game.
	 */
	public Game game;

	/**
	 * Create the InputHandler system.
	 *
	 * @param g the parent game.
	 */
	public InputHandler(Game g) {

		game = g;
	}

	/**
	 * @see KeyListener#keyTyped(KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * @see KeyListener#keyPressed(KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		this.previousKeys[e.getKeyCode()] = this.keys[e.getKeyCode()];
		this.keys[e.getKeyCode()] = true;

		control = e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK;
		shift = e.getModifiersEx() == KeyEvent.SHIFT_DOWN_MASK;
		alt = e.getModifiersEx() == KeyEvent.ALT_DOWN_MASK;
		altGr = e.getModifiersEx() == KeyEvent.ALT_GRAPH_DOWN_MASK;

		for (KeyListener kl : listeners) {
			kl.keyPressed(e);
		}
	}

	/**
	 * @see KeyListener#keyReleased(KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		this.previousKeys[e.getKeyCode()] = this.keys[e.getKeyCode()];
		this.keys[e.getKeyCode()] = false;

		control = e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK;
		shift = e.getModifiersEx() == KeyEvent.SHIFT_DOWN_MASK;
		alt = e.getModifiersEx() == KeyEvent.ALT_DOWN_MASK;
		altGr = e.getModifiersEx() == KeyEvent.ALT_GRAPH_DOWN_MASK;

		for (KeyListener kl : listeners) {
			kl.keyReleased(e);
		}
	}

	/**
	 * Add a game key listener to the system.
	 *
	 * @param kl the KeyListener to be added.
	 */
	public void addListener(KeyListener kl) {
		if (!listeners.contains(kl)) {
			listeners.add(kl);
		}
	}

	/**
	 * Remove a specific game key listener.
	 *
	 * @param kl the KeyListener to be removed.
	 */
	public void remove(KeyListener kl) {
		if (!listeners.contains(kl)) {
			listeners.remove(kl);
		}
	}
}
