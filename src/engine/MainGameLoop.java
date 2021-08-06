package engine;

import classes.Pair;
import classes.SpriteInfo;
import entities.Camera;
import entities.dimensions.types.StarterWorld;
import entities.entity.Entity;
import entities.entity.moving.Player;
import entities.entity.moving.Zombie;
import entities.tiles.PlayerCamera;
import entities.tiles.Tile;
import entities.tiles.chunking.ChunkGenerator;
import entities.tiles.chunking.TileChunk;
import entities.tiles.chunking.chunkingThread.ChunkLoaderThread;
import entities.tiles.tileTypes.decor.DecorTile;
import enums.GameState;
import enums.GuiType;
import global.Constants;
import global.GlobalMethods;
import global.ParticleSystems;
import gui.GuiHandler;
import gui.GuiRenderer;
import gui.components.StaticImage;
import gui.components.buttons.StartButton;
import inventory.items.ItemEntity;
import inventory.items.ItemType;
import listeners.KeyboardListener;
import listeners.MouseListener;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.shaders.texture.TextureShader;
import text.fontRendering.TextMaster;

import java.util.ArrayList;
import java.util.HashMap;

import static global.Constants.*;
import static global.GlobalMethods.encode;
import static global.GlobalValues.currentDimension;

public class MainGameLoop {

    public static boolean doCreateNewChunk = false;
    public static HashMap<HashMap<ArrayList<Pair<Integer>>, ArrayList<ArrayList<Entity>>>, ArrayList<ArrayList<TileChunk>>> chunkInfo = new HashMap<>();

    public static void main(String[] args) {

        //Create the display
        DisplayManager.createDisplay();

        //************************ INIT ALL THE GLOBAL INSTANCES ************************
        Loader loader = new Loader();

        initTileTextures(loader);
        initItemTextures(loader);
        initParticleSystems();

        TextMaster.init(loader);

        MasterRenderer renderer = new MasterRenderer();
        ParticleMaster.init(loader, renderer.getProjectionMatrix());

        Camera camera = new Camera();
        PlayerCamera playerCamera = new PlayerCamera();
        initDimensions(camera, renderer.getShader(), renderer);
        ChunkGenerator chunkGenerator = new ChunkGenerator(loader);

        GuiRenderer guiRenderer = new GuiRenderer(loader);
        GuiHandler guiHandler = new GuiHandler(guiRenderer);

        MouseListener mouseListener = new MouseListener();
        KeyboardListener keyboardListener = new KeyboardListener();

        //******************************* GENERATION START **************************************

        //******************************* CREATE DEFAULT OBJECTS *********************************

        Player player = new Player(Player.generatePlayerModel(loader), new Vector3f(0, 0, -0.2f), 0, 0f, 0f, camera, loader, new GameState[]{GameState.PLAY}, GlobalMethods.getAnimationTextures("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, encode(0, 1), encode(0, 2)), currentDimension, guiRenderer);
        ChunkLoaderThread loaderThread = new ChunkLoaderThread(loader, currentDimension, chunkGenerator, player);

        for (int i = 0; i < 10; i++) {
            guiHandler.addGui(new StaticImage(GlobalMethods.generateGuiTexture("heart", loader, new Vector2f(0.2f + -0.12f * i, 0.9f), new Vector2f(0.06f, 0.06f)), GuiType.IMAGE, guiHandler, new GameState[]{GameState.PLAY}));
        }
        guiHandler.addGui(StartButton.generateStartButton("start", "startDown", "startHover", guiHandler, new GameState[]{GameState.START}, 5, new Vector2f(0f, 0f), new Vector2f(0.5f, 0.2f), loader));

        Zombie testZombie = new Zombie(new SpriteInfo(8, 0, entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, "entities"), new Vector3f(0, 0, -0.2f), 0, 0, 1, new GameState[]{GameState.PLAY}, new Vector2f(0, 0), loader);
        testZombie.getModel().setGlobalZIndex(3);
        currentDimension.addEntity(testZombie);
        ItemEntity testItem = new ItemEntity(oakWoodLogModel, new Vector3f(0, 0, -0.2f), 20, 0, 0, new GameState[] {GameState.PLAY}, ItemType.OAK_WOOD_LOG);
        testItem.getModel().setGlobalZIndex(2);
        currentDimension.addEntity(testItem);

        //******************************* START THE GAME LOOP ******************************

        Thread t = new Thread(loaderThread);

        long lastTime = System.nanoTime();
        double amountOfTicks = 30.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        //Game loop
        if (Constants.useNewThreadForChunks) {
            t.start();
        }
        while (!Display.isCloseRequested()) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            currentDimension.process();
            ParticleMaster.update();

            camera.frame(player);

            if (doCreateNewChunk) {
                doCreateNewChunk = false;
                for (HashMap<ArrayList<Pair<Integer>>, ArrayList<ArrayList<Entity>>> i : chunkInfo.keySet()) {
                    for (ArrayList<Pair<Integer>> a : i.keySet()) {
                        currentDimension.loadDimension(chunkInfo.get(i).get(0), chunkInfo.get(i).get(1), a, i.get(a), chunkGenerator);
                    }
                }
            }

            while (delta >= 1) {
                if (currentDimension.getChunkAt(currentDimension.getPlayerPos().x, currentDimension.getPlayerPos().y) != null) {
                    for (Tile tile : currentDimension.getChunkAt(currentDimension.getPlayerPos().x, currentDimension.getPlayerPos().y).getTiles()) {
                        //System.out.println(tile.getOnTop());
                    }
                }
                player.tick();
                guiHandler.tick();
                currentDimension.tick();
                camera.move(currentDimension, player, chunkGenerator);
                delta--;
            }

            //**************************** INPUTS FROM PLAYER CHECK **************************
            mouseListener.checkClick();
            keyboardListener.checkClick();

            //**************************** CAMERA ********************************

            //**************************** RENDER EVERYTHING ****************************

            //Render the entities

            currentDimension.render();
            ParticleMaster.render(camera);

            guiHandler.render(camera);
            TextMaster.render();

            if (player.getDisplayOn().contains(Constants.gameState)) {
                renderer.getPlayerShader().start();
                renderer.getPlayerShader().changeView(playerCamera);
                renderer.getRenderer().renderPlayer(player, renderer.getPlayerShader());
                renderer.getPlayerShader().stop();
            }


            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }

            DisplayManager.updateDisplay();

        }

        //**************************** CLEAN UP ******************************
        TextMaster.cleanUp();
        loader.cleanUp();
        renderer.cleanUp();
        guiRenderer.cleanUp();
        ParticleMaster.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static void initTileTextures(Loader loader) {
        grassModel = DecorTile.generateModel(loader, 8, 3);
        dirtModel = DecorTile.generateModel(loader, 5, 3);
        treeModel = DecorTile.generateModel(loader, 3, 4);
        sandModel = DecorTile.generateModel(loader, 0, 0);
    }

    private static void initItemTextures(Loader loader) {
        oakWoodLogModel = ItemEntity.generateModel(loader, 0, 0);
    }

    private static void initDimensions(Camera camera, TextureShader shader, MasterRenderer renderer) {
        starterDimension = new StarterWorld(camera, shader, renderer);
    }

    private static void initParticleSystems() {
        ParticleSystems.walkingParticles = new ParticleSystem(15, 0.02f, 0.3f, 0.6f, 0.005f, ParticleTexture.generateTexture("particles", 0, 0, Constants.particleSpriteSheetTextureWidth, Constants.particleSpriteSheetTextureHeight, Constants.particleSpriteSheetGap));
    }
}
