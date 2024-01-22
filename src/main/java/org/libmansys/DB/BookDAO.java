package org.libmansys.DB;

import org.libmansys.Items.Book;
import org.libmansys.Items.Item;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class BookDAO implements ReadCommands<Book>, DeleteCommands<Book>, WriteCommands {
    private final Connection connection;

    public BookDAO() {
        this.connection = DatabaseConnector.connect();
    }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        bookDAO.createItem();
    }

    @Override
    public ArrayList<Book> retrieveAll() {
        ArrayList<Book> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String queryCommand = "SELECT * FROM books";
            ResultSet resultSet = statement.executeQuery(queryCommand);
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                int id = resultSet.getInt("id");
                double cost = resultSet.getDouble("price");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                Date publishedDate = resultSet.getDate("publication_date");
                String genre = resultSet.getString("genre");
                books.add(new Book(title, id, cost, author, isbn, publishedDate, genre));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public ArrayList<String> retrieveNames() {
        ArrayList<String> bookTitles = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String queryCommand = "SELECT * FROM books";
            ResultSet resultSet = statement.executeQuery(queryCommand);
            while (resultSet.next()) {
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
            while (resultSet.next()) {
                String author = resultSet.getString("author");
                if (author.equalsIgnoreCase(maker)) {
                    String title = resultSet.getString("title");
                    int id = resultSet.getInt("id");
                    double cost = resultSet.getDouble("price");
                    String isbn = resultSet.getString("isbn");
                    Date publishedDate = resultSet.getDate("publication_date");
                    String genre = resultSet.getString("genre");
                    booksBySpecifiedAuthor.add(new Book(title, id, cost, author, isbn, publishedDate, genre));
                }
            }
        } catch (SQLException e) {
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
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                if (ID == id) {
                    String title = resultSet.getString("title");
                    double cost = resultSet.getDouble("price");
                    String isbn = resultSet.getString("isbn");
                    String author = resultSet.getString("author");
                    Date publishedDate = resultSet.getDate("publication_date");
                    String genre = resultSet.getString("genre");
                    booksBySpecifiedID.add(new Book(title, id, cost, author, isbn, publishedDate, genre));
                }
            }
        } catch (SQLException e) {
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
    public ArrayList<Book> retrieveInAlphabeticalOrder(ArrayList<Book> itemList) {
        itemList.sort(Comparator.comparing(Book::getItemName, String.CASE_INSENSITIVE_ORDER));
        return itemList;
    }

    @Override
    public int retrieveItemCount(ArrayList<Book> itemList) {
        return itemList.size();
    }

    @Override
    public Item retrieveRandomItem(ArrayList<Book> itemList) {
        Random random = new Random();
        int randomIndex = random.nextInt(0, retrieveItemCount(this.retrieveAll()));
        return itemList.get(randomIndex);
    }

    @Override
    public Item retrieveLatestItem(ArrayList<Book> itemList) {
        return itemList.get(itemList.size() - 1);
    }

    public ArrayList<Book> retrieveBooksByISBN(String isbnNum) {
        ArrayList<Book> booksBySpecifiedISBN = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE isbn = ?")) {
            preparedStatement.setString(1, isbnNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                if (isbn.equals(isbnNum)) {
                    String title = resultSet.getString("title");
                    int id = resultSet.getInt("id");
                    double cost = resultSet.getDouble("price");
                    String author = resultSet.getString("author");
                    Date publishedDate = resultSet.getDate("publication_date");
                    String genre = resultSet.getString("genre");
                    booksBySpecifiedISBN.add(new Book(title, id, cost, author, isbn, publishedDate, genre));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return booksBySpecifiedISBN;
    }
    @Override
    public boolean removeValue(ArrayList<Book> itemList, int ID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM books WHERE id = ?")) {
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
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET title = ? WHERE id = ?"))
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
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET id = ? WHERE id = ?"))
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
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET price = ? WHERE id = ?"))
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
        if (item instanceof Book) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET genre = ? WHERE id = ?"))
            {
                System.out.println("Given " + ((Book) item).getGenre() + ", what genre would you like to substitute?");
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
        if (item instanceof Book) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET publication_date = ? WHERE id = ?"))
            {
                System.out.println("Given " + ((Book) item).getYear() + ", what date would you like to substitute?");
                System.out.println("Enter a date (yyyy-MM-dd):");
                String dateString = scanner.next();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = dateFormat.parse(dateString);
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
        if (item instanceof Book) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET author = ? WHERE id = ?"))
            {
                System.out.println("Given " + ((Book) item).getAuthor() + ", what author's name would you like to substitute?");
                String updatedAuthorName = scanner.next();
                preparedStatement.setString(1,updatedAuthorName);
                preparedStatement.setInt(2,ID);
                int rowsUpdated = preparedStatement.executeUpdate();
                if(rowsUpdated > 0) System.out.println("Author's name updated!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void restockItem(Item item) {
        int ID = item.getItemID();
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET isRented = 0 WHERE id = ?")) {
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
                                ("INSERT INTO books (title, id, price, author, isbn, publication_date, genre, isRented) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"))
            {
                System.out.println("Name of the book? ");
                String nameOfBook = scanner.next();

                System.out.println("ID of the book? ");
                int idOfBook = scanner.nextInt();

                System.out.println("Price of the book? ");
                double priceOfBook = scanner.nextDouble();

                System.out.println("Author of the book? ");
                String authorOfBook = scanner.next();

                System.out.println("ISBN? ");
                String isbnOfBook = scanner.next();

                System.out.println("Publication date? ");
                System.out.println("Enter a date (yyyy-MM-dd): ");
                String dateString = scanner.next();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBook = dateFormat.parse(dateString);

                System.out.println("Genre of the book? ");
                String genreOfBook = scanner.next();

                preparedStatement.setString(1, nameOfBook);
                preparedStatement.setInt(2,idOfBook);
                preparedStatement.setDouble(3, priceOfBook);
                preparedStatement.setString(4, authorOfBook);
                preparedStatement.setString(5, isbnOfBook);
                preparedStatement.setDate(6, new java.sql.Date(dateOfBook.getTime()));
                preparedStatement.setString(7, genreOfBook);
                preparedStatement.setInt(8, 0);

                int rowsInserted = preparedStatement.executeUpdate();
                if(rowsInserted > 0 ) System.out.println("Book created successfully!");
            } catch (SQLException | ParseException e){
                throw new RuntimeException(e);
        }
    }
    public void changeISBNNumber(Book book){
        var scanner = new Scanner(System.in);
        int ID = book.getItemID();
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET isbn = ? WHERE id = ?")) {
            System.out.println("Given " + book.getIsbn() + ", what ISBN would you like to substitute?");
            String updatedISBNNumber = scanner.next();
            preparedStatement.setString(1,updatedISBNNumber);
            preparedStatement.setInt(2,ID);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0) System.out.println("ISBN number updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}