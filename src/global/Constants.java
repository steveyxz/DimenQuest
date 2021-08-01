package global;

import entities.dimensions.types.StarterWorld;
import enums.GameState;
import enums.PlayState;
import models.TexturedModel;
import renderEngine.spriteHandler.SpriteGlobal;

import java.util.ArrayList;

import static global.GlobalMethods.encode;

public class Constants {
    //SETTINGS
    /**
     * TOGGLE THIS ONLY IF PERFORMANCE IS MAJOR
     * !!! Turning this on will cause minor chunk flashes during chunk generation. Use at your own risk. !!!
     */
    public static final boolean useNewThreadForChunks = false;
    //SPRITE SHEET INFO
    public static final int particleSpriteSheetHeight = 1280;
    public static final int particleSpriteSheetWidth = 1280;
    public static final int particleSpriteSheetTextureWidth = 64;
    public static final int particleSpriteSheetTextureHeight = 64;
    public static final int particleSpriteSheetGap = 0;
    public static final int tileSpriteSheetHeight = 2172;
    public static final int tileSpriteSheetWidth = 2172;

    //CHUNK INFO
    public static final int tileSpriteSheetTextureWidth = 64;
    public static final int tileSpriteSheetTextureHeight = 64;
    public static final int tileSpriteSheetGap = 4;
    public static final int entitySpriteSheetHeight = 1280;
    public static final int entitySpriteSheetWidth = 1280;
    public static final int entitySpriteSheetTextureWidth = 64;
    public static final int entitySpriteSheetTextureHeight = 64;
    public static final int entitySpriteSheetGap = 0;
    public static final int guiSpriteSheetHeight = 5120;
    public static final int guiSpriteSheetWidth = 5120;
    public static final int guiSpriteSheetTextureWidth = 256;
    public static final int guiSpriteSheetTextureHeight = 256;
    public static final int guiSpriteSheetGap = 0;

    //INVENTORY
    public static final int inventoryMaxWidth = 9;
    public static final int inventoryDefaultSize = 9;

    //BACKPACKS
    public static final int[] backpackSizeProg = {0, 9, 18, 27, 36, 45, 81};
    public static final String[] backpackNameProg = {"No", "Mini", "Small", "Regular", "Large", "XL", "Jumbo"}; //Combine with ' Backpack' to create names


    //PLAYER INFO
    public static final int playerWalkDelay = 10;
    public static final int playerWalkRenderChangeDelay = 2;
    public static final ArrayList<Integer> playerDown = GlobalMethods.getAnimationTextures("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, encode(0, 0), encode(0, 1));
    public static final ArrayList<Integer> playerRight = GlobalMethods.getAnimationTextures("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, encode(2, 0), encode(2, 1));
    public static final ArrayList<Integer> playerLeft = GlobalMethods.getAnimationTextures("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, encode(6, 0), encode(6, 1));
    public static final ArrayList<Integer> playerUp = GlobalMethods.getAnimationTextures("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, encode(4, 0), encode(4, 1));
    public static final ArrayList<Integer> playerDownLeft = GlobalMethods.getAnimationTextures("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, encode(7, 0), encode(7, 1));
    public static final ArrayList<Integer> playerDownRight = GlobalMethods.getAnimationTextures("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, encode(1, 0), encode(1, 1));
    public static final ArrayList<Integer> playerUpLeft = GlobalMethods.getAnimationTextures("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, encode(5, 0), encode(5, 1));
    public static final ArrayList<Integer> playerUpRight = GlobalMethods.getAnimationTextures("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, encode(3, 0), encode(3, 1));
    public static final int playerDownPassive = SpriteGlobal.loadTexture("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, 0, 2);
    public static final int playerRightPassive = SpriteGlobal.loadTexture("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, 2, 2);
    public static final int playerLeftPassive = SpriteGlobal.loadTexture("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, 6, 2);
    public static final int playerUpPassive = SpriteGlobal.loadTexture("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, 4, 2);
    public static final int playerDownLeftPassive = SpriteGlobal.loadTexture("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, 7, 2);
    public static final int playerDownRightPassive = SpriteGlobal.loadTexture("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, 1, 2);
    public static final int playerUpLeftPassive = SpriteGlobal.loadTexture("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, 5, 2);
    public static final int playerUpRightPassive = SpriteGlobal.loadTexture("entities", entitySpriteSheetTextureWidth, entitySpriteSheetTextureHeight, entitySpriteSheetGap, 3, 2);
    public static final String inputChars = "abcdefghijklmnopqrstuvwxyz1234567890()|$/[]{}\\@!?%'#;:<>+^\"=`";
    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    //GAME STATES
    public static PlayState playState = PlayState.PLAY;
    public static GameState gameState = GameState.START;

    //TILE INFO
    public static float tileWidth = 0.03f;

    //TILE TEXTURE MODELS
    public static TexturedModel grassModel;
    public static TexturedModel dirtModel;
    public static TexturedModel treeModel;
    public static TexturedModel sandModel;
    public static int chunkSpreadDiffer = 10;


    //Dimensions
    public static int chunkWidth = 8;

    //Misc
    public static int chunkHeight = 8;
    public static StarterWorld starterDimension;
    public static float[] rectVertices = {
            0f, 0f, 0f,//v0
            0f, -1, 0f,//v1
            1, -1, 0f,//v2
            1, 0, 0f,//v3
    };

    public static int[] rectIndices = {
            0, 1, 3,//top left triangle (v0, v1, v3)
            3, 1, 2//bottom right triangle (v3, v1, v2)
    };

    public static float[] rectTextureCoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };

}
