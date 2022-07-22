module com.example.mlauncher {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.json;

    opens com.example.mlauncher to javafx.fxml;
    exports com.example.mlauncher;
    exports com.example.mlauncher.util;
    opens com.example.mlauncher.util to javafx.fxml;
}