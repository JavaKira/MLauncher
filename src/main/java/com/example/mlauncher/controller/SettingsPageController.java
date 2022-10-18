package com.example.mlauncher.controller;

import com.example.mlauncher.PropertiesFacade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
        beBuildsSlider.setValue(PropertiesFacade.getInstance().getBeBuilds());
        beBuildsSlider.setOnMouseReleased(actionEvent -> {
            System.out.println(beBuildsSlider.getValue());
            PropertiesFacade.getInstance().setBeBuilds((int) beBuildsSlider.getValue());
            PropertiesFacade.getInstance().storeProperties();
        });
        beBuildsSlider.setValue(PropertiesFacade.getInstance().getVersions());
        buildsSlider.setOnMouseReleased(actionEvent -> {
            PropertiesFacade.getInstance().setVersions((int) buildsSlider.getValue());
            PropertiesFacade.getInstance().storeProperties();
        });
        pathTextField.setText(PropertiesFacade.getInstance().getPath());
        pathTextField.setOnAction(actionEvent -> {
            PropertiesFacade.getInstance().setPath(pathTextField.getText());
            PropertiesFacade.getInstance().storeProperties();
        });
        pathOpenButton.setOnAction(actionEvent -> {
            try {
                Runtime.getRuntime().exec(new String[]{"explorer", '"' + pathTextField.getText() + '"'});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        resetButton.setOnAction(actionEvent -> {
            PropertiesFacade.getInstance().setDefaults();
            PropertiesFacade.getInstance().storeProperties();
            initialize(location, resources);
        });
    }
}
