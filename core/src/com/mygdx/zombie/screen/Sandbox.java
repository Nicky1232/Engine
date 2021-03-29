package com.mygdx.zombie.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
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
import com.mygdx.zombie.actors.Pipe;
import com.mygdx.zombie.actors.ShapeDrawable;
import com.mygdx.zombie.swingterface.SelectButton;
import com.mygdx.zombie.swingterface.Status;
import com.mygdx.zombie.utils.camera.ScrollingCamera;

import static com.mygdx.zombie.ZombieEngine.WORLD_HEIGHT;
import static com.mygdx.zombie.ZombieEngine.WORLD_WIDTH;
import static com.mygdx.zombie.info.GameInfo.LINE_WIDHT;

public class Sandbox implements Screen {

    private FPSLogger fpsLogger;
    private Viewport viewport;
    private ScrollingCamera camera;

    private final ShapeRenderer shapeRenderer = ZombieEngine.shapeRenderer;
    private final SpriteBatch   batch         = ZombieEngine.batch;

    private Array<Pipe> pipes;

    @Override
    public void show() {

        pipes = new Array<>();
        pipes.add(new Pipe(50, 150, 500));
        pipes.add(new Pipe(50, 250, 300));
        pipes.add(new Pipe(50, 350));

        camera = new ScrollingCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        camera.bindViewport(viewport);

        fpsLogger = new FPSLogger();
        Gdx.input.setInputProcessor(new SandboxIA());
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glLineWidth(LINE_WIDHT);

        Vector2 cursorWorldPosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        shapeRenderer.setProjectionMatrix(camera.combined);
        for(Pipe pipe : pipes) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            pipe.draw(shapeRenderer);
            shapeRenderer.end();
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        StatusManager.act(shapeRenderer, cursorWorldPosition);
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

    private final class SandboxIA extends InputAdapter {
        @Override
        public boolean scrolled(float amountX, float amountY) {
            camera.onScroll(amountY, amountY >= 0);
            return true;
        }
    }

    public static final class StatusManager {

        private static ShapeDrawable object;

        private StatusManager() {}

        ///////////////////////////////////////////////////////
        // This is called when new Selection Button is picked
        ///////////////////////////////////////////////////////
        public static void signal() {
            if(Status.selection == SelectButton.SELECTION) {
                object = null;
            } else if(Status.selection == SelectButton.PIPE) {
                object = new Pipe(0, 0);
            }
        }

        private static void act(ShapeRenderer shapeDrawable, Vector2 cursorWorldPosition) {
            if(object != null) {
                object.draw(shapeDrawable);
                object.setPosition((int)cursorWorldPosition.x,
                        (int)cursorWorldPosition.y, true);
            }
        }

    }
}
