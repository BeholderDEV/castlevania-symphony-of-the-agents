/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Augustop
 */
public class AnimationManager {
    
    public static Animation<TextureRegion> generateAnimation(TextureRegion spritesSheet, int spriteWidth, int spriteHeight, PlayMode loopMode, float frameTime){
        Array<TextureRegion> spriteArray = new Array<TextureRegion>();
        TextureRegion[][] splitedSprites = spritesSheet.split(spriteWidth, spriteHeight);
        for (int i = 0; i < splitedSprites.length; i++) {
            for (int j = 0; j < splitedSprites[0].length; j++) {
                spriteArray.add(splitedSprites[i][j]);
            }
        }
        if(spriteArray.size == 0){
            System.out.println("Error generating animation"); // Possible exception
            return null;
        }
        frameTime = (frameTime == 0) ? 1f / spriteArray.size : frameTime; 
        return new Animation<TextureRegion>(frameTime, spriteArray, loopMode);
    }
    
    public static Animation<TextureRegion> generateAnimation (Texture spritesSheet, int[] x, int[] y, int[] width, int[] height, PlayMode loopMode, float frameTime){
        Array<TextureRegion> spriteArray = new Array<TextureRegion>();
        for (int i = 0; i < x.length; i++) {
           spriteArray.add(new TextureRegion(spritesSheet, x[i], y[i], width[i], height[i]));
        }
        if(spriteArray.size == 0){
            System.out.println("Error generating animation"); // Possible exception
            return null;
        }
        frameTime = (frameTime == 0) ? 1f / spriteArray.size : frameTime; 
        return new Animation<TextureRegion>(frameTime, spriteArray, loopMode);
    }
    
    public static Animation<TextureRegion> generateAnimation(TextureRegion spritesSheet, int spriteWidth, int spriteHeight, PlayMode loopMode){
        return generateAnimation(spritesSheet, spriteWidth, spriteHeight, loopMode, 0);
    }
    
    public static Animation<TextureRegion> generateAnimation (Texture spritesSheet, int[] x, int[] y, int[] width, int[] height, PlayMode loopMode){
        return generateAnimation(spritesSheet, x, y, width, height, loopMode, 0);
    }
}
