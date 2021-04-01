package com.mygdx.zombie.swingterface;

import com.mygdx.zombie.actors.ColorBall;
import com.mygdx.zombie.actors.Pipe;
import com.mygdx.zombie.actors.Player;
import com.mygdx.zombie.actors.Spike;
import com.mygdx.zombie.screen.sandbox.SandboxStatusManager;

public class Swingterface {

    static Buffer buffer = new Buffer();

    public static void triggerSelectionUpdate(String name) {

        switch (name) {
            case "Select":
                Status.selection = null;
                break;
            case "Pipe":
                Status.selection = Pipe.class;
                break;
            case "Spike":
                Status.selection = Spike.class;
                break;
            case "ColorBall":
                Status.selection = ColorBall.class;
                break;
            case "Player":
                Status.selection = Player.class;
                break;
        }

        SandboxStatusManager.signal();

    }

    public static void triggerDataUpdate(Buffer buffer) {
        SandboxStatusManager.updateEntity(buffer);
    }

    public static Buffer getBuffer() {
        return buffer;
    }

    public static void flush() {
        buffer.flushed = true;
    }
}
