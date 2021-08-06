package inventory.backpacks;

import entities.entity.moving.Player;
import inventory.InventorySlot;
import inventory.items.ItemGui;

import java.util.ArrayList;

public class Backpack {

    public ArrayList<InventorySlot> items = new ArrayList<>();
    public boolean opened = false;

    public int progression = 0;
    public int size = 0;

    public Player player;

    public Backpack(Player player) {
        this.player = player;
    }

    public void setItem(InventorySlot item, int slot) {
        items.set(slot, item);
    }

    public void setItem(ItemGui item, int slot) {
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

    }



}
