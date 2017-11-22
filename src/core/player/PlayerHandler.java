/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
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
        this.behaviorHandler.defineAction(deltaTime, map);
        this.behaviorHandler.updatePosition(deltaTime);
        this.behaviorHandler.checkCollisions(map);
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
    
    public void drawRecOnPlayer(SpriteBatch batch){
        this.behaviorHandler.drawRec(batch);
    }
    
    public Rectangle getPlayerBody(){
        return this.behaviorHandler.getPlayerBody();
    }
    
    public boolean isFacingRight(){
        return this.behaviorHandler.isFacingRight();
    }
}
