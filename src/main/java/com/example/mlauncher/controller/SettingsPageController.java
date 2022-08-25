package com.example.mlauncher.controller;

import com.example.mlauncher.MLauncherPropertiesFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsPageController implements Initializable {
    @FXML
    private CheckBox beBuildsCheckBox;
    @FXML
    private CheckBox oldBeBuildCheckBox;
    @FXML
    private CheckBox oldVersionsCheckBox;
    @FXML
    private TextField pathTextField;
    @FXML
    private Button pathOpenButton;
    @FXML
    private Button resetButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        beBuildsCheckBox.setSelected(MLauncherPropertiesFacade.getInstance().getBeBuilds());
        beBuildsCheckBox.setOnAction(actionEvent -> {
            MLauncherPropertiesFacade.getInstance().setBeBuilds(beBuildsCheckBox.isSelected());
            MLauncherPropertiesFacade.getInstance().storeProperties();
        });
        oldBeBuildCheckBox.setSelected(MLauncherPropertiesFacade.getInstance().getOldBeBuilds());
        oldBeBuildCheckBox.setOnAction(actionEvent -> {
            MLauncherPropertiesFacade.getInstance().setOldBeBuilds(oldBeBuildCheckBox.isSelected());
            MLauncherPropertiesFacade.getInstance().storeProperties();
        });
        oldVersionsCheckBox.setSelected(MLauncherPropertiesFacade.getInstance().getOldVersions());
        oldVersionsCheckBox.setOnAction(actionEvent -> {
            MLauncherPropertiesFacade.getInstance().setOldVersions(oldVersionsCheckBox.isSelected());
            MLauncherPropertiesFacade.getInstance().storeProperties();
        });
        pathTextField.setText(MLauncherPropertiesFacade.getInstance().getPath());
        pathTextField.setOnAction(actionEvent -> {
            MLauncherPropertiesFacade.getInstance().setPath(pathTextField.getText());
            MLauncherPropertiesFacade.getInstance().storeProperties();
        });
        pathOpenButton.setOnAction(actionEvent -> {
            try {
                Runtime.getRuntime().exec(new String[]{"explorer", '"' + pathTextField.getText() + '"'});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        resetButton.setOnAction(actionEvent -> {
            MLauncherPropertiesFacade.getInstance().setDefaults();
            MLauncherPropertiesFacade.getInstance().storeProperties();
            initialize(location, resources);
        });
    }
}
