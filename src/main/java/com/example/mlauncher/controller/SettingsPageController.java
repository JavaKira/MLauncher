package com.example.mlauncher.controller;

import com.example.mlauncher.MLauncherPropertiesFacade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsPageController implements Initializable {
    @FXML
    private Slider beBuildsSlider;
    @FXML
    private Slider buildsSlider;
    @FXML
    private TextField pathTextField;
    @FXML
    private Button pathOpenButton;
    @FXML
    private Button resetButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        beBuildsSlider.setValue(MLauncherPropertiesFacade.getInstance().getBeBuilds());
        beBuildsSlider.setOnMouseReleased(actionEvent -> {
            System.out.println(beBuildsSlider.getValue());
            MLauncherPropertiesFacade.getInstance().setBeBuilds((int) beBuildsSlider.getValue());
            MLauncherPropertiesFacade.getInstance().storeProperties();
        });
        beBuildsSlider.setValue(MLauncherPropertiesFacade.getInstance().getVersions());
        buildsSlider.setOnMouseReleased(actionEvent -> {
            MLauncherPropertiesFacade.getInstance().setVersions((int) buildsSlider.getValue());
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
