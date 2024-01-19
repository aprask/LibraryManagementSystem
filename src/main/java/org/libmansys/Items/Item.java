package org.libmansys.Items;

import java.util.Objects;

public abstract class Item implements Comparable<Item> {
    private String itemName;
    private int itemID;
    private double itemCost;
    public Item(String itemName, int itemID, double itemCost)
    {
        this.itemName = itemName;
        this.itemID = itemID;
        this.itemCost = itemCost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }
    @Override
    public int compareTo(Item o) {
        if(o.itemCost > this.itemCost)
        {
            return 1;
        }
        else if(o.itemCost < this.itemCost)
        {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemID == item.itemID && Double.compare(item.itemCost, itemCost) == 0 && Objects.equals(itemName, item.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, itemID, itemCost);
    }
}
