package org.libmansys.DB;

import org.libmansys.Items.DVD;
import org.libmansys.Items.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

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
    public ArrayList<DVD> retrieveOrderedListByYear() {
        return null;
    }

    @Override
    public int retrieveItemCount() {
        return 0;
    }

    @Override
    public Item retrieveRandomItem() {
        return null;
    }

    @Override
    public Item retrieveLatestItem() {
        return null;
    }

    public Connection getConnection() {
        return connection;
    }
}
