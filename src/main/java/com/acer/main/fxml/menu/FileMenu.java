package com.acer.main.fxml.menu;


import com.acer.main.service.BTService;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileMenu implements ViewHandler {

    private Stage stage;
    private TextField textField;

    public FileMenu(Stage stage, TextField textField) {
        this.stage = stage;
        this.textField = textField;
    }

    @Override
    public void handle() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(BTService.getDefaultPath()));
        directoryChooser.setTitle("選擇專案資料夾");
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            String directoryPath = selectedDirectory.getAbsolutePath();
            textField.setText(directoryPath);
        }
    }
}
