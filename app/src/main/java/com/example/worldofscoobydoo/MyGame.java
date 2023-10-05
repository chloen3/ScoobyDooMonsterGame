package com.example.worldofscoobydoo;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
public class MyGame extends ApplicationAdapter {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Viewport viewport;

    @Override
    public void create() {
        // Load your Tiled Map (replace "yourmap.tmx" with your map's file path)
        tiledMap = new TmxMapLoader().load("yourmap.tmx");

        // Create a viewport to manage the screen's size
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize the map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the viewport to update the camera's projection matrix
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        // Render the map using the camera's projection matrix
        mapRenderer.setView((OrthographicCamera) viewport.getCamera());
        mapRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport when the screen is resized
        viewport.update(width, height);
    }
}
