/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launcher;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import core.actors.CollisionHandler;
import core.screens.ScreenHandler;

/**
 *
 * @author Augustop
 */
public class Launcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CollisionHandler.iniatilizeRectangles();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Symphony of the Agents";
//        config.width = 1280;
//        config.height = 720;
//        config.width = 1024;
//        config.height = 768;
//        config.width = 1366;
//        config.width = 1366;
//        config.height = 600;
        config.width = 750;
        config.height = 400;

        new LwjglApplication(new ScreenHandler(), config);
    }
    
}
