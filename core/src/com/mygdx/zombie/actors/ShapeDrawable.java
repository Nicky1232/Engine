package com.mygdx.zombie.actors;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface ShapeDrawable {
    void draw(ShapeRenderer shapeRenderer);
    void setPosition(int x, int y, boolean centered);
}
