package com.example.demoreadingnow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.demoreadingnow.Controller.*;

public class ContainerController {
    private final Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/beibarysbexeiit/DataGripProjects/database_/read.sqlite");
    private Book booktemp;
    @FXML
    private VBox contain;
    @FXML
    private Label container_author;

    @FXML
    private Label container_name;

    @FXML
    private ImageView container_pic;

    private final String [] colors = {"808080", "818589", "D3D3D3", "899499"};

    public ContainerController() throws SQLException {
    }

    public void setData(Book book){
        booktemp = book;
        Image image = new Image(book.getUrl());
        container_pic.setImage(image);
        container_name.setText(book.getName());
        container_author.setText(book.getAuthor_name());
        contain.setStyle("-fx-background-color: #" + colors[(int)(Math.random()*colors.length)] +
                "; -fx-background-radius: 100;" +
                "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0), 10, 0, 0, 10);");
    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void read_button(ActionEvent event) throws SQLException, IOException {
        String doneBOOK = container_name.getText();
        String sql = "INSERT INTO booksReadingNow SELECT * FROM booksWillRead WHERE title = " + '"' + doneBOOK + '"';
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        String sql1 = "DELETE FROM booksWillRead WHERE title = " + '"' + doneBOOK + '"';
        PreparedStatement stmt1 = conn.prepareStatement(sql1);
        stmt1.executeUpdate();
        listWR.remove(booktemp);
        index_ini1 = listWR.size();
        index_ini = listRN.size();
        root = FXMLLoader.load(getClass().getResource("view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
