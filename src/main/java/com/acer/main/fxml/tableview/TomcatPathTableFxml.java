package com.acer.main.fxml.tableview;

import com.acer.main.bean.ProjectFile;
import com.acer.main.fxml.fxmlinterface.TableViewListener;
import com.acer.main.fxml.fxmlinterface.FxmlHandler;
import com.acer.main.handler.DragHandler;
import javafx.scene.control.*;

public class TomcatPathTableFxml implements FxmlHandler {

    private TableView tableView;
    private Object item;

    private TableViewListener listener;

    public <T> TomcatPathTableFxml(TableView<T> tableView, T item, TableViewListener listener) {
        this.tableView = tableView;
        this.item = item;
        this.listener = listener;
    }

    @Override
    public void handle() {
        if (tableView.getColumns().size() == 0) {
            setTomcatPathColumn();
        }
        if (!tableView.getItems().contains(item)) {
            tableView.getItems().add(item);
        }
    }

    private void setTomcatPathColumn() {
        TableColumn<ProjectFile, String> outputFileNameColumn =
                ColumnHelper.getColumn(tableView, "檔案名稱", "fileName", 0.3);
        TableColumn<ProjectFile, String> outputFileTypeColumn =
                ColumnHelper.getColumn(tableView, "類型", "fileType", 0.2);
        TableColumn<ProjectFile, String> outputFileSizeColumn =
                ColumnHelper.getColumn(tableView, "檔案大小", "fileSize", 0.2);
        TableColumn<ProjectFile, String> outputLastModifiedTimeColumn =
                ColumnHelper.getColumn(tableView, "最後修改日期", "lastModifiedTime", 0.3);

        setColumnPosition(outputFileNameColumn, "-fx-alignment: center-left;");
        setColumnPosition(outputFileTypeColumn, "-fx-alignment: center-right;");
        setColumnPosition(outputFileSizeColumn, "-fx-alignment: center-right;");
        setColumnPosition(outputLastModifiedTimeColumn, "-fx-alignment: center-left;");

        tableView.getColumns().addAll(outputFileNameColumn, outputFileTypeColumn, outputFileSizeColumn, outputLastModifiedTimeColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//設置可以多選
        tableView.setRowFactory(tv -> {
            TableRow row = new TableRow<>();
            row.setOnMouseClicked(event ->  listener.callback(event, row));
            row.setOnDragDetected(event -> DragHandler.detect((TableView) tv, event, row));
            row.setOnDragOver(event -> DragHandler.over(event, row));
            row.setOnDragDropped(event -> DragHandler.drop((TableView) tv, event, row));
            return row;
        });
    }

    private <T> void setColumnPosition(TableColumn<T, String> column, String style) {
        column.setCellFactory(col -> {
            return new TableCell<T, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setStyle(style);
                    }
                }
            };
        });
    }

}
