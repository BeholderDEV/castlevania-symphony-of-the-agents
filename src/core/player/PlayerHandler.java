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
    public enum State {
        STANDING, WALKING, JUMPING, CROUNCHING, ATTACKING, ON_STAIRS
    }
    
    private final float NORMAL_WIDTH = 4f;
    private final float NORMAL_HEIGHT = 6f;
    private final int WALKING_SPEED = 12;
    private final int JUMPING_SPEED = WALKING_SPEED * 3;
    private float stateTime;
    private final TextureRegion standImg;
    private final TextureRegion jumpImg;
    private final TextureRegion crouchImg;
    private State currentState = State.STANDING;
    private boolean facesRight = true;
    private boolean upstairs = false;
    private Vector2 velocity = new Vector2(); 
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> upstairsAnimation;
    private Animation<TextureRegion> downstairsAnimation;
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
        this.upstairsAnimation = AnimationManager.generateAnimation(new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 405, 8, 194, 50), 32, 50, Animation.PlayMode.LOOP);
        this.downstairsAnimation = AnimationManager.generateAnimation(new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 633, 8, 194, 50), 32, 50, Animation.PlayMode.LOOP);
    }
    
    public void updatePlayer(float deltaTime, MapHandler map){
        this.stateTime += deltaTime;
        switch(this.currentState){
            case WALKING:
            case STANDING:
                this.defineActionStanding(deltaTime);
            break;
            case JUMPING:
                this.defineActionJumping(deltaTime);
            break;
            case CROUNCHING:
                this.defineActionCrounching(deltaTime);
            break;
            case ON_STAIRS:
                this.defineActionOnStairs(deltaTime);
            break;
        }
        this.updatePosition(deltaTime);
        this.checkCollisions(map, deltaTime);
    }
    
    private void defineActionStanding(float deltaTime){
        this.currentState = State.STANDING;
        this.velocity.x = 0;
        this.velocity.y = 0;
        if((Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) && (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))){
            return;
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            this.currentState = State.WALKING;
            this.velocity.x = WALKING_SPEED;
            this.facesRight = false;
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            this.currentState = State.WALKING;
            this.velocity.x = WALKING_SPEED;
            this.facesRight = true;
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)){
            this.currentState = State.CROUNCHING;
            this.playerBody.height = Math.round(this.NORMAL_HEIGHT - this.NORMAL_HEIGHT * 0.25);
        }
        if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)){
            this.currentState = State.JUMPING;
            this.velocity.y = JUMPING_SPEED;
        }
    }
    
    private void defineActionJumping(float deltaTime){
        this.velocity.y = (this.velocity.y < 0) ? this.velocity.y - JUMPING_SPEED / 28f : this.velocity.y - JUMPING_SPEED /21f;
        this.velocity.x = (this.velocity.x > 0) ? this.velocity.x + WALKING_SPEED / 1.1f: 0;
        if(this.velocity.x >= WALKING_SPEED){
            this.velocity.x = WALKING_SPEED;
        }
    }
    
    private void defineActionCrounching(float deltaTime){
        if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)){
            this.currentState = State.CROUNCHING;
            return;
        }
        this.currentState = State.STANDING;
        this.playerBody.height = this.NORMAL_HEIGHT;
    }
    
    private void defineActionOnStairs(float deltaTime){
        this.velocity.x = 0;
        this.velocity.y = 0;

        if((Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) && (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))){
            return;
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)){
            this.velocity.x = WALKING_SPEED;
            this.velocity.y = WALKING_SPEED;
            if(this.upstairs && !this.facesRight){
                this.upstairs = false;
                this.facesRight = true;
                this.velocity.y *= -1;
            }else if(!this.upstairs && this.facesRight){
                this.velocity.y *= -1;
            }else if(!this.upstairs && !this.facesRight){
                this.facesRight = true;
                this.upstairs = true;
            }
            this.stateTime += deltaTime;
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)){
            this.velocity.x = WALKING_SPEED;
            this.velocity.y = WALKING_SPEED;
            if(this.upstairs && this.facesRight){
                this.facesRight = false;
                this.upstairs = false;
                this.velocity.y *= -1;
            }else if(!this.upstairs && this.facesRight){
                this.upstairs = true;
                this.facesRight = false;
            }else if(!this.upstairs && !this.facesRight){
                this.velocity.y *= -1;
            }
            this.stateTime += deltaTime;
        }
        if(Gdx.input.isKeyPressed(Keys.SPACE)){
            this.currentState = State.JUMPING;
            this.velocity.y = JUMPING_SPEED;
        }
        this.stateTime -= deltaTime;
    }
    
    private void checkCollisions(MapHandler map, float delta){
        if(map.checkLayerCollision(MapHandler.Layer.GROUND, Math.round(this.playerBody.x), Math.round(this.playerBody.y), Math.round(this.playerBody.x + this.playerBody.width), Math.round(this.playerBody.y + this.playerBody.height * 0.01f))){
            if(this.currentState == State.JUMPING || this.currentState == State.ON_STAIRS){
                this.currentState = State.STANDING;
                this.playerBody.y = Math.round(this.playerBody.y) + 0.4f;
            }
        }else{
            if(this.currentState != State.JUMPING && this.currentState != State.ON_STAIRS){
                this.currentState = State.JUMPING;
            }
        }
        if(this.currentState == State.STANDING || this.currentState == State.WALKING){
            String collisionWithStair;
            if(this.facesRight){
                collisionWithStair = map.checkCollisionWithStairEntrance(Math.round(this.playerBody.x), Math.round(this.playerBody.y), Math.round((this.playerBody.x + this.playerBody.width) - this.playerBody.width * 0.20f), Math.round(this.playerBody.y + this.playerBody.height * 0.10f), this.facesRight);
            }else{
                collisionWithStair = map.checkCollisionWithStairEntrance(Math.round(this.playerBody.x), Math.round(this.playerBody.y), Math.round(this.playerBody.x + this.playerBody.width * 0.20f), Math.round(this.playerBody.y + this.playerBody.height * 0.10f), this.facesRight);
            }
            if(collisionWithStair.equals("upstairs")){
                this.currentState = State.ON_STAIRS;
                this.upstairs = true;
            }
            if(collisionWithStair.equals("downstairs")){
                this.currentState = State.ON_STAIRS;
                this.upstairs = false;
            }
        }
    }
    
    private void updatePosition(float delta){
        this.playerBody.x += this.velocity.x * delta * ((this.facesRight) ? 1: -1);
        this.playerBody.y += this.velocity.y * delta;
    }
    
    public TextureRegion getCurrentFrame(){
        switch(this.currentState){
            case WALKING:
                return this.walkAnimation.getKeyFrame(this.stateTime, true);
            case JUMPING:
                return this.jumpImg;
            case CROUNCHING:
                return this.crouchImg;
            case ON_STAIRS:
                if(this.upstairs) {
                    return this.upstairsAnimation.getKeyFrame(this.stateTime, true);
                }else{
                    return this.downstairsAnimation.getKeyFrame(this.stateTime, true);
                }
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
    
    public Vector2 getVelocity() {
        return velocity;
    }

    public boolean isFacingRight() {
        return facesRight;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
    
}
