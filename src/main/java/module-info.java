module com.example.mlauncher {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.mlauncher to javafx.fxml;
    exports com.example.mlauncher;
}