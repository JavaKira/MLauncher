package com.example.mlauncher.controller;

import com.example.mlauncher.PropertiesFacade;
import com.example.mlauncher.Version;
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
import java.util.stream.Collectors;

public class MainMenuController {
    private final MindustryVersionPool mindustryVersionPool;

    @FXML
    private ChoiceBox<Version> versionBox;
    @FXML
    private ProgressBar downloadProgressBar;
    @FXML
    private Button actionButton;
    @FXML
    private Pane newsPane;

    public MainMenuController() {
        mindustryVersionPool = new MindustryVersionPool();
        PropertiesFacade.getInstance().setOnUpdated(event -> update());
    }

    @FXML
    public void initialize() {
        Properties properties = PropertiesFacade.getInstance().getProperties();
        versionBox.setValue(mindustryVersionPool.getObjects().stream()
                .filter(version -> version.toString().equals(properties.getProperty("LastSelectedVersion")))
                .findAny().orElse(mindustryVersionPool.getObjects().get(0)));
        versionBox.getItems().addAll(getMindustryVersions());
        versionBox.onActionProperty().setValue(event -> {
            properties.setProperty("LastSelectedVersion", versionBox.getValue().toString());
            PropertiesFacade.getInstance().storeProperties();
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
        mindustryVersionPool.getObjects().forEach(version -> {
            if (version.isBE()) return;
            newsPane.getChildren().add(new Label(version.getName()));
            newsPane.getChildren().add(new Text(version.getBody() + "\n"));
        });
    }

    public void update() {
        Version value = versionBox.getValue();
        versionBox.getItems().clear();
        versionBox.getItems().addAll(getMindustryVersions());
        versionBox.setValue(value);
        newsPane.getChildren().clear();
        mindustryVersionPool.getObjects().forEach(version -> {
            if (version.isBE()) return;
            newsPane.getChildren().add(new Label(version.getName()));
            newsPane.getChildren().add(new Text(version.getBody() + "\n"));
        });
    }

    public List<Version> getMindustryVersions() {
        List<Version> versionList = new ArrayList<>();
        List<Version> beBuilds = mindustryVersionPool.getObjects().stream().filter(Version::isBE).collect(Collectors.toList());
        List<Version> builds = mindustryVersionPool.getObjects().stream().filter(version -> !version.isBE()).collect(Collectors.toList());
        versionList.addAll(beBuilds.subList(0, PropertiesFacade.getInstance().getBeBuilds()));
        versionList.addAll(builds.subList(0, PropertiesFacade.getInstance().getVersions()));
        versionList.sort(Comparator.comparing(Version::getCreatedAt));
        Collections.reverse(versionList);
        return versionList;
    }

    public void updateActionButton() {
        actionButton.setText(versionBox.getValue().isDownloaded() ? "Launch" : "Download");
    }
}
