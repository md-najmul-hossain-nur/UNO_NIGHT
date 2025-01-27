module com.example.uno_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires jdk.unsupported.desktop;
    requires java.smartcardio;
    requires java.logging;

    opens com.example.uno_project to javafx.fxml;
    exports com.example.uno_project;
}
