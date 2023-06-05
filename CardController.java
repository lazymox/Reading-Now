package com.example.demoreadingnow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.demoreadingnow.Controller.*;
public class CardController {
    private Book booktemp;
    private final Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/beibarysbexeiit/DataGripProjects/database_/read.sqlite");
    @FXML
    private HBox card;
    @FXML
    private Label card_author;
    @FXML
    private ImageView card_image;
    @FXML
    private Label card_name;
    @FXML
    private Label card_rating;

    private final String [] colors = {"808080", "818589", "D3D3D3", "899499"};

    public CardController() throws SQLException {
    }

    public void setData(Book book){
        booktemp = book;
        Image image = new Image(book.getUrl());
        card_image.setImage(image);
        card_name.setText(book.getName());
        card_author.setText(book.getAuthor_name());
        card_rating.setText("Rating: " + String.valueOf(book.getRating()));
        card.setStyle("-fx-background-color: #" + colors[(int)(Math.random()*colors.length)] +
                "; -fx-background-radius: 100;" +
                "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0), 10, 0, 0, 10);");
    }

    @FXML
    private TextField newRating;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void doneBUTTON(ActionEvent event) throws SQLException, IOException {
        String doneBOOK = card_name.getText();
        if(newRating.getText() != null){
            String sql = "INSERT INTO booksRead SELECT * FROM booksReadingNow WHERE title = " + '"' + doneBOOK + '"';
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            String sql1 = "DELETE FROM booksReadingNow WHERE title = " + '"' + doneBOOK + '"';
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.executeUpdate();
            listRN.remove(booktemp);
            index_ini = listRN.size();
            index_ini1 = listWR.size();
            String sql2 = "UPDATE booksRead SET average_rating = ? WHERE title = " + '"' + doneBOOK + '"';
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setString(1, String.valueOf(newRating.getText()));
            stmt2.executeUpdate();
            root = FXMLLoader.load(getClass().getResource("view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

}
