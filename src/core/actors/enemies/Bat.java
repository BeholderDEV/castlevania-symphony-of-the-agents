/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.actors.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import core.actors.GameActor;
import core.map.MapHandler;
import core.screens.GameScreen;

/**
 *
 * @author Augustop
 */
public class Bat extends Enemy{

    public Bat(int walkingSpeed, Rectangle body, GameScreen gameScreen) {
        super(walkingSpeed, body, gameScreen);
    }
    
    @Override
    public void updateActor(float deltaTime, MapHandler map, Array<GameActor> stageActors) {
    
    }

    @Override
    public void renderActor(SpriteBatch batch) {
    
    }

    @Override
    protected void adjustRenderCorrections(TextureRegion currentFrame) {

    }
    
    @Override
    public void drawDebugRec(SpriteBatch batch) {
    }


    
}
