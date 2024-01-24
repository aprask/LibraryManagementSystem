package org.libmansys.Internals;

import org.libmansys.Items.Item;

import java.util.ArrayList;

public interface DeleteCommands<T extends Item> {
    boolean removeValue(ArrayList<T> itemList, int ID);
}