package com.example.demoreadingnow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import static com.example.demoreadingnow.Controller.*;

public class InfoController implements Initializable {
    @FXML
    ImageView info_pic;
    @FXML
    Label info_name;
    @FXML
    Label info_author;
    @FXML
    Label info_category;
    @FXML
    Label info_rating;
    @FXML
    Label info_yearandpages;
    @FXML
    TextArea info_info;





    private final Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/beibarysbexeiit/DataGripProjects/database_/read.sqlite");;

//    if(rs.next()){
//        info_pic.setImage();
//    }


    public InfoController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM books WHERE title = " + '"' + index_name + '"';
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                Image image = new Image(rs.getString("thumbnail"));
                info_pic.setImage(image);
                info_name.setText(rs.getString("title"));
                info_author.setText(rs.getString("authors"));
                info_rating.setText("Rating: " + rs.getDouble("average_rating") + "\n" + "(" + rs.getString("ratings_count") + ")");
                info_category.setText("Category: " + rs.getString("categories"));
                info_yearandpages.setText("Published: " + rs.getInt("published_year") + "\n" + "Pages: " + rs.getInt("num_pages"));
                info_info.setText("Description: \n" + rs.getString("description"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void backBUTTON(ActionEvent event) throws IOException {
        index_ini = listRN.size();
        index_ini1 = listWR.size();
        root = FXMLLoader.load(getClass().getResource("view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public void readBUTTON(ActionEvent event) throws SQLException, IOException {
//        Book temp = list.get(index);
//        if(!listRN.contains(temp)) {
//            listRN.add(temp);
//        }
        String sql = "INSERT INTO booksReadingNow SELECT * FROM books WHERE title = " + '"' + info_name.getText() + '"';
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        backBUTTON(event);
    }
    public void willreadBUTTON(ActionEvent event) throws SQLException, IOException {
//        Book temp = list.get(index);
//        if(!listWR.contains(temp)) {
//            listWR.add(temp);
//        }
        String sql = "INSERT INTO booksWillRead SELECT * FROM books WHERE title = " + '"' + info_name.getText() + '"';
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        backBUTTON(event);
    }
}
