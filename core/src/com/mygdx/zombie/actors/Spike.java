package com.mygdx.zombie.actors;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Spike extends Entity {

    private static final int default_width = 30;
    private static final int default_height = 30;

    private final Triangle shape;

    public Spike(int x, int y) {
        super(x, y, default_width, default_height);

        Vector2 A, B, C;
        A = new Vector2(x, y);
        B = new Vector2(x + default_width, y);
        C = new Vector2(x + (default_width >> 1), y + default_height);

        this.shape = new Triangle(A, B, C);
    }

    @Override
    protected void abstractDraw(ShapeRenderer shapeRenderer) {
        drawTriangle(shapeRenderer, shape);
    }

    @Override
    public void setPosition(int x, int y, boolean centered) {
        super.setPosition(x, y, centered);
        shape.setPosition(boundingRectangle.x, boundingRectangle.y);
    }

    private void drawTriangle(ShapeRenderer shapeRenderer, Triangle triangle) {
        shapeRenderer.triangle(
                triangle.A.x, triangle.A.y,
                triangle.B.x, triangle.B.y,
                triangle.C.x, triangle.C.y);
    }

    private static final class Triangle {

        private final Vector2[] points = new Vector2[3];
        private final Vector2 A, B, C;

        public Triangle(Vector2 a, Vector2 b, Vector2 c) {
            points[0] = a; points[1] = b; points[2] = c;
            A = a; B = b; C = c;
        }

        public void move(float x, float y) {
            for(Vector2 point : points) point.add(x, y);
        }

        public void setPosition(float x, float y) {
            A.set(x, y);
            B.set(x + default_width, y);
            C.set(x + (default_width >> 1), y + default_height);
        }
    }
}
