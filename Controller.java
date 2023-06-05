package com.example.demoreadingnow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Controller() throws SQLException {
    }

    public static int index_ini = 0;
    public static int index_ini1 = 0;
    public static ObservableList<Book> listRN = FXCollections.observableArrayList();
    public static ObservableList<Book> listWR = FXCollections.observableArrayList();
    public static ObservableList<Book> listDONE = FXCollections.observableArrayList();
    public static ObservableList<Book> list = FXCollections.observableArrayList();
    public static int index = -1;
    public static String index_name = "";
    private final Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/beibarysbexeiit/DataGripProjects/database_/read.sqlite");
    //vbox in READING NOW
    @FXML
    private VBox bookLayout;

    //table into tableview CATALOG
    @FXML
    private TableView tableC;
    @FXML
    private TableColumn<Book, String> catalog_name;
    @FXML
    private TableColumn<Book, String> catalog_author;
    @FXML
    private TableColumn<Book, String> catalog_categories;

    @FXML
    private TableView tableR;
    @FXML
    private TableColumn<Book, String> read_name;
    @FXML
    private TableColumn<Book, String> read_author;
    @FXML
    private TableColumn<Book, Double> read_like;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //NOTES
        Statement stmtN = null;
        try{
            stmtN = conn.createStatement();

            String sql = "SELECT * FROM note LIMIT 1";
            ResultSet rs = stmtN.executeQuery(sql);
            if(rs.next()){
                notesRN.setText(rs.getString("written"));
            }
            rs.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        //catalog
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            String sql = "SELECT * FROM books";
            ResultSet rs = stmt.executeQuery(sql);
            list.clear();
            tableC.refresh();

            while (rs.next()) {
                Book tempp = new Book(rs.getString("title"), rs.getString("authors"), rs.getString("categories"), rs.getString("description"), rs.getString("thumbnail"), rs.getInt("published_year"), rs.getInt("ratings_count"), rs.getInt("num_pages"), rs.getDouble("average_rating"));
                if(!list.contains(tempp)) {
                    list.add(tempp);
                }
            }
            rs.close();

            catalog_name.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
            catalog_author.setCellValueFactory(new PropertyValueFactory<Book, String>("author_name"));
            catalog_categories.setCellValueFactory(new PropertyValueFactory<Book, String>("category"));

            tableC.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Read
        Statement stmt0 = null;
        try {
            stmt0 = conn.createStatement();

            String sql = "SELECT * FROM booksRead";
            ResultSet rs = stmt0.executeQuery(sql);

            listDONE.clear();
            tableR.refresh();
            while (rs.next()) {
                Book tempp = new Book(rs.getString("title"), rs.getString("authors"), rs.getString("categories"), rs.getString("description"), rs.getString("thumbnail"), rs.getInt("published_year"), rs.getInt("ratings_count"), rs.getInt("num_pages"), rs.getDouble("average_rating"));
                if(!listDONE.contains(tempp)) {
                    listDONE.add(tempp);
                }
            }
            rs.close();

            read_name.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
            read_author.setCellValueFactory(new PropertyValueFactory<Book, String>("author_name"));
            read_like.setCellValueFactory(new PropertyValueFactory<Book, Double>("rating"));
            tableR.setItems(listDONE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //reading now
        Statement stmt1 = null;
        Statement stmt9 = null;

        try {
            stmt1 = conn.createStatement();

            String sql = "SELECT * FROM booksReadingNow";
            ResultSet rs = stmt1.executeQuery(sql);

            while (rs.next()) {
                Book tempp = new Book(rs.getString("title"), rs.getString("authors"), rs.getString("categories"), rs.getString("description"), rs.getString("thumbnail"), rs.getInt("published_year"), rs.getInt("ratings_count"), rs.getInt("num_pages"), rs.getDouble("average_rating"));
                if(!listRN.contains(tempp)) {
                    listRN.add(tempp);
                }
            }
            rs.close();

            //will read
            stmt9 = conn.createStatement();

            String sql9 = "SELECT * FROM booksWillRead";
            ResultSet rs9 = stmt9.executeQuery(sql9);

            while (rs9.next()) {
                Book temppp = new Book(rs9.getString("title"), rs9.getString("authors"), rs9.getString("categories"), rs9.getString("description"), rs9.getString("thumbnail"), rs9.getInt("published_year"), rs9.getInt("ratings_count"), rs9.getInt("num_pages"), rs9.getDouble("average_rating"));
                if(!listWR.contains(temppp)) {
                    listWR.add(temppp);
                }
            }
            rs9.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = index_ini; i < listRN.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("card.fxml"));

                HBox cardBOX = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(listRN.get(i));
                bookLayout.getChildren().add(cardBOX);
            }

            //will read
            for(int i = index_ini1; i < listWR.size(); i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("container.fxml"));

                VBox containerBOX = fxmlLoader.load();
                ContainerController containerController = fxmlLoader.getController();
                containerController.setData(listWR.get(i));
                if(column == 4){
                    column = 0;
                    ++row;
                }
                bookContainer.add(containerBOX, column++, row);
                GridPane.setMargin(containerBOX, new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //search
        search_user();

    }

    //view->info
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void chooseButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("info.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = tableC.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        index_name = catalog_name.getCellData(index).toString();
    }

    //will read
    @FXML
    private GridPane bookContainer;

    //SEARCH
    @FXML
    private TextField search;
    @FXML
    void search_user() {
    catalog_name.setCellValueFactory (new PropertyValueFactory<Book, String> ("name") );
    catalog_author.setCellValueFactory (new PropertyValueFactory<Book, String> ("author_name") );
    catalog_categories.setCellValueFactory (new PropertyValueFactory<Book, String> ("category" ));
    tableC.setItems(list);
    FilteredList<Book> filteredData = new FilteredList<>(list, b -> true);
    search.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(person -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else if (person.getAuthor_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else if (String.valueOf(person.getCategory()).indexOf(lowerCaseFilter) != -1) {
                return true;
            } else return false;
        });
    });
    SortedList<Book> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(tableC.comparatorProperty());
    tableC.setItems(sortedData);
    }


    //NOTES
    @FXML
    private TextArea notesRN;

    public void savenoteBUTTON(ActionEvent event) throws SQLException {
        String tableName = "note";
        String columnName = "written";
        String newValue = notesRN.getText();
        Statement statement = conn.createStatement();
        String updateQuery = String.format("UPDATE %s SET %s = '%s' WHERE rowid = 1",
                tableName, columnName, newValue);

        statement.executeUpdate(updateQuery);

    }

}