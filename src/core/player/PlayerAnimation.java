/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import core.AnimationManager;
import core.AssetsManager;

/**
 *
 * @author Alisson
 */
public class PlayerAnimation {
    private final TextureRegion standImg;
    private final TextureRegion jumpImg;
    private final TextureRegion crouchImg;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> upstairsAnimation;
    private Animation<TextureRegion> downstairsAnimation;

    public PlayerAnimation() {
        this.standImg = new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 12, 8, 26, 49);
        this.crouchImg = new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 62, 19, 26, 37);
        this.jumpImg = new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 121, 9, 25, 47);
        this.prepareAnimations();
    }
    
    private void prepareAnimations(){
        this.walkAnimation = AnimationManager.generateAnimation(new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 176, 6, 194, 50), 32, 50, Animation.PlayMode.LOOP);
        this.upstairsAnimation = AnimationManager.generateAnimation(new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 405, 8, 194, 50), 32, 50, Animation.PlayMode.LOOP);
        this.downstairsAnimation = AnimationManager.generateAnimation(new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 633, 8, 194, 50), 32, 50, Animation.PlayMode.LOOP);
    }
    
    public TextureRegion getStandImg() {
        return standImg;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public TextureRegion getCrouchImg() {
        return crouchImg;
    }

    public TextureRegion getJumpImg() {
        return jumpImg;
    }

    public Animation<TextureRegion> getDownstairsAnimation() {
        return downstairsAnimation;
    }

    public Animation<TextureRegion> getUpstairsAnimation() {
        return upstairsAnimation;
    }
}
