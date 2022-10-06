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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.*;

public class MainMenuController {
    private final MindustryVersionPool mindustryVersionPool;

    @FXML
    private ChoiceBox<MindustryVersion> versionBox;
    @FXML
    private ProgressBar downloadProgressBar;
    @FXML
    private Button actionButton;
    @FXML
    private Pane newsPane;

    public MainMenuController() {
        mindustryVersionPool = new MindustryVersionPool();
        MLauncherPropertiesFacade.getInstance().setOnUpdated(event -> update());
    }

    @FXML
    public void initialize() {
        Properties properties = MLauncherPropertiesFacade.getInstance().getProperties();
        versionBox.setValue(mindustryVersionPool.getObjects().stream()
                .filter(mindustryVersion -> mindustryVersion.toString().equals(properties.getProperty("LastSelectedVersion")))
                .findAny().orElse(mindustryVersionPool.getObjects().get(0)));
        versionBox.getItems().addAll(getMindustryVersions());
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
        mindustryVersionPool.getObjects().forEach(mindustryVersion -> {
            if (mindustryVersion.isBE()) return;
            newsPane.getChildren().add(new Label(mindustryVersion.getName()));
            newsPane.getChildren().add(new Text(mindustryVersion.getBody() + "\n"));
        });
    }

    public void update() {
        MindustryVersion value = versionBox.getValue();
        versionBox.getItems().clear();
        versionBox.getItems().addAll(getMindustryVersions());
        versionBox.setValue(value);
        newsPane.getChildren().clear();
        mindustryVersionPool.getObjects().forEach(mindustryVersion -> {
            if (mindustryVersion.isBE()) return;
            newsPane.getChildren().add(new Label(mindustryVersion.getName()));
            newsPane.getChildren().add(new Text(mindustryVersion.getBody() + "\n"));
        });
    }

    public List<MindustryVersion> getMindustryVersions() {
        List<MindustryVersion> versionList = new ArrayList<>();
        List<MindustryVersion> beBuilds = mindustryVersionPool.getObjects().stream().filter(MindustryVersion::isBE).toList();
        List<MindustryVersion> builds = mindustryVersionPool.getObjects().stream().filter(mindustryVersion -> !mindustryVersion.isBE()).toList();
        versionList.addAll(beBuilds.subList(0, MLauncherPropertiesFacade.getInstance().getBeBuilds()));
        versionList.addAll(builds.subList(0, MLauncherPropertiesFacade.getInstance().getVersions()));
        versionList.sort(Comparator.comparing(MindustryVersion::getCreatedAt));
        Collections.reverse(versionList);
        return versionList;
    }

    public void updateActionButton() {
        actionButton.setText(versionBox.getValue().isDownloaded() ? "Launch" : "Download");
    }
}
