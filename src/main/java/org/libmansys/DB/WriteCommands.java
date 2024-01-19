package org.libmansys.DB;

import org.libmansys.Items.Item;

import java.util.Date;

public interface WriteCommands {
    void changeItemName(Item item, String name);
    void changeItemID(Item item, int ID);
    void changeItemPrice(Item item, double price);
    void changeItemGenre(Item item, String genre);
    void changeItemYear(Item item, Date date);
    void changeItemOwner(Item item, String maker);
    void restockItem(Item item);
    void createItem(Item item);

}
