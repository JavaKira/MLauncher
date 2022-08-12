package com.example.mlauncher.controller;

import com.example.mlauncher.MLauncherApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {
    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        openHomePage();
    }

    public void openSettingsPage() {
        openPage(MLauncherApplication.class.getResource("SettingsPage.fxml"));
    }

    public void openHomePage() {
        openPage(MLauncherApplication.class.getResource("HomePage.fxml"));
    }

    private void openPage(URL url) {
        try {
            Parent parent = new FXMLLoader(url).load();
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
