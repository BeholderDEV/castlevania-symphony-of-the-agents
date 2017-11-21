/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launcher;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Symphony of the Agents";
//        config.width = 1280;
//        config.height = 720;
        config.width = 600;
        config.height = 400;
        new LwjglApplication(new ScreenHandler(), config);
    }
    
}
