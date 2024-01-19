package org.libmansys.DB;

import org.libmansys.Items.Item;

import java.util.ArrayList;

public interface ReadCommands<T> {
    ArrayList<T> retrieveAll();
    ArrayList<String> retrieveNames();
    ArrayList<T> retrieveByMaker(String maker);
    ArrayList<T> retrieveByID(int ID);
    ArrayList<T> retrieveOrderedListByYear();
    int retrieveItemCount();
    Item retrieveRandomItem();
    Item retrieveLatestItem();
}
