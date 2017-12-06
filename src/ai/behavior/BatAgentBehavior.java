/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.behavior;

import core.actors.enemies.Enemy;
import jade.core.Agent;

/**
 *
 * @author Augustop
 */
public class BatAgentBehavior extends AgentBehavior{

    public BatAgentBehavior(Enemy container, Agent a, long period) {
        super(container, a, period, 4);
    }
    
    @Override
    public void defineAction() {
        
    }

    @Override
    public void checkCollisions() {
        
    }

    @Override
    public void checkStatus() {
        
    }
    
}
