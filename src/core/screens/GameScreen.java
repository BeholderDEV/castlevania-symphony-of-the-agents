/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import core.AssetsManager;
import core.map.MapHandler;
import core.player.PlayerHandler;
import java.awt.Dimension;

/**
 *
 * @author Augustop
 */
public class GameScreen implements Screen {
    public final int SCREEN_WIDTH = 50;
    public final int SCREEN_HEIGHT = 20;
    private final ScreenHandler game;
    private final PlayerHandler player;
    private final MapHandler mapHandler;
    private OrthographicCamera camera;
    

    public GameScreen(final ScreenHandler game, PlayerHandler player) {
        this.game = game;
        this.player = player;
        
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        this.mapHandler = new MapHandler("assets/map/mapadahora.tmx", 1 / 8f);
        
        this.camera.update();
        this.mapHandler.getMapRenderer().setView(camera);
        this.player.getPlayerBody().setPosition(23, 3.4f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        this.player.updatePlayer(delta, this.mapHandler);
        
        
        this.updateCameraPosition();
        this.camera.update();
        
        this.mapHandler.getMapRenderer().setView(camera);
        this.mapHandler.getMapRenderer().render();
        this.game.batch.setProjectionMatrix(camera.combined);
        
//        AssetsManager.assets.load("assets/img/square.png", Texture.class);
//        AssetsManager.assets.load("assets/img/squarer.png", Texture.class);
//        AssetsManager.assets.finishLoading();
        this.game.batch.begin();
        this.renderPlayer(); 
//        
//        this.game.batch.draw(AssetsManager.assets.get("assets/img/squarer.png", Texture.class), this.player.getPlayerBody().x, this.player.getPlayerBody().y, this.player.getPlayerBody().width, this.player.getPlayerBody().height);
//        
//        float x = (this.player.getPlayerBody().x + this.player.getPlayerBody().width) - (this.player.getPlayerBody().width * (this.FOOT_SIZE.width / 100f));
//        System.out.println(x);
//        
//        float w = this.player.getPlayerBody().x +this.player.getPlayerBody().width - x;
//        System.out.println(w);
//        
//        
//        float y= this.player.getPlayerBody().y;
//        float h= this.player.getPlayerBody().height * (this.FOOT_SIZE.height / 100f);
//        
//        System.out.println("x: "+this.player.getPlayerBody().x +" - w: "+this.player.getPlayerBody().width);
//        System.out.println("y: "+this.player.getPlayerBody().y +" - h: "+this.player.getPlayerBody().height);
//        this.game.batch.draw(AssetsManager.assets.get("assets/img/square.png", Texture.class), 
//                
//                x,
//                y, 
//                w,
//                h);
        this.game.batch.end();        
        this.verifyPlayerStatus();
        this.verifyMenuInputs();
    }
    
    private void verifyPlayerStatus(){
        if(this.player.getPlayerBody().y + this.player.getPlayerBody().height < 0){
            AssetsManager.assets.load("assets/img/gameover_screen.png", Texture.class);
            AssetsManager.assets.finishLoading();
            this.game.setScreen(new GameOverScreen(game));
            this.mapHandler.disposeMap();
        }
    }
    
    private void updateCameraPosition(){
        TiledMapTileLayer mapFrontLayer = (TiledMapTileLayer) this.mapHandler.getMapRenderer().getMap().getLayers().get("front");
        if(this.player.getPlayerBody().x + this.player.getPlayerBody().width / 2 <= this.SCREEN_WIDTH / 2){
            this.camera.position.x = this.SCREEN_WIDTH / 2;
            return;
        }
        if(this.player.getPlayerBody().x + this.player.getPlayerBody().width / 2 >= mapFrontLayer.getWidth() - this.SCREEN_WIDTH / 2){
            this.camera.position.x = mapFrontLayer.getWidth() - this.SCREEN_WIDTH / 2;
            return;
        }
        this.camera.position.x = this.player.getPlayerBody().x + this.player.getPlayerBody().width / 2;
    }
    
    private void renderPlayer(){
        Rectangle playerRec = this.player.getPlayerBody();
        if(playerRec.x < 0) playerRec.setX(0);
//        if(playerRec.x > this.SCREEN_WIDTH - playerRec.width) playerRec.setX(this.SCREEN_WIDTH - playerRec.width);
        if(this.player.isFacingRight()){
            this.game.batch.draw(this.player.getCurrentFrame(), playerRec.x, playerRec.y, playerRec.width, playerRec.height);
        }else{
            this.game.batch.draw(this.player.getCurrentFrame(), playerRec.x + playerRec.width, playerRec.y, -playerRec.width, playerRec.height);
        }
    }
    
    private void verifyMenuInputs(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            this.dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
//        this.rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        this.mapHandler.disposeMap();
        AssetsManager.assets.clear();
    }

}