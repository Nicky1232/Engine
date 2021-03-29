package com.mygdx.zombie;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.zombie.screen.Sandbox;

public class ZombieEngine extends Game {

	public static final float WORLD_WIDTH = 800;
	public static final float WORLD_HEIGHT = 480;

	public static SpriteBatch 	batch;
	public static ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		batch 		  = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		setScreen(new Sandbox());
	}
}
