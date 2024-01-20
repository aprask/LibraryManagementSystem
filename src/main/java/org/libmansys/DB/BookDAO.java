package org.libmansys.DB;

import org.libmansys.Items.Book;
import org.libmansys.Items.Item;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class BookDAO implements ReadCommands<Book> {
    private final Connection connection;
    public BookDAO() {
        this.connection = DatabaseConnector.connect();
    }
    public Connection getConnection() {
        return connection;
    }
    public ArrayList<Book> retrieveBooksByISBN(String isbnNum)
    {
        ArrayList<Book> booksBySpecifiedISBN = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE isbn = ?")) {
            preparedStatement.setString(1, isbnNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                String isbn = resultSet.getString("isbn");
                if(isbn.equals(isbnNum))
                {
                    String title = resultSet.getString("title");
                    int id = resultSet.getInt("id");
                    double cost = resultSet.getDouble("price");
                    String author = resultSet.getString("author");
                    Date publishedDate = resultSet.getDate("publication_date");
                    String genre = resultSet.getString("genre");
                    booksBySpecifiedISBN.add(new Book(title,id,cost,author,isbn,publishedDate,genre));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return booksBySpecifiedISBN;
    }
    public static void main(String[] args)
    {
        BookDAO bookDAO = new BookDAO();
    }
    @Override
    public ArrayList<Book> retrieveAll() {
        ArrayList<Book> books = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            String queryCommand = "SELECT * FROM books";
            ResultSet resultSet = statement.executeQuery(queryCommand);
            while(resultSet.next())
            {
                String title = resultSet.getString("title");
                int id = resultSet.getInt("id");
                double cost = resultSet.getDouble("price");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                Date publishedDate = resultSet.getDate("publication_date");
                String genre = resultSet.getString("genre");
                books.add(new Book(title,id,cost,author,isbn,publishedDate,genre));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public ArrayList<String> retrieveNames() {
        ArrayList<String> bookTitles = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            String queryCommand = "SELECT * FROM books";
            ResultSet resultSet = statement.executeQuery(queryCommand);
            while(resultSet.next())
            {
                String title = resultSet.getString("title");
                bookTitles.add(title);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookTitles;
    }

    @Override
    public ArrayList<Book> retrieveByMaker(String maker) {
        ArrayList<Book> booksBySpecifiedAuthor = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE author = ?")) {
            preparedStatement.setString(1, maker);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                String author = resultSet.getString("author");
                if(author.equalsIgnoreCase(maker))
                {
                    String title = resultSet.getString("title");
                    int id = resultSet.getInt("id");
                    double cost = resultSet.getDouble("price");
                    String isbn = resultSet.getString("isbn");
                    Date publishedDate = resultSet.getDate("publication_date");
                    String genre = resultSet.getString("genre");
                    booksBySpecifiedAuthor.add(new Book(title,id,cost,author,isbn,publishedDate,genre));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return booksBySpecifiedAuthor;
    }

    @Override
    public ArrayList<Book> retrieveByID(int ID) {
        ArrayList<Book> booksBySpecifiedID = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE id = ?")) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                if(ID == id)
                {
                    String title = resultSet.getString("title");
                    double cost = resultSet.getDouble("price");
                    String isbn = resultSet.getString("isbn");
                    String author = resultSet.getString("author");
                    Date publishedDate = resultSet.getDate("publication_date");
                    String genre = resultSet.getString("genre");
                    booksBySpecifiedID.add(new Book(title,id,cost,author,isbn,publishedDate,genre));
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booksBySpecifiedID;
    }

    @Override
    public ArrayList<Book> retrieveInDescendingOrder(ArrayList<Book> itemList) {
        ArrayList<Book> sortedBooks = new ArrayList<>();
        int i = 0;
        while (i < itemList.size()) {
            Book currentBook = itemList.get(i);
            if (currentBook != null) {
                sortedBooks.add(currentBook);
            }
            i++;
        }
        sortedBooks.sort(Comparator.comparing(Book::getYear, Comparator.reverseOrder()));
        return sortedBooks;
    }

    @Override
    public ArrayList<Book> retrieveInAscendingOrder(ArrayList<Book> itemList) {
        ArrayList<Book> sortedBooks = new ArrayList<>();
        int i = 0;
        while (i < itemList.size()) {
            Book currentBook = itemList.get(i);
            if (currentBook != null) {
                sortedBooks.add(currentBook);
            }
            i++;
        }
        sortedBooks.sort(Comparator.comparing(Book::getYear));
        return sortedBooks;
    }
    @Override
    public int retrieveItemCount(ArrayList<Book> itemList) {
        return itemList.size();
    }

    @Override
    public Item retrieveRandomItem(ArrayList<Book> itemList) {
        Random random = new Random();
        int randomIndex = random.nextInt(0,retrieveItemCount(this.retrieveAll()));
        return itemList.get(randomIndex);
    }

    @Override
    public Item retrieveLatestItem(ArrayList<Book> itemList) {
        return itemList.get(itemList.size()-1);
    }
}