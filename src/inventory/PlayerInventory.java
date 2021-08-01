package inventory;

import entities.Camera;
import entities.entity.moving.Player;
import global.Constants;
import gui.ComponentHandler;
import gui.GuiRenderer;
import gui.components.GuiComponent;
import inventory.backpacks.Backpack;
import inventory.items.ItemGui;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventory implements ComponentHandler {

    private Player player;
    private ArrayList<InventorySlot> quickbarItems = new ArrayList<>();
    private Backpack backpack = new Backpack();
    private int size;
    private GuiRenderer renderer;

    public PlayerInventory(Player player, GuiRenderer renderer) {
        this(player, Constants.inventoryDefaultSize, renderer);
    }

    public PlayerInventory(Player player, int size, GuiRenderer renderer) {
        this.player = player;
        this.renderer = renderer;
        for (int i = 0; i < size; i++) {
            quickbarItems.add(null);
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<InventorySlot> getItems() {
        return quickbarItems;
    }

    public void addItem(InventorySlot item, int slot) {
        if (slot < size) {
            quickbarItems.set(slot, item);
        }
    }

    public void addItem(ItemGui item, int slot) {
        if (slot < size) {
            if (quickbarItems.get(slot) != null) {
                quickbarItems.get(slot).setItem(item);
            }
        }
    }

    public void updateItems() {

    }

    public InventorySlot getItem(int slot) {
        return quickbarItems.get(slot);
    }

    @Override
    public List<GuiComponent> getGuis() {
        return new ArrayList<>(quickbarItems);
    }

    public void render(Camera camera) {
        renderer.render(this, camera);
    }

    public void tick() {
        for (InventorySlot slot : quickbarItems) {
            slot.tick();
        }
    }

}
