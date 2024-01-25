package org.libmansys.Internals;

import org.libmansys.Items.DVD;
import org.libmansys.Items.Item;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class DVDDAO implements ReadCommands<DVD>, DeleteCommands<DVD>, WriteCommands, Rent {
    private final Connection connection;

    public DVDDAO() {
        connection = DatabaseConnector.connect();
    }
    @Override
    public ArrayList<DVD> retrieveAll() {
        ArrayList<DVD> dvds = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            String queryCommand = "SELECT * FROM dvds";
            ResultSet resultSet = statement.executeQuery(queryCommand);
            while(resultSet.next())
            {
                String title = resultSet.getString("title");
                int id = resultSet.getInt("id");
                double cost = resultSet.getDouble("price");
                String artist = resultSet.getString("artist");
                Date publishedDate = resultSet.getDate("publication_date");
                String genre = resultSet.getString("genre");
                dvds.add(new DVD(title,id,cost,artist,publishedDate,genre));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dvds;
    }

    @Override
    public ArrayList<String> retrieveNames() {
        ArrayList<String> dvdTitles = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String queryCommand = "SELECT * FROM dvds";
            ResultSet resultSet = statement.executeQuery(queryCommand);
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                dvdTitles.add(title);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dvdTitles;
    }

    @Override
    public ArrayList<DVD> retrieveByMaker(String maker) {
        ArrayList<DVD> dvdsBySpecifiedDirector = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM dvds WHERE director = ?")) {
            preparedStatement.setString(1,maker);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                String director = resultSet.getString("director");
                if(director.equalsIgnoreCase(maker))
                {
                    String title = resultSet.getString("title");
                    int id = resultSet.getInt("id");
                    double cost = resultSet.getDouble("price");
                    Date publishedDate = resultSet.getDate("publication_date");
                    String genre = resultSet.getString("genre");
                    dvdsBySpecifiedDirector.add(new DVD(title,id,cost,director,publishedDate,genre));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dvdsBySpecifiedDirector;
    }

    @Override
    public ArrayList<DVD> retrieveByID(int ID) {
        ArrayList<DVD> dvdsBySpecifiedID = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM dvds WHERE id = ?")){
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                if (ID == id) {
                    String title = resultSet.getString("title");
                    double cost = resultSet.getDouble("price");
                    String director = resultSet.getString("artist");
                    Date publishedDate = resultSet.getDate("publication_date");
                    String genre = resultSet.getString("genre");
                    dvdsBySpecifiedID.add(new DVD(title,id,cost,director,publishedDate,genre));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dvdsBySpecifiedID;
    }

    @Override
    public ArrayList<DVD> retrieveInDescendingOrder(ArrayList<DVD> itemList) {
        ArrayList<DVD> sortedDVDs = new ArrayList<>();
        int i = 0;
        while (i < itemList.size()) {
            DVD currentDVD = itemList.get(i);
            if (currentDVD != null) {
                sortedDVDs.add(currentDVD);
            }
            i++;
        }
        sortedDVDs.sort(Comparator.comparing(DVD::getYear, Comparator.reverseOrder()));
        return sortedDVDs;
    }

    @Override
    public ArrayList<DVD> retrieveInAscendingOrder(ArrayList<DVD> itemList) {
        ArrayList<DVD> sortedDVDs = new ArrayList<>();
        int i = 0;
        while (i < itemList.size()) {
            DVD currentDVD = itemList.get(i);
            if (currentDVD != null) {
                sortedDVDs.add(currentDVD);
            }
            i++;
        }
        sortedDVDs.sort(Comparator.comparing(DVD::getYear));
        return sortedDVDs;
    }

    @Override
    public ArrayList<DVD> retrieveInAlphabeticalOrder(ArrayList<DVD> itemList) {
        itemList.sort(Comparator.comparing(DVD::getItemName, String.CASE_INSENSITIVE_ORDER));
        return itemList;
    }

    @Override
    public int retrieveItemCount(ArrayList<DVD> itemList) {
        return itemList.size();
    }

    @Override
    public Item retrieveRandomItem(ArrayList<DVD> itemList) {
        Random random = new Random();
        int randomIndex = random.nextInt(0,retrieveItemCount(this.retrieveAll()));
        return itemList.get(randomIndex);
    }
    @Override
    public Item retrieveLatestItem(ArrayList<DVD> itemList) {
        return itemList.get(itemList.size()-1);
    }
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean removeValue(ArrayList<DVD> itemList, int ID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM dvds WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public void changeItemName(Item item) {
        var scanner = new Scanner(System.in);
        int ID = item.getItemID();
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dvds SET title = ? WHERE id = ?"))
        {
            String message = "Given " + item.getItemName() + ", what name would you like to substitute?";
            String scannedName = JOptionPane.showInputDialog(message);
            preparedStatement.setString(1,scannedName);
            preparedStatement.setInt(2,ID);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0) JOptionPane.showMessageDialog(null,"Title updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void changeItemID(Item item) {
        var scanner = new Scanner(System.in);
        int ID = item.getItemID();
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dvds SET id = ? WHERE id = ?"))
        {
            String message = "Given " + item.getItemID() + ", what ID would you like to substitute?";
            String scannedID = JOptionPane.showInputDialog(message);
            preparedStatement.setInt(1,Integer.parseInt(scannedID));
            preparedStatement.setInt(2,ID);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0) JOptionPane.showMessageDialog(null,"ID updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeItemPrice(Item item) {
        var scanner = new Scanner(System.in);
        int ID = item.getItemID();
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dvds SET price = ? WHERE id = ?"))
        {
            String message = "Given " + item.getItemCost() + ", what price would you like to substitute?";
            String scannedPrice = JOptionPane.showInputDialog(message);
            preparedStatement.setDouble(1,Integer.parseInt(scannedPrice));
            preparedStatement.setInt(2,ID);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0) JOptionPane.showMessageDialog(null,"Price updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeItemGenre(Item item) {
        var scanner = new Scanner(System.in);
        int ID = item.getItemID();
        if (item instanceof DVD) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dvds SET genre = ? WHERE id = ?"))
            {
                System.out.println("Given " + ((DVD) item).getGenre() + ", what genre would you like to substitute?");
                String scannedGenre = scanner.next();
                preparedStatement.setString(1,scannedGenre);
                preparedStatement.setInt(2,ID);
                int rowsUpdated = preparedStatement.executeUpdate();
                if(rowsUpdated > 0) JOptionPane.showMessageDialog(null,"Genre updated!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void changeItemYear(Item item) {
        int ID = item.getItemID();
        if (item instanceof DVD) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dvds SET publication_date = ? WHERE id = ?"))
            {
                String message = "Given " + ((DVD) item).getYear() + ", what date would you like to substitute? \n(yyyy-MM-dd): ";
                String scannedDate = JOptionPane.showInputDialog(message);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date newDate = dateFormat.parse(scannedDate);
                preparedStatement.setDate(1,new java.sql.Date(newDate.getTime()));
                preparedStatement.setInt(2,ID);
                int rowsUpdated = preparedStatement.executeUpdate();
                if(rowsUpdated > 0) JOptionPane.showMessageDialog(null,"Date updated!");
            } catch (SQLException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void changeItemOwner(Item item) {
        var scanner = new Scanner(System.in);
        int ID = item.getItemID();
        if (item instanceof DVD) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dvds SET author = ? WHERE id = ?"))
            {
                String message = "Given " + ((DVD) item).getArtist() + ", what director's name would you like to substitute?";
                String updatedAuthorName = JOptionPane.showInputDialog(message);
                preparedStatement.setString(1,updatedAuthorName);
                preparedStatement.setInt(2,ID);
                int rowsUpdated = preparedStatement.executeUpdate();
                if(rowsUpdated > 0) JOptionPane.showMessageDialog(null,"Director's name updated!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public boolean isRented(Item item)
    {
        int ID = item.getItemID();
        try (PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM dvds WHERE id = ? AND isRented = 1")) {
            selectStatement.setInt(1, ID);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                int currentIsRentedValue = resultSet.getInt("isRented");
                if(currentIsRentedValue == 1) return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    @Override
    public void restockItem(Item item) {
        if(isRented(item))
        {
            int ID = item.getItemID();
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dvds SET isRented = 0 WHERE id = ?")) {
                preparedStatement.setInt(1, ID);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) JOptionPane.showMessageDialog(null,("Item restocked!"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            System.out.println("Failed to restock!");
        }

    }
    @Override
    public void rentItem(Item item) {
        int ID = item.getItemID();
        if(!isRented(item))
        {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dvds set isRented = 1 WHERE id = ?")) {
                preparedStatement.setInt(1,ID);
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0)
                {
                    try(PreparedStatement innerPreparedStatement = connection.prepareStatement("INSERT INTO rent " +
                            "(Type, ItemID, ItemName, Renter, RentDate, ReturnDate) VALUES (?, ?, ?, ?, ?, ?)"))
                    {
                        String typeOfItem = item.getClass().getSimpleName();
                        int idOfItem = item.getItemID();
                        String nameOfItem = item.getItemName();
                        String nameOfRenter = JOptionPane.showInputDialog("Enter your name");
                        LocalDate currentDate = LocalDate.now();
                        LocalDate returnDate = currentDate.plusWeeks(2);

                        java.sql.Date sqlCurrentDate = java.sql.Date.valueOf(currentDate);
                        java.sql.Date sqlReturnDate = java.sql.Date.valueOf(returnDate);

                        innerPreparedStatement.setString(1,typeOfItem);
                        innerPreparedStatement.setInt(2,idOfItem);
                        innerPreparedStatement.setString(3,nameOfItem);
                        innerPreparedStatement.setString(4,nameOfRenter);
                        innerPreparedStatement.setDate(5, sqlCurrentDate);
                        innerPreparedStatement.setDate(6, sqlReturnDate);

                        int rowsInserted = innerPreparedStatement.executeUpdate();
                        if(rowsInserted > 0 ) JOptionPane.showMessageDialog(null,"You have rented this cd successfully!");
                    }

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void createItem() {
        var scanner = new Scanner(System.in);
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement
                            ("INSERT INTO books (title, id, price, director, release_date, genre, isRented) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?)"))
        {
            String nameOfDVD = JOptionPane.showInputDialog("Name? ");
            int idOfDVD = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
            double priceOfDVD = Double.parseDouble(JOptionPane.showInputDialog("Price? "));
            String authorOfDVD = JOptionPane.showInputDialog("Author? ");
            String dateString = JOptionPane.showInputDialog("Enter a publication date (yyyy-MM-dd): ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateOfDVD = dateFormat.parse(dateString);
            String genreOfDVD = JOptionPane.showInputDialog("Genre of the book? ");

            preparedStatement.setString(1, nameOfDVD);
            preparedStatement.setInt(2,idOfDVD);
            preparedStatement.setDouble(3, priceOfDVD);
            preparedStatement.setString(4, authorOfDVD);
            preparedStatement.setDate(5, new java.sql.Date(dateOfDVD.getTime()));
            preparedStatement.setString(6, genreOfDVD);
            preparedStatement.setInt(7, 0);

            int rowsInserted = preparedStatement.executeUpdate();
            if(rowsInserted > 0 ) System.out.println("Book created successfully!");
        } catch (SQLException | ParseException e){
            throw new RuntimeException(e);
        }
    }
}