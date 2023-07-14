package com.acer.main.fxml.fxmlinterface;

import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;

public interface TableViewListener {
    void callback(MouseEvent event, TableRow row);
}
