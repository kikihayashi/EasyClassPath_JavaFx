package com.acer.main.fxml.tableview;

import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;

public interface ItemListener {
    void callback(MouseEvent event, TableRow row);
}
