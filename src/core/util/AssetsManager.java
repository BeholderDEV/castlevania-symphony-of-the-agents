/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

/**
 *
 * @author Augustop
 */
public class AssetsManager {
    public static final AssetManager assets = new AssetManager();
    
    // Possible an loading screen
    public static void loadFirstFaseAssets(){
        assets.load("assets/img/square.png", Texture.class);
        assets.load("assets/img/squarer.png", Texture.class);        
        
        assets.load("assets/img/superIV_Enemies.png", Texture.class);
        assets.load("assets/img/playerSprites.png", Texture.class);
        assets.load("assets/img/bone-archer.png", Texture.class);
        assets.load("assets/img/heart.png", Texture.class); 
        assets.load("assets/sound/bloodyTears.mp3", Music.class); 
        assets.load("assets/sound/whipSound.wav", Music.class);
        assets.load("assets/sound/hurtSound.wav", Music.class);
        assets.load("assets/sound/enemyDeath.mp3", Sound.class);
        assets.finishLoading(); // Forces to wait until all assets are loaded
//        assets.get("assets/img/playerSprites.png", Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        float progress = assets.getProgress();
    }
}
