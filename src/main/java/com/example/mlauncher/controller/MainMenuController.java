package com.example.mlauncher.controller;

import com.example.mlauncher.MLauncherPropertiesFacade;
import com.example.mlauncher.MindustryVersion;
import com.example.mlauncher.MindustryVersionPool;
import com.example.mlauncher.util.FileDownload;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;

import java.util.Properties;

public class MainMenuController {
    private final MindustryVersionPool mindustryVersionPool;

    @FXML
    private ChoiceBox<MindustryVersion> versionBox;
    @FXML
    private ProgressBar downloadProgressBar;
    @FXML
    private Button actionButton;

    public MainMenuController() {
        mindustryVersionPool = new MindustryVersionPool();
        mindustryVersionPool.initialize(true);
    }

    @FXML
    public void initialize() {
        Properties properties = MLauncherPropertiesFacade.getInstance().getProperties();
        versionBox.setValue(mindustryVersionPool.getObjects().stream()
                .filter(mindustryVersion -> mindustryVersion.toString().equals(properties.getProperty("LastSelectedVersion")))
                .findAny().orElse(mindustryVersionPool.getObjects().get(0)));
        versionBox.getItems().addAll(mindustryVersionPool.getObjects());
        versionBox.onActionProperty().setValue(event -> {
            properties.setProperty("LastSelectedVersion", versionBox.getValue().toString());
            MLauncherPropertiesFacade.getInstance().storeProperties();
            updateActionButton();
            actionButton.onActionProperty().setValue(event1 -> {
                if (versionBox.getValue().isDownloaded())
                    versionBox.getValue().launch();
                else {
                    downloadProgressBar.setOpacity(1);
                    FileDownload fileDownload = versionBox.getValue().download();
                    fileDownload.setOnRead(integer -> downloadProgressBar.setProgress((float)integer / versionBox.getValue().getSize()));
                    fileDownload.setOnEnd(event2 -> {
                        downloadProgressBar.setOpacity(0);
                        Platform.runLater(this::updateActionButton);
                    });
                }
            });
        });
        versionBox.onActionProperty().get().handle(new ActionEvent());
    }

    public void updateActionButton() {
        actionButton.setText(versionBox.getValue().isDownloaded() ? "Launch" : "Download");
    }
}
