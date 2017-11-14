/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jade.core.Runtime;

/**
 *
 * @author Augustop
 */

//Example
//  AgentCreator a = AgentCreator.getInstance();
//  a.createAgent("Main-Container", "Durandal", "launcher.SwordAgent", null);
//  a.createAgent("Main-Container", "Aegis", "launcher.ShieldAgent", null);
//  a.createAgent("Main-Container", "Aegis2", "launcher.ShieldAgent", null);
//  a.createContainer("localhost", "1099", "Container2");
//  a.createAgent("Container2", "Aegis3", "launcher.ShieldAgent", null);

//  public class ShieldAgent extends Agent{
//
//    @Override
//    protected void setup() {
//        System.out.println("My name is " + getLocalName());
//        System.out.println("Nya");
//        super.setup();
//    }
//    
//  }
public class AgentCreator {
    private static AgentCreator instance = null;
    private final HashMap<String, ContainerController> containerMap = new HashMap<>();
    
    private AgentCreator(){
        Runtime rt = Runtime.instance();
	Profile p = new ProfileImpl();
	p.setParameter(Profile.MAIN_HOST, "localhost");
	p.setParameter(Profile.MAIN_PORT, "1099");
	p.setParameter(Profile.CONTAINER_NAME, "Main-Container");
        p.setParameter(Profile.GUI, "true");
        this.containerMap.put("Main-Container", rt.createMainContainer(p));
    }
    
    public static AgentCreator getInstance(){
        if(instance == null){
            instance = new AgentCreator();
        }
        return instance;
    }
    
    public void createContainer(String host, String port, String name){
        Runtime rt = Runtime.instance();
	Profile p = new ProfileImpl();
	p.setParameter(Profile.MAIN_HOST, host);
	p.setParameter(Profile.MAIN_PORT, port);
	p.setParameter(Profile.CONTAINER_NAME, name);
        this.containerMap.put(name, rt.createAgentContainer(p));
    }
    
    public void createAgent(String containerName, String agentName, String classPath, Object[] args){
        ContainerController cc = this.containerMap.get(containerName);
        try {
            cc.createNewAgent(agentName, classPath, args).start();
        } catch (StaleProxyException ex) {
            Logger.getLogger(AgentCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
