package com.mygdx.zombie.actors;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ColorBall extends Entity {

    private static final int default_radius = 15;

    public ColorBall(float x, float y) {
        super(x - default_radius, y - default_radius,
                default_radius << 1, default_radius << 1);
    }

    @Override
    protected void abstractDraw(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(boundingRectangle.x + default_radius,
                boundingRectangle.y + default_radius, default_radius);
    }
}
