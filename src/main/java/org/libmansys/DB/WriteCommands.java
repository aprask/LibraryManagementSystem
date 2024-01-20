package org.libmansys.DB;

import org.libmansys.Items.Item;

import java.util.Date;

public interface WriteCommands {
    void changeItemName(Item item);
    void changeItemID(Item item);
    void changeItemPrice(Item item);
    void changeItemGenre(Item item);
    void changeItemYear(Item item);
    void changeItemOwner(Item item);
    void restockItem(Item item);
    void createItem(Item item);

}
