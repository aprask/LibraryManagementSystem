package org.libmansys.Internals;

import org.libmansys.Items.CD;
import org.libmansys.Items.Item;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class CDDAO implements ReadCommands<CD>, DeleteCommands<CD>, WriteCommands {
    private final Connection connection;

    public CDDAO() {
        connection = DatabaseConnector.connect();
    }

    @Override
    public ArrayList<CD> retrieveAll() {
        ArrayList<CD> cds = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String queryCommand = "SELECT * FROM cds";
            ResultSet resultSet = statement.executeQuery(queryCommand);
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int id = resultSet.getInt("id");
                double cost = resultSet.getDouble("price");
                String artist = resultSet.getString("artist");
                Date publishedDate = resultSet.getDate("publication_date");
                String genre = resultSet.getString("genre");
                cds.add(new CD(title, id, cost, artist, publishedDate, genre));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cds;
    }

    @Override
    public ArrayList<String> retrieveNames() {
        ArrayList<String> cdTitles = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String queryCommand = "SELECT * FROM cds";
            ResultSet resultSet = statement.executeQuery(queryCommand);
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                cdTitles.add(title);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cdTitles;
    }

    @Override
    public ArrayList<CD> retrieveByMaker(String maker) {
        ArrayList<CD> cdsBySpecifiedArtist = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cds WHERE artist = ?")) {
            preparedStatement.setString(1, maker);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String artist = resultSet.getString("artist");
                if (artist.equalsIgnoreCase(maker)) {
                    String title = resultSet.getString("title");
                    int id = resultSet.getInt("id");
                    double cost = resultSet.getDouble("price");
                    Date publishedDate = resultSet.getDate("publication_date");
                    String genre = resultSet.getString("genre");
                    cdsBySpecifiedArtist.add(new CD(title, id, cost, artist, publishedDate, genre));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cdsBySpecifiedArtist;
    }

    @Override
    public ArrayList<CD> retrieveByID(int ID) {
        ArrayList<CD> cdsBySpecifiedID = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cds WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                if (ID == id) {
                    String title = resultSet.getString("title");
                    double cost = resultSet.getDouble("price");
                    String artist = resultSet.getString("artist");
                    Date publishedDate = resultSet.getDate("publication_date");
                    String genre = resultSet.getString("genre");
                    cdsBySpecifiedID.add(new CD(title, id, cost, artist, publishedDate, genre));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cdsBySpecifiedID;
    }

    @Override
    public ArrayList<CD> retrieveInDescendingOrder(ArrayList<CD> itemList) {
        ArrayList<CD> sortedCDs = new ArrayList<>();
        int i = 0;
        while (i < itemList.size()) {
            CD currentCD = itemList.get(i);
            if (currentCD != null) {
                sortedCDs.add(currentCD);
            }
            i++;
        }
        sortedCDs.sort(Comparator.comparing(CD::getYear, Comparator.reverseOrder()));
        return sortedCDs;
    }

    @Override
    public ArrayList<CD> retrieveInAscendingOrder(ArrayList<CD> itemList) {
        ArrayList<CD> sortedCDs = new ArrayList<>();
        int i = 0;
        while (i < itemList.size()) {
            CD currentCD = itemList.get(i);
            if (currentCD != null) {
                sortedCDs.add(currentCD);
            }
            i++;
        }
        sortedCDs.sort(Comparator.comparing(CD::getYear));
        return sortedCDs;
    }

    @Override
    public ArrayList<CD> retrieveInAlphabeticalOrder(ArrayList<CD> itemList) {
        itemList.sort(Comparator.comparing(CD::getItemName, String.CASE_INSENSITIVE_ORDER));
        return itemList;
    }

    @Override
    public int retrieveItemCount(ArrayList<CD> itemList) {
        return itemList.size();
    }

    @Override
    public Item retrieveRandomItem(ArrayList<CD> itemList) {
        Random random = new Random();
        int randomIndex = random.nextInt(0,retrieveItemCount(this.retrieveAll()));
        return itemList.get(randomIndex);
    }

    @Override
    public Item retrieveLatestItem(ArrayList<CD> itemList) {
        return itemList.get(itemList.size()-1);
    }

    public Connection getConnection() {
        return connection;
    }
    @Override
    public boolean removeValue(ArrayList<CD> itemList, int ID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cds WHERE id = ?")) {
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
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cds SET title = ? WHERE id = ?"))
        {
            String message = "Given " + item.getItemName() + ", what name would you like to substitute?";
            String scannedName = JOptionPane.showInputDialog(message);
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
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cds SET id = ? WHERE id = ?"))
        {
            String message = "Given " + item.getItemID() + ", what ID would you like to substitute?";
            String scannedID = JOptionPane.showInputDialog(message);
            preparedStatement.setInt(1,Integer.parseInt(scannedID));
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
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cds SET price = ? WHERE id = ?"))
        {
            String message = "Given " + item.getItemCost() + ", what price would you like to substitute?";
            String scannedPrice = JOptionPane.showInputDialog(message);
            preparedStatement.setDouble(1,Integer.parseInt(scannedPrice));
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
        if (item instanceof CD) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cds SET genre = ? WHERE id = ?"))
            {
                System.out.println("Given " + ((CD) item).getGenre() + ", what genre would you like to substitute?");
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
        int ID = item.getItemID();
        if (item instanceof CD) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cds SET publication_date = ? WHERE id = ?"))
            {
                String message = "Given " + ((CD) item).getYear() + ", what date would you like to substitute? \n(yyyy-MM-dd): ";
                String scannedDate = JOptionPane.showInputDialog(message);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date newDate = dateFormat.parse(scannedDate);
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
        if (item instanceof CD) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cds SET artist = ? WHERE id = ?"))
            {
                String message = "Given " + ((CD) item).getArtist() + ", what artist's name would you like to substitute?";
                String updatedAuthorName = JOptionPane.showInputDialog(message);
                preparedStatement.setString(1,updatedAuthorName);
                preparedStatement.setInt(2,ID);
                int rowsUpdated = preparedStatement.executeUpdate();
                if(rowsUpdated > 0) System.out.println("Artist's name updated!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void restockItem(Item item) {
        int ID = item.getItemID();
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cds SET isRented = 0 WHERE id = ?")) {
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
                            ("INSERT INTO cds (title, id, price, artist, publication_date, genre, isRented) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?)"))
        {
            String nameOfCD = JOptionPane.showInputDialog("Name? ");
            int idOfCD = Integer.parseInt(JOptionPane.showInputDialog("ID? "));
            double priceOfCD = Double.parseDouble(JOptionPane.showInputDialog("Price? "));
            String authorOfCD = JOptionPane.showInputDialog("Author? ");
            String dateString = JOptionPane.showInputDialog("Enter a publication date (yyyy-MM-dd): ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateOfCD = dateFormat.parse(dateString);
            String genreOfCD = JOptionPane.showInputDialog("Genre of the book? ");

            preparedStatement.setString(1, nameOfCD);
            preparedStatement.setInt(2,idOfCD);
            preparedStatement.setDouble(3, priceOfCD);
            preparedStatement.setString(4, authorOfCD);
            preparedStatement.setDate(5, new java.sql.Date(dateOfCD.getTime()));
            preparedStatement.setString(6, genreOfCD);
            preparedStatement.setInt(7, 0);

            int rowsInserted = preparedStatement.executeUpdate();
            if(rowsInserted > 0 ) System.out.println("CD created successfully!");
        } catch (SQLException | ParseException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isRented(Item item)
    {
        int ID = item.getItemID();
        int currentIsRentedValue = 0;
        try (PreparedStatement selectStatement = connection.prepareStatement("SELECT isRented FROM cds WHERE id = ?")) {
            selectStatement.setInt(1, ID);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                currentIsRentedValue = resultSet.getInt("isRented");
                if(currentIsRentedValue == 0) return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public static void main(String[] args)
    {
        CDDAO cddao = new CDDAO();
        System.out.println(cddao.retrieveAll().get(5).getArtist());
    }

}
