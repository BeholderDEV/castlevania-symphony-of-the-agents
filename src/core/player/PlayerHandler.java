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
    enum State {
        Standing, Walking, Jumping, Crounching, Attacking
    }
    private int normalWidth = 32;
    private int normalHeight = 50;
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
        this.playerBody = new Rectangle(0, 0, this.normalWidth, this.normalHeight);
        this.stateTime = 0;
    }
    
    private void prepareAnimations(){
        this.walkAnimation = AnimationManager.generateAnimation(new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 176, 6, 194, 50), this.normalWidth, this.normalHeight, Animation.PlayMode.LOOP);
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
            this.playerBody.x -= 50 * deltaTime;
            this.velocity.x = 50;
            this.facesRight = false;
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            this.currentState = State.Walking;
            this.playerBody.x += 50 * deltaTime;
            this.velocity.x = 50;
            this.facesRight = true;
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)){
            this.currentState = State.Crounching;
            this.playerBody.height = 37;
        }
        if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)){
            this.currentState = State.Jumping;
            this.playerBody.y += 400 * deltaTime;
            this.velocity.y = 400;
        }
    }
    
    //Detect the ground when using an actual map
    private void defineActionJumping(float deltaTime){
        this.velocity.y = (this.velocity.y < 0) ? this.velocity.y - 17 : this.velocity.y - 10;
        this.velocity.x = (this.velocity.x > 0) ? this.velocity.x + 2: 0;
        this.playerBody.x += this.velocity.x * deltaTime * ((this.facesRight) ? 1: -1);
        this.playerBody.y += this.velocity.y * deltaTime;
        if(this.velocity.x >= 100){
            this.velocity.x = 100;
        }
        if(this.playerBody.y <= 100){
            this.playerBody.y = 100;
            this.currentState = State.Standing;
        }
    }
    
    private void defineActionCrounching(float deltaTime){
        if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)){
            this.currentState = State.Crounching;
            return;
        }
        this.currentState = State.Standing;
        this.playerBody.height = this.normalHeight;
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
