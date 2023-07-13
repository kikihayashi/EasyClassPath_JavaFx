package com.acer.main;

import com.acer.main.controller.BTController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BTApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(BTApplication.class.getResource("bt-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        BTController btController = fxmlLoader.getController();
        btController.setStage(stage);
        stage.setTitle("EasyClassPath - WoodyLin @2023");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
