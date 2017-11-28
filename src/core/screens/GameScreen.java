/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import core.AssetsManager;
import core.actors.GameActor;
import core.actors.enemies.Enemy;
import core.actors.enemies.EnemyFactory;
import core.map.MapHandler;
import core.actors.player.PlayerBehavior;
import core.actors.player.PlayerHandler;

/**
 *
 * @author Augustop
 */
public class GameScreen implements Screen {
    public final int SCREEN_WIDTH = 50;
    public final int SCREEN_HEIGHT = 20;
    private final ScreenHandler game;
//    private final PlayerHandler player;
//    private Array<Enemy> enemies = new Array<>();
    private Array<GameActor> actors = new Array<>();
    
    private final MapHandler mapHandler;
    private OrthographicCamera camera;
    
    public GameScreen(final ScreenHandler game, PlayerHandler player) {
        this.game = game;
//        this.actors.get(0) = player;
//        this.enemies.add(EnemyFactory.createEnemy(EnemyFactory.enemyType.SWORD_SKELETON, 12, new Vector2(30, 3.4f), 5f, 6f));
        
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        this.mapHandler = new MapHandler("assets/map/mapadahora.tmx");
        
        this.camera.update();
        this.mapHandler.getMapRenderer().setView(camera);
//        this.actors.get(0).getBody().setPosition(23, 3.4f);
        this.createActors();
    }
    
    private void createActors(){
        PlayerHandler player = new PlayerHandler();
        player.getBody().setPosition(23, 3.4f);
        this.actors.add(player);
        this.actors.add(EnemyFactory.createEnemy(EnemyFactory.enemyType.SWORD_SKELETON, 12, new Vector2(30, 3.4f), 5f, 6f));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (GameActor actor : actors) {
            actor.updateActor(delta, mapHandler);
        }
        this.updateCameraPosition();
        this.camera.update();
        
        this.mapHandler.getMapRenderer().setView(camera);
        this.mapHandler.getMapRenderer().render();
        this.game.batch.setProjectionMatrix(camera.combined);
        this.game.batch.begin();
        for (GameActor actor : actors) {
            actor.renderActor(this.game.batch);
        }
        this.game.batch.end();        
        this.verifyPlayerStatus();
        this.verifyMenuInputs();
    }
    
    private void verifyPlayerStatus(){
        if(this.actors.get(0).getBody().y + this.actors.get(0).getBody().height < 0 || this.actors.get(0).isDead()){
            AssetsManager.assets.load("assets/img/gameover_screen.png", Texture.class);
            AssetsManager.assets.finishLoading();
            this.game.setScreen(new GameOverScreen(game));
            this.mapHandler.disposeMap();
        }
    }
    
    private void updateCameraPosition(){
        TiledMapTileLayer mapFrontLayer = (TiledMapTileLayer) this.mapHandler.getMapRenderer().getMap().getLayers().get("front");
        this.updateCameraX(mapFrontLayer);
        this.updateCameraY(mapFrontLayer);
    }
    
    private void updateCameraX(TiledMapTileLayer mapFrontLayer){
        if(this.actors.get(0).getBody().x + this.actors.get(0).getBody().width / 2 <= this.SCREEN_WIDTH / 2){
            this.camera.position.x = this.SCREEN_WIDTH / 2;
            return;
        }
        if(this.actors.get(0).getBody().x + this.actors.get(0).getBody().width / 2 >= mapFrontLayer.getWidth() - this.SCREEN_WIDTH / 2){
            this.camera.position.x = mapFrontLayer.getWidth() - this.SCREEN_WIDTH / 2;
            return;
        }
        this.camera.position.x = this.actors.get(0).getBody().x + this.actors.get(0).getBody().width / 2;

    }
    private void updateCameraY(TiledMapTileLayer mapFrontLayer){
        if(this.actors.get(0).getBody().y + this.actors.get(0).getBody().height / 2 <= this.SCREEN_HEIGHT / 2){
            this.camera.position.y = this.SCREEN_HEIGHT / 2;
            return;
        }
        if(this.actors.get(0).getBody().y + this.actors.get(0).getBody().height / 2 >= mapFrontLayer.getHeight() - this.SCREEN_HEIGHT / 2){
            this.camera.position.y = mapFrontLayer.getHeight() - this.SCREEN_HEIGHT / 2;
            return;
        }
        this.camera.position.y = this.actors.get(0).getBody().y + this.actors.get(0).getBody().height / 2;
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