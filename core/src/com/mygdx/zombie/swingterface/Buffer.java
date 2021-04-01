package com.mygdx.zombie.swingterface;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.zombie.actors.Entity;

public class Buffer {

    boolean flushed;
    Common common;

    public void setCommon(Entity entity) {
        Rectangle rectangle = entity.getBoundingRectangle();
        common = new Common(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        flushed = false;
    }

    public void setCommon(float x, float y, float width, float height) {
        common = new Common(x, y, width, height);
        flushed = false;
    }

    public boolean isFlushed() {
        return flushed;
    }

    public Common getCommon() {
        return common;
    }

    public static class Common {

        public final float x;
        public final float y;
        public final float width;
        public final float height;

        public Common(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

    }

}
