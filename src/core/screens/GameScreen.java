/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import core.AssetsManager;
import core.map.MapRenderer;
import core.player.PlayerHandler;

/**
 *
 * @author Augustop
 */
public class GameScreen implements Screen {
    private final ScreenHandler game;
    private final PlayerHandler player;
    private final MapRenderer mapRenderer;
//    private float unitScale = 1 / 32f; // tile size
//    private TiledMap map;
//    private OrthogonalTiledMapRenderer mapRenderer;
    

    private OrthographicCamera camera;

    public GameScreen(final ScreenHandler game, PlayerHandler player) {
        this.game = game;
        this.player = player;
        this.mapRenderer = new MapRenderer();
        
        this.camera = new OrthographicCamera();
        
        this.camera.setToOrtho(false, 400, 400);
        
        this.camera.update();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.game.batch.setProjectionMatrix(camera.combined);
        
        this.game.batch.begin();
        this.game.batch.draw(this.player.getStandImg(), 10, 100);
        this.game.batch.end();
        
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
//        this.map.dispose();
//        this.mapRenderer.dispose();
        AssetsManager.assets.clear();
        this.game.dispose();
    }

}