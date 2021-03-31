package com.mygdx.zombie.swingterface;

import com.mygdx.zombie.screen.sandbox.SandboxStatusManager;

public class Swingterface {

    public static void triggerSelectionUpdate(String name) {
        switch (name) {
            case "Select":
                Status.selection = SelectButton.SELECTION;
                break;
            case "Pipe":
                Status.selection = SelectButton.PIPE;
                break;
            case "Spike":
                Status.selection = SelectButton.SPIKE;
                break;
            case "ColorBall":
                Status.selection = SelectButton.COLOR_BALL;
                break;
            case "Player":
                Status.selection = SelectButton.PLAYER;
                break;
        }

        SandboxStatusManager.signal();

    }
}
