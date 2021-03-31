package com.mygdx.zombie.actors;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Pipe extends Entity {

    private static final int default_width = 150;
    private static final int default_height = 20;

    public Pipe(int x, int y, int width) {
        super(x, y, width, default_height);
    }

    public Pipe(int x, int y) {
        super(x, y, default_width, default_height);
    }

    @Override
    protected void abstractDraw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(boundingRectangle.x, boundingRectangle.y,
                boundingRectangle.width, boundingRectangle.height);
    }
}
