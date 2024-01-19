package org.libmansys.DB;

import java.util.ArrayList;

public interface ReadCommands<T> {
    ArrayList<T> retrieveAll();
    ArrayList<String> retrieveNames();
    ArrayList<T> retrieveByMaker(String maker);
    ArrayList<T> retrieveByID(int ID);
}
