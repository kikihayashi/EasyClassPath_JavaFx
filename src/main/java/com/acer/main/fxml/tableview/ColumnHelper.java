package com.acer.main.fxml.tableview;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ColumnHelper {

    public static <S, T> TableColumn<S, T> getColumn(TableView<S> tableView, String title, String beanAttribute, double widthPercentage) {
        TableColumn<S, T> tableColumn = new TableColumn<>(title);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(beanAttribute));
        tableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(widthPercentage));
        return tableColumn;
    }
}
