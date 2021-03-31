package com.mygdx.zombie.utils.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

//This is camera, that could be zoomed in and out both with buttons and wheel rolling
public class ScrollingCamera extends OrthographicCamera {

    private static final float keyboardSensitivity              = 10f;      //<< The speed of the camera when button is hold
    private static final float dragSensitivityReduceFactor      = 1.75f;    //<< While draging with mouse button hold down
    private static final float scrollSensitivityReduceFactor    = 5f;       //<< The offset of the camera beset on the cursor position
    private static final float zoomSensitivityReduceFactor      = 10f;      //<< The zoom value on scroll
    private static final boolean moveOnZoomOut = true;                      //<< Should the camera use offset when zooming out (both are fine)
    private static final boolean moveOnZoomIn = true;                       //<< Should the camera use offset when zooming in  (usually true)

    private Viewport viewport;
    private int width;
    private int height;

    private boolean dragable;       //<< If user can drag the camera (when primary mouse button is down)
    private boolean touched;
    private float startMouseX;
    private float startMouseY;

    public ScrollingCamera() {
        this.dragable = true;
    }

    public ScrollingCamera(boolean dragable) {
        this.dragable = dragable;
    }

    public void bindViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    @Override
    public void update() {
        super.update();
        processKeyboardInput();
        processDragAction();
    }

    public void onScroll(float amountY, boolean isPositiveScroll) {

        if (width != viewport.getScreenWidth() ||
                height != viewport.getScreenHeight()) updateScreenDimensions();

        int x = Gdx.input.getX();
        int y = Gdx.input.getY();

        Vector2 centerVector = new Vector2();
        centerVector.x = width >> 1;
        centerVector.y = height >> 1;

        boolean isLeftCtrlHold = Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT);
        Vector2 offsetVector = new Vector2();
        if (isPositiveScroll) {
            if (moveOnZoomOut && !isLeftCtrlHold) {
                offsetVector.x = centerVector.x - x;
                offsetVector.y = -(centerVector.y - y);
            }
        } else {
            if(moveOnZoomIn && !isLeftCtrlHold) {
                offsetVector.x = -(centerVector.x - x);
                offsetVector.y = centerVector.y - y;
            }
        }

        offsetVector.x /= scrollSensitivityReduceFactor;
        offsetVector.y /= scrollSensitivityReduceFactor;
        translate(offsetVector);

        zoom += amountY / zoomSensitivityReduceFactor;
        if(zoom < 0) zoom = 0;
    }

    public void setDragable(boolean dragable) {
        this.dragable = dragable;
    }

    private void processKeyboardInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)) translate(new Vector2(0, keyboardSensitivity));
        if(Gdx.input.isKeyPressed(Input.Keys.S)) translate(new Vector2(0, -keyboardSensitivity));
        if(Gdx.input.isKeyPressed(Input.Keys.A)) translate(new Vector2(-keyboardSensitivity, 0));
        if(Gdx.input.isKeyPressed(Input.Keys.D)) translate(new Vector2(keyboardSensitivity, 0));
    }

    private void processDragAction() {
        if(dragable) {
            boolean lastFrameTouched = touched;
            if (!lastFrameTouched && Gdx.input.isTouched()) {
                startMouseX = Gdx.input.getX();
                startMouseY = Gdx.input.getY();
                touched = true;
            } else if (lastFrameTouched && Gdx.input.isTouched()) {
                position.x += (-Gdx.input.getX() + startMouseX) / dragSensitivityReduceFactor;
                position.y += (Gdx.input.getY() - startMouseY) / dragSensitivityReduceFactor;
                startMouseX = Gdx.input.getX();
                startMouseY = Gdx.input.getY();
                touched = true;
            } else if (lastFrameTouched && !Gdx.input.isTouched()) {
                startMouseX = 0;
                startMouseY = 0;
                touched = false;
            }
        }
    }

    private void updateScreenDimensions() {
        this.width = viewport.getScreenWidth();
        this.height = viewport.getScreenHeight();
    }
}