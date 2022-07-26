package com.example.mlauncher;

import com.example.mlauncher.util.FileDownload;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;

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
        mindustryVersionPool.initialize();
    }

    @FXML
    public void initialize() {
        versionBox.getItems().addAll(mindustryVersionPool.getObjects());
        versionBox.onActionProperty().setValue(event -> {
            actionButton.setText(versionBox.getValue().isDownloaded() ? "Launch" : "Download");
            actionButton.onActionProperty().setValue(event1 -> {
                if (versionBox.getValue().isDownloaded())
                    versionBox.getValue().launch();
                else {
                    FileDownload fileDownload = versionBox.getValue().download();
                    fileDownload.setOnRead(integer -> downloadProgressBar.setProgress((float)integer / versionBox.getValue().getSize()));
                }
            });
        });
    }
}
