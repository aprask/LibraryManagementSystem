package org.libmansys.DB;

import org.libmansys.Items.CD;
import org.libmansys.Items.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class CDDAO implements ReadCommands<CD> {
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
    public ArrayList<CD> retrieveOrderedListByYear() {
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

    public static void main(String[] args)
    {
        CDDAO cddao = new CDDAO();
        System.out.println(cddao.retrieveAll().get(5).getArtist());
    }

}
