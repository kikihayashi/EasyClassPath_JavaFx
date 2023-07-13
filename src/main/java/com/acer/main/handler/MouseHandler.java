package com.acer.main.handler;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class MouseHandler {

    public static <T> void click(TableView<T> tableView, MouseEvent event, TableRow row, HandlerCallback callback) {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
            Object item = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(item);
            callback.run();
        }
    }
}
