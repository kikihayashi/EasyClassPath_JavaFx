package com.acer.main.fxml.menu;

import javafx.scene.control.Alert;

public class OtherMenu implements ViewHandler {
    @Override
    public void handle() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("說明");
        alert.setHeaderText(null);
        String message = "本程式使用JavaFx開發，擁有EasyClassPath的功能。\n";
        message += "支援：\n";
        message += "1.Intellij IDEA專案\n";
        message += "2.多筆檔案匯入搜尋功能\n";
        message += "3.匯出java原始檔功能";
        alert.setContentText(message);
        alert.showAndWait();
    }
}
