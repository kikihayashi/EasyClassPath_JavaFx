package com.acer.main.fxml.menu;

import com.acer.main.fxml.fxmlinterface.FxmlHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

public class OtherMenu implements FxmlHandler {

    private MenuItem menuItem;

    public OtherMenu(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    //設置關於本程式
    @Override
    public void handle() {
        menuItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("說明");
            alert.setHeaderText(null);
            String message = "本程式使用JavaFx開發，擁有原EasyClassPath功能。\n";
            message += "並支援：\n";
            message += "1.Intellij IDEA專案\n";
            message += "2.多筆檔案匯入搜尋功能\n";
            message += "3.匯出java原始檔功能\n";
            message += "4.匯出列表拖曳功能";
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
