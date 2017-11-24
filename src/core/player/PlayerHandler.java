/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.player;

import com.badlogic.gdx.graphics.g2d.Animation;
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
    private float stateTime = 0;
    
    public PlayerHandler() {
        this.animationHandler = new PlayerAnimation();
        this.behaviorHandler = new PlayerBehavior(this);
    }
    
    public void updatePlayer(float deltaTime, MapHandler map){
        this.stateTime += deltaTime;
        this.behaviorHandler.defineAction(deltaTime, map);
        this.behaviorHandler.updatePosition(deltaTime);
        this.behaviorHandler.checkCollisions(map);
    }
    
    public TextureRegion getCurrentFrame(){
        switch(this.behaviorHandler.getCurrentState()){
            case WALKING:
                return this.animationHandler.getWalkAnimation().getKeyFrame(this.stateTime, true);
            case JUMPING:
                return this.animationHandler.getJumpImg();
            case CROUNCHING:
                return this.animationHandler.getCrouchImg();
            case ON_STAIRS:
                if(this.behaviorHandler.isUpstairs()) {
                    return this.animationHandler.getUpstairsAnimation().getKeyFrame(this.stateTime, true);
                }else{
                    return this.animationHandler.getDownstairsAnimation().getKeyFrame(this.stateTime, true);
                }
            case DYING:
                return this.defineDeathSprite();
            case STAND_ATK:
                return this.defineAtkSprite(this.animationHandler.getStandAtkAnimation());
            case CROUCH_ATK:
                 return this.defineAtkSprite(this.animationHandler.getCrouchAtkAnimation());
            default:
                return this.animationHandler.getStandImg();
        }
    }
    
    private TextureRegion defineDeathSprite(){
        if(this.animationHandler.getDeathAnimation().isAnimationFinished(stateTime)){
            return this.animationHandler.getDeathAnimation().getKeyFrame(this.stateTime);
        }
        TextureRegion deathSprite = this.animationHandler.getDeathAnimation().getKeyFrame(this.stateTime);
        this.behaviorHandler.getPlayerBody().width = deathSprite.getRegionWidth() * MapHandler.unitScale;
        this.behaviorHandler.getPlayerBody().height = deathSprite.getRegionHeight() * MapHandler.unitScale;
        return deathSprite;
    }
    
    private TextureRegion defineAtkSprite(Animation<TextureRegion> atkAnimation){
        if(atkAnimation.isAnimationFinished(stateTime)){
            switch(this.getCurrentState()){
                case CROUCH_ATK:
                    this.behaviorHandler.getPlayerBody().setSize(PlayerBehavior.NORMAL_WIDTH, Math.round(PlayerBehavior.NORMAL_HEIGHT - PlayerBehavior.NORMAL_HEIGHT * 0.25));
                    this.behaviorHandler.setCurrentState(PlayerBehavior.State.CROUNCHING);
                    return this.animationHandler.getCrouchImg();
                default:
                    this.behaviorHandler.getPlayerBody().setSize(PlayerBehavior.NORMAL_WIDTH, PlayerBehavior.NORMAL_HEIGHT);
                    this.behaviorHandler.setCurrentState(PlayerBehavior.State.STANDING);
                    return this.animationHandler.getStandImg();
            }
        }
        return atkAnimation.getKeyFrame(this.stateTime);
//        System.out.println(this.animationHandler.getStandAtkAnimation().getKeyFrames().getClass());
//        return this.animationHandler.getStandAtkAnimation().getKeyFrame(this.stateTime);
    }
    
    public boolean isDead(){
        return this.behaviorHandler.getCurrentState() == PlayerBehavior.State.DYING && this.animationHandler.getDeathAnimation().isAnimationFinished(stateTime);
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

    public float getStateTime() {
        return stateTime;
    }
    
    public PlayerBehavior.State getCurrentState(){
        return this.behaviorHandler.getCurrentState();
    }
    

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
    
    public void changeStateTime(float delta){
        this.stateTime += delta;
    }
    
}
