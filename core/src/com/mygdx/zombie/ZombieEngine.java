package com.mygdx.zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ZombieEngine extends ApplicationAdapter {

	private static final float WORLD_WIDTH = 800;
	private static final float WORLD_HEIGHT = 480;

	private Viewport viewport;
	private OrthographicCamera camera;

	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {

		camera = new OrthographicCamera();

		viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	private final float baseRatio = 0.4f;
	private float updateRatio = baseRatio;

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

//		if(updateRatio < 0) {
//			Vector2 vector = new Vector2(Gdx.input.getX(), Gdx.input.getY());
//			Vector2 result = viewport.unproject(vector);
//			System.out.println("X=" + result.x + " Y=" + result.y);
//			updateRatio = baseRatio;
//		} else updateRatio -= Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			System.out.println("W");
			camera.position.y += 2;
			System.out.println(camera.position.y);
		}

		camera.update();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public void resize(int width, int height) {
//		System.out.println("width=" + width + " height=" + height);
		viewport.update(width, height);
	}
}
