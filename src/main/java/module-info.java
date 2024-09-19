module me.paulojr.cadastrosfront {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.google.gson;
    requires kotlin.stdlib;


    opens me.paulojr.cadastrosfront to javafx.fxml;
    exports me.paulojr.cadastrosfront.models;
    exports me.paulojr.cadastrosfront;
    exports me.paulojr.cadastrosfront.controller;
    opens me.paulojr.cadastrosfront.controller to javafx.fxml;
}