/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.sword;

import core.actors.enemies.SwordSkeleton;
import jade.core.Agent;

/**
 *
 * @author 5674867
 */
public class SwordAgentCore extends Agent{
    public static int agentNameCount = 1;
    
    @Override
    protected void setup() {
        System.out.println("Iniating sword agent name: " + getAID().getName());
        Object [] args = getArguments();
        if(args != null && args.length > 0){
            addBehaviour(new SwordAgentBehavior(this, (SwordSkeleton) args[0]));
        }
    }
    
    @Override
    protected void takeDown() {
        System.out.println("Ending agent " + getAID().getName());
    }
}
