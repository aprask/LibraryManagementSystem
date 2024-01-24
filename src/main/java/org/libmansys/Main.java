package org.libmansys;

import org.libmansys.Internals.BookDAO;
import org.libmansys.Internals.CDDAO;
import org.libmansys.Internals.DVDDAO;

public class Main {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        CDDAO cdDAO = new CDDAO();
        DVDDAO dvdDAO = new DVDDAO();
    }
}