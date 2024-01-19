package org.libmansys;

import org.libmansys.DB.BookDAO;
import org.libmansys.DB.CDDAO;
import org.libmansys.DB.DVDDAO;

public class Main {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        CDDAO cdDAO = new CDDAO();
        DVDDAO dvdDAO = new DVDDAO();
    }
}