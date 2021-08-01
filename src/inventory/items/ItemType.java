package inventory.items;

import global.Constants;
import models.TexturedModel;

public enum ItemType {
    GENERIC(null),
    OAK_WOOD_LOG(Constants.oakWoodLogModel);

    private TexturedModel texture;

    ItemType(TexturedModel texture) {
        this.texture = texture;
    }
}
