package org.libmansys.DB;

import org.libmansys.Items.DVD;
import org.libmansys.Items.Item;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class DVDDAO implements ReadCommands<DVD>, DeleteCommands<DVD>, WriteCommands {
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
            System.out.println("Given " + item.getItemName() + ", what name would you like to substitute?");
            String scannedName = scanner.next();
            preparedStatement.setString(1,scannedName);
            preparedStatement.setInt(2,ID);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0) System.out.println("Title updated!");
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
            System.out.println("Given " + item.getItemID() + ", what ID would you like to substitute?");
            int scannedID = scanner.nextInt();
            preparedStatement.setInt(1,scannedID);
            preparedStatement.setInt(2,ID);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0) System.out.println("ID updated!");
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
            System.out.println("Given $" + item.getItemCost() + ", what price would you like to substitute?");
            double scannedPrice = scanner.nextDouble();
            preparedStatement.setDouble(1,scannedPrice);
            preparedStatement.setInt(2,ID);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0) System.out.println("Price updated!");
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
                if(rowsUpdated > 0) System.out.println("Genre updated!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void changeItemYear(Item item) {
        var scanner = new Scanner(System.in);
        int ID = item.getItemID();
        if (item instanceof DVD) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dvds SET publication_date = ? WHERE id = ?"))
            {
                System.out.println("Given " + ((DVD) item).getYear() + ", what date would you like to substitute?");
                System.out.println("Enter a date (yyyy-MM-dd):");
                String dateString = scanner.next();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = (Date) dateFormat.parse(dateString);
                preparedStatement.setDate(1,new java.sql.Date(newDate.getTime()));
                preparedStatement.setInt(2,ID);
                int rowsUpdated = preparedStatement.executeUpdate();
                if(rowsUpdated > 0) System.out.println("Date updated!");
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
                System.out.println("Given " + ((DVD) item).getArtist() + ", what director's name would you like to substitute?");
                String updatedDirectorName = scanner.next();
                preparedStatement.setString(1,updatedDirectorName);
                preparedStatement.setInt(2,ID);
                int rowsUpdated = preparedStatement.executeUpdate();
                if(rowsUpdated > 0) System.out.println("Director's name updated!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void restockItem(Item item) {
        int ID = item.getItemID();
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE dvds SET isRented = 0 WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) System.out.println("Item restocked!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            System.out.println("Name of the dvd? ");
            String nameOfDVD = scanner.next();

            System.out.println("ID of the dvd? ");
            int idOfDVD = scanner.nextInt();

            System.out.println("Price of the dvd? ");
            double priceOfDVD = scanner.nextDouble();

            System.out.println("Director of the dvd? ");
            String directorOfDVD = scanner.next();

            System.out.println("Release date? ");
            System.out.println("Enter a date (yyyy-MM-dd): ");
            String dateString = scanner.next();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfDVD = (Date) dateFormat.parse(dateString);

            System.out.println("Genre of the book? ");
            String genreOfDVD = scanner.next();

            preparedStatement.setString(1, nameOfDVD);
            preparedStatement.setInt(2,idOfDVD);
            preparedStatement.setDouble(3, priceOfDVD);
            preparedStatement.setString(4, directorOfDVD);
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