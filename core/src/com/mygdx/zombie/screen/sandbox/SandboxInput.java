package com.mygdx.zombie.screen.sandbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.zombie.actors.Entity;
import com.mygdx.zombie.swingterface.SelectButton;
import com.mygdx.zombie.swingterface.Status;
import com.mygdx.zombie.utils.camera.ScrollingCamera;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;


final class SandboxInput extends InputAdapter {

    private final HashMap<Class<? extends Entity>, Array<Entity>> entityMap;
    private final Viewport viewport;
    private final ScrollingCamera camera;

    public SandboxInput(Sandbox context) {
        this.viewport  = context.viewport;
        this.camera    = context.camera;
        this.entityMap = context.entityMap;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 cursorWorldPosition =
                viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        if(Status.selection == SelectButton.SELECTION) {

            AtomicBoolean objectSelected = new AtomicBoolean(false);
            entityMap.values().forEach((entities -> {
                for(Entity entity : entities) {
                    if (entity.overlaps((int) cursorWorldPosition.x, (int) cursorWorldPosition.y)) {
                        SandboxStatusManager.mark(entity);
                        objectSelected.set(true);
                    }
                }
            }));

            if (!objectSelected.get()) {
                if( SandboxStatusManager.object != null) {
                    SandboxStatusManager.object.setDrawBoundingRectangle(false);
                    SandboxStatusManager.object = null;
                    SandboxStatusManager.isBoundToCursor = false;
                }
            }
        }

        if(Status.selection != SelectButton.SELECTION
                && SandboxStatusManager.object != null) {
            SandboxStatusManager.addActor(entityMap);
        }

        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        camera.onScroll(amountY, amountY >= 0);
        return true;
    }
}