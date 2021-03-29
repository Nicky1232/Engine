package com.mygdx.zombie.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Rectangle;

public class Pipe implements ShapeDrawable {

    private static final int default_width = 150;
    private static final int default_height = 20;
    private static final Color default_color = Color.WHITE;

    private final Rectangle rectangle;
    private final Color color;

    public Pipe(int x, int y, int width) {
        rectangle = new Rectangle(x, y, width, default_height);
        color = default_color;
    }

    public Pipe(int x, int y) {
        rectangle = new Rectangle(x, y, default_width, default_height);
        color = default_color;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        if(shapeRenderer.isDrawing() && shapeRenderer.getCurrentType() == ShapeRenderer.ShapeType.Line) {
            shapeRenderer.rect(rectangle.x, rectangle.y,
                    rectangle.width, rectangle.height);
        } else if(shapeRenderer.isDrawing() && shapeRenderer.getCurrentType() != ShapeRenderer.ShapeType.Line) {
            System.out.println("Warning: You are drawing Pipe in non optimal way");
            ShapeRenderer.ShapeType tempType = shapeRenderer.getCurrentType();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(rectangle.x, rectangle.y,
                    rectangle.width, rectangle.height);
            shapeRenderer.end();
            shapeRenderer.begin(tempType);
        } else {
            System.out.println("Warning: You are drawing Pipe in non optimal way");
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(rectangle.x, rectangle.y,
                    rectangle.width, rectangle.height);
            shapeRenderer.end();
        }
    }

    @Override
    public void setPosition(int x, int y, boolean centered) {
        if(centered) {
            rectangle.x = x - (rectangle.width >> 1);
            rectangle.y = y - (rectangle.height >> 1);
        } else {
            rectangle.x = x;
            rectangle.y = y;
        }
    }
}
