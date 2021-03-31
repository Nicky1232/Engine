package com.mygdx.zombie.screen.sandbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.zombie.ZombieEngine;
import com.mygdx.zombie.actors.ColorBall;
import com.mygdx.zombie.actors.Entity;
import com.mygdx.zombie.actors.Pipe;
import com.mygdx.zombie.actors.Spike;
import com.mygdx.zombie.utils.camera.ScrollingCamera;

import java.util.HashMap;

import static com.mygdx.zombie.ZombieEngine.WORLD_HEIGHT;
import static com.mygdx.zombie.ZombieEngine.WORLD_WIDTH;
import static com.mygdx.zombie.info.GameInfo.GL_LINE_WIDTH;

public final class Sandbox implements Screen {

    private FPSLogger fpsLogger;
    private final ShapeRenderer shapeRenderer = ZombieEngine.shapeRenderer;
    private final SpriteBatch   batch         = ZombieEngine.batch;

///////////////////////////////////////////////////////////////////////////////////
// New Bulgarian University (NBU)
// Code I don't understand starts here
///////////////////////////////////////////////////////////////////////////////////
    
    final HashMap<Class<? extends Entity>,
            Array<Entity>> entityMap = new HashMap<>();

    Viewport viewport;
    ScrollingCamera camera;

    @Override
    public void show() {

        entityMap.put(Pipe.class, new Array<>());
        entityMap.put(ColorBall.class, new Array<>());
        entityMap.put(Spike.class, new Array<>());

        entityMap.get(Pipe.class).add(new Pipe(50, 150, 500));
        entityMap.get(Pipe.class).add(new Pipe(50, 250, 300));
        entityMap.get(Pipe.class).add(new Pipe(50, 350));

///////////////////////////////////////////////////////////////////////////////////
// New Bulgarian University (NBU)
// Code I don't understand ends here
///////////////////////////////////////////////////////////////////////////////////

        camera = new ScrollingCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        camera.bindViewport(viewport);

        fpsLogger = new FPSLogger();
        Gdx.input.setInputProcessor(new SandboxInput(this));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glLineWidth(GL_LINE_WIDTH);

        Vector2 cursorWorldPosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        SandboxStatusManager.act(shapeRenderer, cursorWorldPosition);
        drawShapes();

        shapeRenderer.end();

        fpsLogger.log();
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void drawShapes() {
        entityMap.values().forEach((entities -> {
            for (Entity entity : entities) entity.draw(shapeRenderer);
        }));
    }
}
