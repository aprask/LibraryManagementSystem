package org.libmansys.DB;

import org.libmansys.Items.Item;

import java.util.ArrayList;

public interface ReadCommands<T> {
    ArrayList<T> retrieveAll();
    ArrayList<String> retrieveNames();
    ArrayList<T> retrieveByMaker(String maker);
    ArrayList<T> retrieveByID(int ID);
    ArrayList<T> retrieveInDescendingOrder(ArrayList<T> itemList);
    ArrayList<T> retrieveInAscendingOrder(ArrayList<T> itemList);
    ArrayList<T> retrieveInAlphabeticalOrder(ArrayList<T> itemList);
    int retrieveItemCount(ArrayList<T> itemList);
    Item retrieveRandomItem(ArrayList<T> itemList);
    Item retrieveLatestItem(ArrayList<T> itemList);
}
