package org.libmansys.GUI;

import org.libmansys.DB.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class LibraryGUI extends JFrame implements ActionListener {
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
    JMenuItem restockItem;
    JMenuItem getAllItems;
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
    public LibraryGUI()
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
        JMenu helpMenu = new JMenu("Help");
        JMenu exitMenu = new JMenu("Exit");

        rentBook = new JMenuItem("Rent Book");
        rentCD = new JMenuItem("Rent CD");
        rentDVD = new JMenuItem("Rent DVD");

        exitProgram = new JMenuItem("Exit");

        alterName = new JMenuItem("Change Item Name");
        alterID = new JMenuItem("Change Item ID");
        alterPrice = new JMenuItem("Change Item Price");
        alterGenre = new JMenuItem("Change Item Genre");
        alterDate = new JMenuItem("Change Item Date");
        alterMaker = new JMenuItem("Change Item Maker");
        restockItem = new JMenuItem("Restock");

        getAllItems = new JMenuItem("Retrieve All Items");
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
        readMenu.add(getAllItems);
        getAllItems.addActionListener(this);
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
        writeMenu.add(restockItem);
        restockItem.addActionListener(this);

        menu.add(deleteMenu);
        deleteMenu.add(deleteItem);
        deleteItem.addActionListener(this);

        menu.add(rentMenu);
        rentMenu.add(rentBook);
        rentBook.addActionListener(this);
        rentMenu.add(rentCD);
        rentMenu.addActionListener(this);
        rentMenu.add(rentDVD);
        rentDVD.addActionListener(this);

        JLabel label = new JLabel();
        label.setBackground(Color.white);
        label.setIcon(null);
        label.setText("Library Management System");
        label.setFont(new Font("MV Boli", Font.BOLD, 50));
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
        if(e.getSource()==rentBook)
        {
            System.out.println("Rent Book");
        }
        else if(e.getSource()==rentCD)
        {
            System.out.println("Rent CD");
        }
        else if(e.getSource()==rentDVD)
        {
            System.out.println("Rent DVD");
        }
        else if(e.getSource()==exitProgram)
        {
            System.out.println("Exit Program");
        }
        else if(e.getSource()==alterName)
        {
            System.out.println("Change Name");
        }
        else if(e.getSource()==alterID)
        {
            System.out.println("Change ID");
        }
        else if(e.getSource()==alterPrice)
        {
            System.out.println("Change Price");
        }
        else if(e.getSource()==alterGenre)
        {
            System.out.println("Change Genre");
        }
        else if(e.getSource()==alterDate)
        {
            System.out.println("Change Date");
        }
        else if(e.getSource()==alterMaker)
        {
            System.out.println("Change Maker");
        }
        else if(e.getSource()==restockItem)
        {
            System.out.println("Restock Item");
        }
        else if(e.getSource()==getAllItems)
        {
            System.out.println("Get All Items");
        }
        else if(e.getSource()==getNames)
        {
            System.out.println("Get All Names");
        }
        else if(e.getSource()==getItemByMaker)
        {
            System.out.println("Get Item By Maker");
        }
        else if(e.getSource()==getItemByID)
        {
            System.out.println("Get Item By Id");
        }
        else if(e.getSource()==getDescendingList)
        {
            System.out.println("Get Descending List");
        }
        else if(e.getSource()==getAscendingList)
        {
            System.out.println("Get Ascending List");
        }
        else if(e.getSource()==getItemCount)
        {
            System.out.println("Get Item Count");
        }
        else if(e.getSource()==getRandomItem)
        {
            System.out.println("Get Random Item");
        }
        else if(e.getSource()==getLatestItem)
        {
            System.out.println("Get Latest Item");
        }
        else if(e.getSource()==deleteItem)
        {
            System.out.println("Delete Item");
        }

    }
    public static void main(String[] args)
    {
        LibraryGUI menu = new LibraryGUI();
    }
}