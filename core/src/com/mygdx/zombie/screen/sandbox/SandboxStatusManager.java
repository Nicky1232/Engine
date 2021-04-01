package com.mygdx.zombie.screen.sandbox;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.zombie.actors.ColorBall;
import com.mygdx.zombie.actors.Entity;
import com.mygdx.zombie.actors.Pipe;
import com.mygdx.zombie.actors.Spike;
import com.mygdx.zombie.swingterface.Buffer;
import com.mygdx.zombie.swingterface.Status;
import com.mygdx.zombie.swingterface.Swingterface;

import java.util.HashMap;

public final class SandboxStatusManager {

    static Entity object;
    static boolean isBoundToCursor;

    ///////////////////////////////////////////////////////
    // This is called when new Selection Button is picked
    ///////////////////////////////////////////////////////
    public static void signal() {

        if(object != null)
            object.setDrawBoundingRectangle(false);

        if(Status.selection == null) {
            object = null;
        } else if(Status.selection == Pipe.class) {
            object = new Pipe(0, 0);
        } else if(Status.selection == ColorBall.class) {
            object = new ColorBall(0, 0);
        } else if(Status.selection == Spike.class) {
            object = new Spike(0, 0);
        }

        if(object != null) {
            object.setDrawBoundingRectangle(true);
            isBoundToCursor = true;
        }

    }

    public static void updateEntity(Buffer buffer) {

        Buffer.Common common = buffer.getCommon();

        object.setPosition(common.x, common.y, false);
        object.setSize(common.width, common.height);

    }

    static void act(ShapeRenderer shapeDrawable, Vector2 cursorWorldPosition) {
        if(object != null) {
            object.draw(shapeDrawable);
            if(isBoundToCursor) {
                object.setPosition((int) cursorWorldPosition.x,
                        (int) cursorWorldPosition.y, true);
            }
        }
    }

    static void addActor(HashMap<Class<? extends Entity>, Array<Entity>> entityMap) {

        if (object != null) {
            object.setDrawBoundingRectangle(false);
            isBoundToCursor = false;

            if (object instanceof Pipe)             entityMap.get(Pipe.class).add(object);
            else if (object instanceof ColorBall)   entityMap.get(ColorBall.class).add(object);
            else if (object instanceof Spike)       entityMap.get(Spike.class).add(object);

            Status.selection = null;
            object = null;
        }

    }

    static void mark(Entity object) {

        if(SandboxStatusManager.object != null)
            SandboxStatusManager.object.setDrawBoundingRectangle(false);

        SandboxStatusManager.object = object;
        object.setDrawBoundingRectangle(true);
        isBoundToCursor = false;

        //Notify the Swing Interface
        Swingterface.getBuffer().setCommon(object);
    }
}