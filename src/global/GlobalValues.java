package global;

import classes.Pair;
import entities.dimensions.Dimension;
import enums.Biome;
import enums.DimensionType;
import enums.PlayState;
import particles.ParticleTexture;

import java.util.HashMap;

import static global.Constants.starterDimension;

public class GlobalValues {

    public static boolean isMoving = false;
    public static HashMap<Pair<Integer>, ParticleTexture> particleTextures = new HashMap<>();

    public static HashMap<DimensionType, HashMap<Long, Biome>> specifiedChunkTypes = new HashMap<>();

    public static Dimension currentDimension = starterDimension;
}
