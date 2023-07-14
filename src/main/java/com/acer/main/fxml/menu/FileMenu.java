package com.acer.main.fxml.menu;

import com.acer.main.fxml.fxmlinterface.FxmlHandler;
import com.acer.main.service.BTService;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileMenu implements FxmlHandler {

    private final MenuItem menuItem;
    private Stage stage;
    private TextField textField;

    public FileMenu(MenuItem menuItem, Stage stage, TextField textField) {
        this.menuItem = menuItem;
        this.stage = stage;
        this.textField = textField;
    }

    //設置選擇資料夾
    @Override
    public void handle() {
        menuItem.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File(BTService.getDefaultPath()));
            directoryChooser.setTitle("選擇專案資料夾");
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                String directoryPath = selectedDirectory.getAbsolutePath();
                textField.setText(directoryPath);
            }
        });
    }
}
