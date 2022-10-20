package com.example.mlauncher;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Application extends javafx.application.Application {
    private static final Logger log = LogManager.getLogger(Application.class);

    @Override
    public void start(Stage stage) throws IOException {
        File folder = new File(System.getenv("APPDATA") + "\\MLauncher");
        log.info("Folder " + folder.getAbsolutePath() + " status: " +
                (folder.exists() ? "exists" : folder.mkdir() ? "created" : "failed to create"));

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("SideBar.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("MLauncher");
        Arrays.stream(new int[]{16, 32, 64, 128, 256, 512}).forEach(integer -> {
            stage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/icon" + integer + ".png"))));
        });
        stage.setWidth(600);
        stage.setHeight(400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}