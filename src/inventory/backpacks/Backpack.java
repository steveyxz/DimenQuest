package inventory.backpacks;

import inventory.InventorySlot;

import java.util.ArrayList;

public class Backpack {

    public ArrayList<InventorySlot> items = new ArrayList<>();
    public boolean opened = false;

    public int progression = 0;

    public Backpack() {

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
