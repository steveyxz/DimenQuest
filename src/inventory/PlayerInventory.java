package inventory;

import entities.Camera;
import entities.entity.moving.Player;
import enums.GameState;
import enums.GuiType;
import global.Constants;
import gui.GuiHandler;
import gui.GuiRenderer;
import gui.GuiTexture;
import gui.components.GuiComponent;
import inventory.backpacks.Backpack;
import inventory.items.ItemGui;
import inventory.items.ItemState;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventory extends GuiHandler {

    private Player player;
    private ArrayList<InventorySlot> quickbarItems = new ArrayList<>();
    private Backpack backpack;
    private int quickBarSize;
    private GuiRenderer renderer;

    public PlayerInventory(Player player, GuiRenderer renderer) {
        this(player, Constants.quickbarSize, renderer);
    }

    public PlayerInventory(Player player, int quickBarSize, GuiRenderer renderer) {
        super(renderer);
        this.player = player;
        this.renderer = renderer;
        this.backpack = new Backpack(player, renderer);
        int count = 1;
        for (int i = 0; i < quickBarSize; i++) {
            InventorySlot slot = new InventorySlot(GuiType.INVENTORY_SLOT, this, new GameState[]{GameState.PLAY}, 1, count, -1);
            slot.setShown(true);
            count++;
        }
        setItem(new ItemGui(new GuiTexture(Constants.oakWoodLogModel.getTexture().getTextureID(), new Vector2f(0, 0), new Vector2f(1, 1)), GuiType.INVENTORY_ITEM, this, new GameState[] {GameState.START}, ItemState.INVENTORY), 0);
    }

    public int getSize() {
        return quickBarSize + backpack.size;
    }

    public int getQuickBarSize() {
        return quickBarSize;
    }

    public int getBackpackSize() {
        return backpack.size;
    }

    public void setQuickBarSize(int quickBarSize) {
        this.quickBarSize = quickBarSize;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<InventorySlot> getItems() {
        return quickbarItems;
    }

    public void setItem(InventorySlot item, int slot) {
        if (slot < quickBarSize) {
            updateItems();
            quickbarItems.set(slot, item);
        }
    }

    public void setItem(ItemGui item, int slot) {
        if (slot < quickBarSize) {
            if (quickbarItems.get(slot) != null) {
                updateItems();
                quickbarItems.get(slot).setItem(item);
            }
        }
    }

    public void updateItems() {
        backpack.reloadItems();
        for (InventorySlot slot : quickbarItems) {
            slot.getTexture().setPos(new Vector2f(-1 + Constants.quickBarXOffset + (slot.getInventoryX() + 1) * Constants.inventorySlotWidth * 2, -1 + Constants.quickBarBottomOffset + Constants.inventorySlotHeight));
            slot.getActiveTexture().setPos(new Vector2f(-1 + Constants.quickBarXOffset + (slot.getInventoryX() + 1) * Constants.inventorySlotWidth * 2, -1 + Constants.quickBarBottomOffset + Constants.inventorySlotHeight));
            slot.getDefaultTexture().setPos(new Vector2f(-1 + Constants.quickBarXOffset + (slot.getInventoryX() + 1) * Constants.inventorySlotWidth * 2, -1 + Constants.quickBarBottomOffset + Constants.inventorySlotHeight));
        }
    }

    public void addBackpackItem(InventorySlot item, int slot) {
        backpack.setItem(item, slot);
    }

    public void addBackpackItem(ItemGui item, int slot) {
        backpack.setItem(item, slot);
    }

    public InventorySlot getItem(int slot) {
        return quickbarItems.get(slot);
    }

    @Override
    public GuiComponent getGui(int index) {
        if (index < quickbarItems.size()) {
            return quickbarItems.get(index);
        }
        return null;
    }

    @Override
    public void removeGui(GuiComponent component) {
        if (component instanceof InventorySlot) {
            updateItems();
            quickbarItems.remove(component);
        }
    }

    @Override
    public void addGui(GuiComponent t) {
        if (t instanceof InventorySlot) {
            quickbarItems.add((InventorySlot) t);
            updateItems();
        }
    }

    @Override
    public List<GuiComponent> getGuis() {
        ArrayList<GuiComponent> returned = new ArrayList<>(quickbarItems);
        for (InventorySlot slot : quickbarItems) {
            ItemGui item = slot.getItem();
            if (item != null) {
                returned.add(item);
            }
        }
        return returned;
    }

    @Override
    public void render(Camera camera) {
        renderer.render(this, camera);
        backpack.render(camera);
    }

    @Override
    public void tick() {
        if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
            if (backpack.isOpened()) {
                backpack.close();
            } else {
                backpack.open();
            }
        }
        backpack.tick();
        for (InventorySlot slot : quickbarItems) {
            slot.tick();
        }
    }

}
