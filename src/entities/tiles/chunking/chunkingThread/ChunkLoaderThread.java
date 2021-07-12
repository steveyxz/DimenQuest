package entities.tiles.chunking.chunkingThread;

import engine.MainGameLoop;
import entities.dimensions.Dimension;
import entities.entity.moving.Player;
import entities.tiles.chunking.ChunkGenerator;
import org.lwjgl.opengl.Display;
import renderEngine.Loader;

public class ChunkLoaderThread implements Runnable {

    private final Loader loader;
    private final Dimension handler;
    private final ChunkGenerator generator;
    private final Player player;

    public ChunkLoaderThread(Loader loader, Dimension handler, ChunkGenerator generator, Player player) {
        this.loader = loader;
        this.player = player;
        this.generator = generator;
        this.handler = handler;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 20.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (!Display.isCloseRequested()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if (!Dimension.isChunksInUse) {
                    Dimension.isChunksInUse = true;
                    MainGameLoop.chunkInfo = handler.getLoads((int) Math.floor(player.getDimension().getPlayerPos().x), (int) Math.floor(player.getDimension().getPlayerPos().y), 4, 4, generator);
                    MainGameLoop.doCreateNewChunk = true;
                    Dimension.isChunksInUse = false;
                }
                delta--;
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("Loader FPS: " + frames);
                frames = 0;
            }
        }
    }
}
