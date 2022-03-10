package com.bitcom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stageD) throws Exception {
        Parent parentD = FXMLLoader.load(getClass().getResource("pendudukPage.fxml"));
        stageD.setTitle("Program Data Penduduk");
        stageD.setScene(new Scene(parentD));
        stageD.show();
    }

    public static void main(String[] args) {launch(args);}
}
