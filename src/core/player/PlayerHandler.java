/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import core.AssetsManager;

/**
 *
 * @author Augustop
 */
public class PlayerHandler {
    enum State {
        Standing, Walking, Jumping, Crounching, Attacking
    }
    private final TextureRegion standImg;
    private float WIDTH;
    private float HEIGHT;
    private State currentState = State.Standing;
    private boolean facesRight = true;
    private final Animation<TextureRegion> walkAnimation;
    private final Animation<TextureRegion> jumpAnimation;
    private Rectangle playerBody;
    
    public PlayerHandler() {
        this.standImg = new TextureRegion(AssetsManager.assets.get("assets/img/playerSprites.png", Texture.class), 12, 8, 26, 49);
        this.walkAnimation = null;
        this.jumpAnimation = null;
        this.playerBody = null;
    }

    public Rectangle getPlayerBody() {
        return playerBody;
    }

    public TextureRegion getStandImg() {
        return standImg;
    }

    public Animation<TextureRegion> getJumpAnimation() {
        return jumpAnimation;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }
}
