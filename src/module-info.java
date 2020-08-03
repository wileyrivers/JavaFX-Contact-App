module JavaFX.Challenge {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires basicGraphics;
    requires java.xml;

    opens sample;
    opens dataModel;
}