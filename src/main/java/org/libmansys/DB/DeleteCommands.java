package org.libmansys.DB;

import java.util.ArrayList;

public interface DeleteCommands<T> {
    boolean removeValue(ArrayList<T> itemList, int ID);
}