package com.acer.main.fxml.combobox;

import com.acer.main.fxml.fxmlinterface.FxmlHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class SourceCodeComboBox implements FxmlHandler {

    private final static String[] typeSet = {"同一資料夾", "原專案路徑", "以上兩種都要"};
    private ComboBox combobox;

    public SourceCodeComboBox(ComboBox combobox) {
        this.combobox = combobox;
    }

    //設置原始檔案(含.java檔)匯出的選項
    @Override
    public void handle() {
        ObservableList<String> data = FXCollections.observableArrayList(typeSet);
        combobox.setItems(data);
        //設置combobox預設index為0，代表預設"同一資料夾"
        combobox.getSelectionModel().select(0);
    }
}
