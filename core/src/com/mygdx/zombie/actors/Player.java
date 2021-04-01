package com.mygdx.zombie.actors;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends Entity {

    protected Player(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    protected void abstractDraw(ShapeRenderer shapeRenderer) {

    }

}
