package com.acer.main.fxml.combobox;

import com.acer.main.fxml.fxmlinterface.ComboBoxListener;
import com.acer.main.fxml.fxmlinterface.FxmlHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class IDEComboBox implements FxmlHandler {

    private final static String[] ideSet = {"Intellij IDEA", "Eclipse"};
    private ComboBox combobox;
    private final ComboBoxListener listener;

    public IDEComboBox(ComboBox combobox, ComboBoxListener listener) {
        this.combobox = combobox;
        this.listener = listener;
    }

    //設置IDE格式的選項(SearchTool內部判斷檔案路徑要用)
    @Override
    public void handle() {
        ObservableList<String> data = FXCollections.observableArrayList(ideSet);
        combobox.setItems(data);
        combobox.valueProperty().addListener((observable, oldValue, newValue) -> listener.callback());
        //設置combobox預設index為0，代表預設"Intellij IDEA"
        combobox.getSelectionModel().select(0);
    }
}
