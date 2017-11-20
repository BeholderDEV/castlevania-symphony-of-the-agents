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

/**
 *
 * @author Augustop
 */
public class PlayerHandler {
    public enum State {
        Standing, Walking, Jumping, Crounching, Attacking
    }
    private final float NORMAL_WIDTH = 4f;
    private final float NORMAL_HEIGHT = 6f;
    private final int WALKING_SPEED = 7;
    private final int JUMPING_SPEED = 30;
    private float stateTime;
    private final TextureRegion standImg;
    private final TextureRegion jumpImg;
    private final TextureRegion crouchImg;
    private State currentState = State.Standing;
    private boolean facesRight = true;
    private Vector2 velocity = new Vector2(); 
    private Animation<TextureRegion> walkAnimation;
    private Rectangle playerBody;
    
    public PlayerHandler() {
        this.standImg = new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 12, 8, 26, 49);
        this.crouchImg = new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 62, 19, 26, 37);
        this.jumpImg = new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 121, 9, 25, 47);
        this.prepareAnimations();
        this.playerBody = new Rectangle(0, 0, this.NORMAL_WIDTH, this.NORMAL_HEIGHT);
        this.stateTime = 0;
    }
    
    private void prepareAnimations(){
        this.walkAnimation = AnimationManager.generateAnimation(new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 176, 6, 194, 50), 32, 50, Animation.PlayMode.LOOP);
    }
    
    public void updatePlayer(float deltaTime){
        this.stateTime += deltaTime;
        
        switch(this.currentState){
            case Walking:
            case Standing:
                this.defineActionStanding(deltaTime);
            break;
            case Jumping:
                this.defineActionJumping(deltaTime);
            break;
            case Crounching:
                this.defineActionCrounching(deltaTime);
            break;
        }
    }
    
    private void defineActionStanding(float deltaTime){
        this.currentState = State.Standing;
        this.velocity.x = 0;
        if((Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) && (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))){
            return;
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            this.currentState = State.Walking;
            this.playerBody.x -= WALKING_SPEED * deltaTime;
            this.velocity.x = WALKING_SPEED;
            this.facesRight = false;
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            this.currentState = State.Walking;
            this.playerBody.x += WALKING_SPEED * deltaTime;
            this.velocity.x = WALKING_SPEED;
            this.facesRight = true;
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)){
            this.currentState = State.Crounching;
            this.playerBody.height = Math.round(this.NORMAL_HEIGHT - this.NORMAL_HEIGHT * 0.25);
        }
        if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)){
            this.currentState = State.Jumping;
            this.playerBody.y += JUMPING_SPEED * deltaTime;
            this.velocity.y = JUMPING_SPEED;
        }
    }
    
    //Detect the ground when using an actual map
    private void defineActionJumping(float deltaTime){
        this.velocity.y = (this.velocity.y < 0) ? this.velocity.y - JUMPING_SPEED / 28f : this.velocity.y - JUMPING_SPEED /21f;
        this.velocity.x = (this.velocity.x > 0) ? this.velocity.x + WALKING_SPEED / 1.1f: 0;
        this.playerBody.x += this.velocity.x * deltaTime * ((this.facesRight) ? 1: -1);
        this.playerBody.y += this.velocity.y * deltaTime;
        if(this.velocity.x >= WALKING_SPEED){
            this.velocity.x = WALKING_SPEED;
        }
        if(this.playerBody.y <= 1){
            this.playerBody.y = 1;
            this.currentState = State.Standing;
        }
    }
    
    private void defineActionCrounching(float deltaTime){
        if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)){
            this.currentState = State.Crounching;
            return;
        }
        this.currentState = State.Standing;
        this.playerBody.height = this.NORMAL_HEIGHT;
    }
    
    public TextureRegion getCurrentFrame(){
        switch(this.currentState){
            case Walking:
                return this.walkAnimation.getKeyFrame(this.stateTime, true);
            case Jumping:
                return this.jumpImg;
            case Crounching:
                return this.crouchImg;
            default:
                return this.getStandImg();
        }
    }

    public Rectangle getPlayerBody() {
        return playerBody;
    }

    public TextureRegion getStandImg() {
        return standImg;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public State getCurrentState() {
        return currentState;
    }

    public float getStateTime() {
        return stateTime;
    }

    public boolean isFacingRight() {
        return facesRight;
    }
}
