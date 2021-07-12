package entities.dimensions.types;

import classes.Pair;
import entities.Camera;
import entities.dimensions.Dimension;
import entities.tiles.chunking.ChunkGenerator;
import enums.DimensionType;
import global.GlobalMethods;
import org.lwjgl.util.vector.Vector2f;
import renderEngine.MasterRenderer;
import renderEngine.shaders.texture.TextureShader;

import java.util.ArrayList;

public class StarterWorld extends Dimension {
    public StarterWorld(Camera camera, TextureShader shader, MasterRenderer renderer) {
        super(camera, shader, renderer, DimensionType.STARTER_WORLD);
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public void createChunks(ChunkGenerator generator, ArrayList<Pair<Integer>> info) {
        for (Pair<Integer> p : info) {
            generator.generateChunk(GlobalMethods.getSpreadBiome(this, GlobalMethods.encode(p.getA(), p.getB())), new Vector2f(p.getA(), p.getB()), this);
        }
    }


}
