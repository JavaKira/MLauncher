package com.example.mlauncher.controller;

import com.example.mlauncher.Application;
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

    private Parent settingsPage;
    private Parent homePage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        openHomePage();
    }

    public void openSettingsPage() {
        if (settingsPage == null)
            settingsPage = loadPage(Application.class.getResource("SettingsPage.fxml"));
        openPage(settingsPage);
    }

    public void openHomePage() {
        if (homePage == null)
            homePage = loadPage(Application.class.getResource("HomePage.fxml"));
        openPage(homePage);
    }

    private Parent loadPage(URL url) {
        try {
            return new FXMLLoader(url).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openPage(Parent parent) {
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(parent);
    }
}
