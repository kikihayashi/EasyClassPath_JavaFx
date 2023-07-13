package com.acer.main.fxml.tableview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.util.List;

public class RealPathTableView implements ViewAction {

    private TableView tableview;
    private List list;
    private ItemListener listener;

    public <T> RealPathTableView(TableView<T> tableview, List list, ItemListener listener) {
        this.tableview = tableview;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public void show() {
        if (list.size() > 0) {
            if (tableview.getColumns().size() == 0) {
                setRealPathColumn();
            }
            tableview.setItems(getProjectFileList(list));
        }
    }

    private void setRealPathColumn() {
        TableColumn inputSourceCodePathColumn =
                ColumnHelper.getColumn(tableview, "檔案路徑", "sourceCodePath", 0.75);
        TableColumn inputLastModifiedTimeColumn =
                ColumnHelper.getColumn(tableview, "最後修改日期", "lastModifiedTime", 0.25);

        tableview.getColumns().addAll(inputSourceCodePathColumn, inputLastModifiedTimeColumn);
        tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableview.setRowFactory(tv -> {
            TableRow row = new TableRow<>();
            row.setOnMouseClicked(event -> listener.callback(event, row));
            return row;
        });
    }

    private <T> ObservableList<T> getProjectFileList(List<T> list) {
        ObservableList<T> observableList = FXCollections.observableArrayList();
        list.stream().forEach(pf -> observableList.add(pf));
        return observableList;
    }

}
