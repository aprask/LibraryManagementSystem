package org.libmansys.DB;

import org.libmansys.Items.Book;
import org.libmansys.Items.CD;
import org.libmansys.Items.DVD;
import org.libmansys.Items.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;

public class DVDDAO implements ReadCommands<DVD> {
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
}
