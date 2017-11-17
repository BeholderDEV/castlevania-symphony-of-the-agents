/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Augustop
 */
public class AssetsManager {
    public static AssetManager assets = new AssetManager();
    
    
    // Possible an loading screen
    public static void loadFirstFaseAssets(){
        assets.load("assets/img/playerSprites.png", Texture.class); // Asynchronous loading
        assets.finishLoading(); // Forces to wait until all assets are loaded
//        assets.get("assets/img/playerSprites.png", Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        float progress = assets.getProgress();
    }
}
