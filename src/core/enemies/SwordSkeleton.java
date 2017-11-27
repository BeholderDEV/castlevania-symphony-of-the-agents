/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import core.map.MapHandler;

/**
 *
 * @author Augustop
 */
public class SwordSkeleton extends Enemy{
    
    
    public SwordSkeleton(int walkingSpeed, Rectangle body) {
        super(walkingSpeed, body);
    }

    @Override
    public void updateBehavior(float deltaTime, MapHandler map) {
        
    }

    @Override
    public TextureRegion getCurrentFrame() {
        return super.standImg;
    }
    
}
