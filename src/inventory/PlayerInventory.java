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
    private Backpack backpack;
    private int quickBarSize;
    private GuiRenderer renderer;

    public PlayerInventory(Player player, GuiRenderer renderer) {
        this(player, Constants.quickbarSize, renderer);
    }

    public PlayerInventory(Player player, int quickBarSize, GuiRenderer renderer) {
        this.player = player;
        this.renderer = renderer;
        this.backpack = new Backpack(player);
        for (int i = 0; i < quickBarSize; i++) {
            quickbarItems.add(null);
        }
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
            quickbarItems.set(slot, item);
        }
    }

    public void setItem(ItemGui item, int slot) {
        if (slot < quickBarSize) {
            if (quickbarItems.get(slot) != null) {
                quickbarItems.get(slot).setItem(item);
            }
        }
    }

    public void updateItems() {
        backpack.reloadItems();
        for (InventorySlot slot : quickbarItems) {
            
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
