package inventory.backpacks;

import entities.Camera;
import entities.entity.moving.Player;
import global.Constants;
import gui.GuiHandler;
import gui.GuiRenderer;
import gui.components.GuiComponent;
import inventory.InventorySlot;
import inventory.items.ItemGui;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Backpack extends GuiHandler {

    public ArrayList<InventorySlot> items = new ArrayList<>();
    public boolean opened = false;

    public int progression = 0;
    public int size = 0;
    public String name = Constants.backpackNameProg[size];

    public Player player;

    public Backpack(Player player, GuiRenderer renderer) {
        super(renderer);
        this.player = player;
    }

    public void setItem(InventorySlot item, int slot) {
        if (slot > size - 1) {
            return;
        }
        items.set(slot, item);
    }

    public void setItem(ItemGui item, int slot) {
        if (slot > size - 1) {
            return;
        }
        items.get(slot).setItem(item);
    }

    public InventorySlot getItem(int slot) {
        return items.get(slot);
    }

    public void progress() {
        progression++;
        reloadItems();
    }

    public void progressBack() {
        progression--;
        reloadItems();
    }

    public void reloadItems() {
        this.size = Constants.backpackSizeProg[progression];
        this.name = Constants.backpackNameProg[progression];
        for (InventorySlot slot : items) {
            slot.getTexture().setPos(new Vector2f(Constants.inventoryXOffset + slot.getInventoryX()*Constants.inventorySlotWidth*2, Constants.inventoryYOffset + slot.getInventoryX()*Constants.inventorySlotHeight*2));
        }
    }

    public void open() {
        opened = true;
        for (InventorySlot slot : items) {
            slot.setShown(true);
            slot.getItem().setShown(true);
        }
    }

    public void close() {
        opened = false;
        for (InventorySlot slot : items) {
            slot.setShown(false);
            slot.getItem().setShown(false);
        }
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public int getProgression() {
        return progression;
    }

    public void setProgression(int progression) {
        this.progression = progression;
        reloadItems();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        reloadItems();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void addGui(GuiComponent t) {
        if (t instanceof InventorySlot) {
            items.add((InventorySlot) t);
            reloadItems();
        }
    }

    @Override
    public void render(Camera camera) {
        renderer.render(this, camera);
    }

    @Override
    public void tick() {
        for (InventorySlot slot : items) {
            if (slot.isShown()) {
                slot.tick();
            }
        }
    }

    @Override
    public GuiComponent getGui(int index) {
        if (index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    @Override
    public void removeGui(GuiComponent component) {
        if (component instanceof InventorySlot) {
            items.remove(component);
            reloadItems();
        }
    }

    @Override
    public List<GuiComponent> getGuis() {
        return new ArrayList<>(items);
    }
}
