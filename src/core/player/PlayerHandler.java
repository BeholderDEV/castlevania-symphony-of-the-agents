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
            case ATTACKING:
                return this.defineAtkSprite();
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
    
    private TextureRegion defineAtkSprite(){
        if(this.animationHandler.getStandAtkAnimation().isAnimationFinished(stateTime)){
            this.behaviorHandler.getPlayerBody().setSize(PlayerBehavior.NORMAL_WIDTH, PlayerBehavior.NORMAL_HEIGHT);
            this.behaviorHandler.setCurrentState(PlayerBehavior.State.STANDING);
            return this.animationHandler.getStandImg();
        }
        TextureRegion atkSprite = this.animationHandler.getStandAtkAnimation().getKeyFrame(this.stateTime);
//        TextureRegion atkSprite = this.animationHandler.getStandAtkAnimation().getKeyFrame(0);
//        this.behaviorHandler.getPlayerBody().x
//        this.behaviorHandler.getPlayerBody().width = atkSprite.getRegionWidth() * MapHandler.unitScale;
//        this.behaviorHandler.getPlayerBody().height = atkSprite.getRegionHeight() * MapHandler.unitScale;
        return atkSprite;
    }
    
    public boolean isDead(){
        return this.behaviorHandler.getCurrentState() == PlayerBehavior.State.DYING && this.animationHandler.getDeathAnimation().isAnimationFinished(stateTime);
    }

    
    public void drawRecOnPlayer(SpriteBatch batch){
//        this.behaviorHandler.drawRec(batch);
        Rectangle p = this.behaviorHandler.getPlayerBody();
//        TextureRegion currentFrame = this.animationHandler.getStandAtkAnimation().getKeyFrame(this.stateTime, true);
//        batch.draw(currentFrame,
//               (p.x + p.width / 2f) - 5f - (currentFrame.getRegionWidth() * MapHandler.unitScale / 2f),
//               (p.y + p.height / 2f) - (currentFrame.getRegionHeight() * MapHandler.unitScale / 2f), 
//                currentFrame.getRegionWidth() * MapHandler.unitScale, 
//                currentFrame.getRegionHeight() * MapHandler.unitScale);
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
