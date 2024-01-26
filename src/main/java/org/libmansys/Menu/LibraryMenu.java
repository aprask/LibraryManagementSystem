package org.libmansys.Menu;

import org.libmansys.Internals.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.libmansys.Items.*;
public class LibraryMenu extends JFrame implements ActionListener {
    private static BookDAO bookdao = new BookDAO();
    private static CDDAO cddao = new CDDAO();
    private static DVDDAO dvddao = new DVDDAO();
    JMenuItem rentBook;
    JMenuItem rentCD;
    JMenuItem rentDVD;
    JMenuItem exitProgram;
    JMenuItem alterName;
    JMenuItem alterID;
    JMenuItem alterPrice;
    JMenuItem alterGenre;
    JMenuItem alterDate;
    JMenuItem alterMaker;
    JMenuItem getNames;
    JMenuItem getItemByMaker;
    JMenuItem getItemByID;
    JMenuItem getDescendingList;
    JMenuItem getAscendingList;
    JMenuItem getAlphabeticalList;
    JMenuItem getItemCount;
    JMenuItem getRandomItem;
    JMenuItem getLatestItem;
    JMenuItem deleteItem;
    JMenuItem createItem;
    JMenuItem itemRestock;
    JFrame paneFrame;
    public LibraryMenu()
    {
        buildGUI();
    }
    public void buildGUI()
    {
        JMenuBar menu = new JMenuBar();
        menu.setBackground(Color.gray);

        JMenu rentMenu = new JMenu("Rent");
        JMenu writeMenu = new JMenu("Write");
        JMenu readMenu = new JMenu("Read");
        JMenu deleteMenu = new JMenu("Delete");
        JMenu exitMenu = new JMenu("Exit");
        JMenu createMenu = new JMenu("Create");

        rentBook = new JMenuItem("Rent Book");
        rentCD = new JMenuItem("Rent CD");
        rentDVD = new JMenuItem("Rent DVD");
        itemRestock = new JMenuItem("Restock");

        alterName = new JMenuItem("Change Item Name");
        alterID = new JMenuItem("Change Item ID");
        alterPrice = new JMenuItem("Change Item Price");
        alterGenre = new JMenuItem("Change Item Genre");
        alterDate = new JMenuItem("Change Item Date");
        alterMaker = new JMenuItem("Change Item Maker");

        getNames = new JMenuItem("Retrieve All Names");
        getItemByMaker = new JMenuItem("Retrieve Items Based on Maker");
        getItemByID = new JMenuItem("Retrieve Items Based on ID");
        getDescendingList = new JMenuItem("Retrieve Items in Descending Order");
        getAscendingList = new JMenuItem("Retrieve Items in Ascending Order");
        getAlphabeticalList = new JMenuItem("Retrieve Items in Alphabetical Order");
        getItemCount = new JMenuItem("Retrieve Item Count");
        getRandomItem = new JMenuItem("Retrieve Random Item");
        getLatestItem = new JMenuItem("Retrieve Latest Item");

        deleteItem = new JMenuItem("Remove Value");


        menu.add(readMenu);
        readMenu.add(getNames);
        getNames.addActionListener(this);
        readMenu.add(getItemByMaker);
        getItemByMaker.addActionListener(this);
        readMenu.add(getItemByID);
        getItemByID.addActionListener(this);
        readMenu.add(getDescendingList);
        getDescendingList.addActionListener(this);
        readMenu.add(getAscendingList);
        getAscendingList.addActionListener(this);
        readMenu.add(getAlphabeticalList);
        getAlphabeticalList.addActionListener(this);
        readMenu.add(getItemCount);
        getItemCount.addActionListener(this);
        readMenu.add(getRandomItem);
        getRandomItem.addActionListener(this);
        readMenu.add(getLatestItem);
        getLatestItem.addActionListener(this);

        menu.add(writeMenu);
        writeMenu.add(alterName);
        alterName.addActionListener(this);
        writeMenu.add(alterID);
        alterID.addActionListener(this);
        writeMenu.add(alterPrice);
        alterPrice.addActionListener(this);
        writeMenu.add(alterGenre);
        alterGenre.addActionListener(this);
        writeMenu.add(alterDate);
        alterDate.addActionListener(this);
        writeMenu.add(alterMaker);
        alterMaker.addActionListener(this);

        menu.add(deleteMenu);
        deleteMenu.add(deleteItem);
        deleteItem.addActionListener(this);

        menu.add(rentMenu);
        rentMenu.add(rentBook);
        rentBook.addActionListener(this);
        rentMenu.add(rentCD);
        rentCD.addActionListener(this);
        rentMenu.add(rentDVD);
        rentDVD.addActionListener(this);
        rentMenu.add(itemRestock);
        itemRestock.addActionListener(this);

        menu.add(createMenu);
        createItem = new JMenuItem("Create Item");
        createMenu.add(createItem);
        createItem.addActionListener(this);

        exitProgram = new JMenuItem("Exit");
        menu.add(exitMenu);
        exitMenu.add(exitProgram);
        exitProgram.addActionListener(this);

        JLabel label = new JLabel();
        label.setBackground(Color.white);
        label.setIcon(null);
        label.setText("Library Management System");
        label.setFont(new Font("Serif", Font.BOLD, 50));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setOpaque(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        this.setLayout(new FlowLayout());
        this.setJMenuBar(menu);
        this.add(label);
        this.setTitle("Library Management System");
        this.setBackground(Color.white);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exitProgram)
        {
            System.exit(0);
        }
        else if(e.getSource()==alterName)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which book (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                Book book = bookdao.retrieveByID(convertedID).get(0);
                bookdao.changeItemName(book);
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which cd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                CD cd = cddao.retrieveByID(convertedID).get(0);
                cddao.changeItemName(cd);
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which dvd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                DVD dvd = dvddao.retrieveByID(convertedID).get(0);
                dvddao.changeItemName(dvd);
            }

        }
        else if(e.getSource()==alterID)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which book (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                Book book = bookdao.retrieveByID(convertedID).get(0);
                bookdao.changeItemID(book);
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which cd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                CD cd = cddao.retrieveByID(convertedID).get(0);
                cddao.changeItemID(cd);
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which dvd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                DVD dvd = dvddao.retrieveByID(convertedID).get(0);
                dvddao.changeItemID(dvd);
            }
        }
        else if(e.getSource()==getAlphabeticalList)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                for(int i = 0; i < bookdao.retrieveAll().size(); i++)
                {
                    JOptionPane.showMessageDialog(null,bookdao.retrieveInAlphabeticalOrder(bookdao.retrieveAll()).get(i).getItemName());
                }
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                for(int i = 0; i < bookdao.retrieveAll().size(); i++)
                {
                    JOptionPane.showMessageDialog(null,cddao.retrieveInAlphabeticalOrder(cddao.retrieveAll()).get(i).getItemName());
                }
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                for(int i = 0; i < bookdao.retrieveAll().size(); i++)
                {
                    JOptionPane.showMessageDialog(null,dvddao.retrieveInAlphabeticalOrder(dvddao.retrieveAll()).get(i).getItemName());
                }
            }
        }
        else if(e.getSource()==alterPrice)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which book (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                Book book = bookdao.retrieveByID(convertedID).get(0);
                bookdao.changeItemPrice(book);
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which cd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                CD cd = cddao.retrieveByID(convertedID).get(0);
                cddao.changeItemPrice(cd);
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which dvd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                DVD dvd = dvddao.retrieveByID(convertedID).get(0);
                dvddao.changeItemPrice(dvd);
            }

        }
        else if(e.getSource()==alterGenre)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which book (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                Book book = bookdao.retrieveByID(convertedID).get(0);
                bookdao.changeItemGenre(book);
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which cd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                CD cd = cddao.retrieveByID(convertedID).get(0);
                cddao.changeItemGenre(cd);
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which dvd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                DVD dvd = dvddao.retrieveByID(convertedID).get(0);
                dvddao.changeItemGenre(dvd);
            }
        }
        else if(e.getSource()==alterDate)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which book (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                Book book = bookdao.retrieveByID(convertedID).get(0);
                bookdao.changeItemYear(book);
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which cd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                CD cd = cddao.retrieveByID(convertedID).get(0);
                cddao.changeItemYear(cd);
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which dvd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                DVD dvd = dvddao.retrieveByID(convertedID).get(0);
                dvddao.changeItemYear(dvd);
            }
        }
        else if(e.getSource()==alterMaker)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which book (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                Book book = bookdao.retrieveByID(convertedID).get(0);
                bookdao.changeItemOwner(book);
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which cd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                CD cd = cddao.retrieveByID(convertedID).get(0);
                cddao.changeItemOwner(cd);
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                String ID = JOptionPane.showInputDialog(paneFrame,"Which dvd (select by id)? ");
                int convertedID = Integer.parseInt(ID);
                DVD dvd = dvddao.retrieveByID(convertedID).get(0);
                dvddao.changeItemOwner(dvd);
            }
        }
        else if(e.getSource()==getNames)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null, bookdao.retrieveNames(), "All Items", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null, cddao.retrieveNames(), "All Items", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null, dvddao.retrieveNames(), "All Items", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if(e.getSource()==getItemByMaker)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                String maker = JOptionPane.showInputDialog("The maker? ");
                JOptionPane.showMessageDialog(null, bookdao.retrieveByMaker(maker).get(0).getItemName());
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                String maker = JOptionPane.showInputDialog("The maker? ");
                JOptionPane.showMessageDialog(null,cddao.retrieveByMaker(maker).get(0).getItemName());
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                String maker = JOptionPane.showInputDialog("The maker? ");
                JOptionPane.showMessageDialog(null,dvddao.retrieveByMaker(maker).get(0).getItemName());
            }
        }
        else if(e.getSource()==getItemByID)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                int id = Integer.parseInt(JOptionPane.showInputDialog("The ID? "));
                JOptionPane.showMessageDialog(null, bookdao.retrieveByID(id).get(0).getItemName());
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                int id = Integer.parseInt(JOptionPane.showInputDialog("The ID? "));
                JOptionPane.showMessageDialog(null,cddao.retrieveByID(id).get(0).getItemName());
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                int id = Integer.parseInt(JOptionPane.showInputDialog("The ID? "));
                JOptionPane.showMessageDialog(null,dvddao.retrieveByID(id).get(0).getItemName());
            }
        }
        else if(e.getSource()==getDescendingList)
        {
            StringBuilder descendingList = new StringBuilder();
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                for(int i = 0; i < bookdao.retrieveAll().size(); i++)
                {
                    descendingList.append(bookdao.retrieveInDescendingOrder(bookdao.retrieveAll()).get(i).getItemName());
                    if(i != bookdao.retrieveAll().size()-1) descendingList.append(", ");
                }
                JOptionPane.showMessageDialog(null,descendingList);
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                for(int i = 0; i < bookdao.retrieveAll().size(); i++)
                {
                    descendingList.append(cddao.retrieveInDescendingOrder(cddao.retrieveAll()).get(i).getItemName());
                    if(i != cddao.retrieveAll().size()-1) descendingList.append(", ");
                }
                JOptionPane.showMessageDialog(null,descendingList);
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                for(int i = 0; i < bookdao.retrieveAll().size(); i++)
                {
                    descendingList.append(dvddao.retrieveInDescendingOrder(dvddao.retrieveAll()).get(i).getItemName());
                    if(i != dvddao.retrieveAll().size()-1) descendingList.append(", ");
                }
                JOptionPane.showMessageDialog(null,descendingList);
            }

        }
        else if(e.getSource()==getAscendingList)
        {
            StringBuilder ascendingList = new StringBuilder();
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                for(int i = 0; i < bookdao.retrieveAll().size(); i++)
                {
                    ascendingList.append(bookdao.retrieveInAscendingOrder(bookdao.retrieveAll()).get(i).getItemName());
                    if(i != bookdao.retrieveAll().size()-1) ascendingList.append(", ");
                }
                JOptionPane.showMessageDialog(null,ascendingList);
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                for(int i = 0; i < bookdao.retrieveAll().size(); i++)
                {
                    ascendingList.append(cddao.retrieveInAscendingOrder(cddao.retrieveAll()).get(i).getItemName());
                    if(i != cddao.retrieveAll().size()-1) ascendingList.append(", ");
                }
                JOptionPane.showMessageDialog(null,ascendingList);
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                for(int i = 0; i < bookdao.retrieveAll().size(); i++)
                {
                    ascendingList.append(dvddao.retrieveInAscendingOrder(dvddao.retrieveAll()).get(i).getItemName());
                    if(i != dvddao.retrieveAll().size()-1) ascendingList.append(", ");
                }
                JOptionPane.showMessageDialog(null,ascendingList);
            }
        }
        else if(e.getSource()==getItemCount)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null, bookdao.retrieveItemCount(bookdao.retrieveAll()));
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null,cddao.retrieveItemCount(cddao.retrieveAll()));
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null,dvddao.retrieveItemCount(dvddao.retrieveAll()));
            }
        }
        else if(e.getSource()==getRandomItem)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null, bookdao.retrieveRandomItem(bookdao.retrieveAll()).getItemName());
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null,cddao.retrieveRandomItem(cddao.retrieveAll()).getItemName());
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null,dvddao.retrieveRandomItem(dvddao.retrieveAll()).getItemName());
            }
        }
        else if(e.getSource()==getLatestItem)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null, bookdao.retrieveLatestItem(bookdao.retrieveAll()).getItemName());
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null,cddao.retrieveLatestItem(cddao.retrieveAll()).getItemName());
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                JOptionPane.showMessageDialog(null,dvddao.retrieveLatestItem(dvddao.retrieveAll()).getItemName());
            }
        }
        else if(e.getSource()==deleteItem)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                int ID = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
                if(bookdao.removeValue(bookdao.retrieveAll(),ID))
                {
                    JOptionPane.showMessageDialog(null,"Deleted");
                }
                else JOptionPane.showMessageDialog(null,"Invalid Input");
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                int ID = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
                if(cddao.removeValue(cddao.retrieveAll(),ID))
                {
                    JOptionPane.showMessageDialog(null,"Deleted");
                }
                else JOptionPane.showMessageDialog(null,"Invalid Input");
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                int ID = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
                if(dvddao.removeValue(dvddao.retrieveAll(),ID))
                {
                    JOptionPane.showMessageDialog(null,"Deleted");
                }
                else JOptionPane.showMessageDialog(null,"Invalid Input");
            }
        }
        else if(e.getSource()==createItem)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                bookdao.createItem();
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                cddao.createItem();
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                dvddao.createItem();
            }
        }
        else if(e.getSource()==createItem)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                bookdao.createItem();
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                cddao.createItem();
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                dvddao.createItem();
            }
        }
        else if(e.getSource()==itemRestock)
        {
            String type = JOptionPane.showInputDialog(paneFrame,"Which type? ");
            if(type.equalsIgnoreCase(Book.class.getSimpleName()))
            {
                int ID = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
                bookdao.restockItem(bookdao.retrieveByID(ID).get(0));
            }
            else if(type.equalsIgnoreCase(CD.class.getSimpleName()))
            {
                int ID = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
                cddao.restockItem(cddao.retrieveByID(ID).get(0));
            }
            else if(type.equalsIgnoreCase(DVD.class.getSimpleName()))
            {
                int ID = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
                dvddao.restockItem(dvddao.retrieveByID(ID).get(0));
            }
        }
        else if(e.getSource() == rentBook)
        {
            int ID = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
            bookdao.rentItem(bookdao.retrieveByID(ID).get(0));
        }
        else if(e.getSource() == rentCD)
        {
            int ID = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
            cddao.rentItem(cddao.retrieveByID(ID).get(0));
        }
        else if(e.getSource() == rentDVD)
        {
            int ID = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
            dvddao.rentItem(dvddao.retrieveByID(ID).get(0));
        }
    }
    public static void main(String[] args)
    {
        LibraryMenu menu = new LibraryMenu();
    }
}