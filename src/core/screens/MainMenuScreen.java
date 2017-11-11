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
import com.badlogic.gdx.utils.viewport.Viewport;
import core.ScreenHandler;

/**
 *
 * @author Augustop
 */
public class MainMenuScreen implements Screen {
    private final ScreenHandler game;
    private Texture title;
    private OrthographicCamera camera;

    public MainMenuScreen(ScreenHandler game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1280, 720);
        this.title = new Texture(Gdx.files.internal("assets/img/titlescreen.jpg"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.game.batch.setProjectionMatrix(camera.combined);

        this.game.batch.begin();
        this.game.batch.draw(this.title, 0, 0, this.camera.viewportWidth, this.camera.viewportHeight);
        this.game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isTouched()){
//            this.game.setScreen(new GameScreen(game));
            this.dispose();
            Gdx.app.exit();
        }
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
    }
}
