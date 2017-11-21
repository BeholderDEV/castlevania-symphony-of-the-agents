/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import core.AnimationManager;
import core.AssetsManager;
import core.map.MapHandler;

/**
 *
 * @author Augustop
 */
public class PlayerHandler {
    private PlayerAnimation animationHandler;
    private PlayerBehavior behaviorHandler;
    
    public PlayerHandler() {
        this.animationHandler = new PlayerAnimation();
        this.behaviorHandler = new PlayerBehavior();
    }
    
    public void updatePlayer(float deltaTime, MapHandler map){
        this.behaviorHandler.defineAction(deltaTime);
        this.behaviorHandler.updatePosition(deltaTime);
        this.behaviorHandler.checkCollisions(map, deltaTime);
    }
    
    public TextureRegion getCurrentFrame(){
        switch(this.behaviorHandler.getCurrentState()){
            case WALKING:
                return this.animationHandler.getWalkAnimation().getKeyFrame(this.behaviorHandler.getStateTime(), true);
            case JUMPING:
                return this.animationHandler.getJumpImg();
            case CROUNCHING:
                return this.animationHandler.getCrouchImg();
            case ON_STAIRS:
                if(this.behaviorHandler.isUpstairs()) {
                    return this.animationHandler.getUpstairsAnimation().getKeyFrame(this.behaviorHandler.getStateTime(), true);
                }else{
                    return this.animationHandler.getDownstairsAnimation().getKeyFrame(this.behaviorHandler.getStateTime(), true);
                }
            default:
                return this.animationHandler.getStandImg();
        }
    }
    
    public Rectangle getPlayerBody(){
        return this.behaviorHandler.getPlayerBody();
    }
    
    public boolean isFacingRight(){
        return this.behaviorHandler.isFacingRight();
    }
}
