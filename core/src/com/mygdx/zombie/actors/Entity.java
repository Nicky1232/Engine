package com.mygdx.zombie.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.zombie.info.GameInfo;

public abstract class Entity {

    private boolean drawBoundingShape;
    private final Color color;
    protected Rectangle boundingRectangle;  //<< Use the rectangle to get the information for x, y, width, height;

    protected Entity(float x, float y, float width, float height) {
        boundingRectangle = new Rectangle(x, y, width, height);
        color = GameInfo.default_color;
        drawBoundingShape = false;
    }

    protected abstract void abstractDraw(ShapeRenderer shapeRenderer);

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        if(shapeRenderer.isDrawing() && shapeRenderer.getCurrentType() == ShapeRenderer.ShapeType.Line) {
            abstractDraw(shapeRenderer);
            drawBoundingRectangle(shapeRenderer);
        } else if(shapeRenderer.isDrawing() && shapeRenderer.getCurrentType() != ShapeRenderer.ShapeType.Line) {
            System.out.println("Warning: You are drawing Pipe in non optimal way");
            ShapeRenderer.ShapeType tempType = shapeRenderer.getCurrentType();
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            abstractDraw(shapeRenderer);
            drawBoundingRectangle(shapeRenderer);
            shapeRenderer.end();
            shapeRenderer.begin(tempType);
        } else {
            System.out.println("Warning: You are drawing Pipe in non optimal way");
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            abstractDraw(shapeRenderer);
            drawBoundingRectangle(shapeRenderer);
            shapeRenderer.end();
        }
    }

    public void setPosition(int x, int y, boolean centered) {
        if(centered) {
            boundingRectangle.x = x - ((int)boundingRectangle.width >> 1);
            boundingRectangle.y = y - ((int)boundingRectangle.height >> 1);
        } else {
            boundingRectangle.x = x;
            boundingRectangle.y = y;
        }
    }

    public boolean overlaps(int x, int y) {
        return boundingRectangle.contains(x, y);
    }

    public void setDrawBoundingRectangle(boolean value) {
        this.drawBoundingShape = value;
    }

    public void drawBoundingRectangle(ShapeRenderer shapeRenderer) {
        if(drawBoundingShape) {
            Color temp = shapeRenderer.getColor();
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(boundingRectangle.x, boundingRectangle.y,
                    boundingRectangle.width, boundingRectangle.height);
            shapeRenderer.setColor(temp);
        }
    }
}
