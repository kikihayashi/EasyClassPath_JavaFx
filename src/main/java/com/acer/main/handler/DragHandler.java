package com.acer.main.handler;

import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.*;

import java.util.ArrayList;
import java.util.List;

public class DragHandler {

    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    private static List list;

    public static <T> void detect(TableView tableView, MouseEvent event, TableRow<T> row) {
        if (!row.isEmpty()) {
            Integer index = row.getIndex();
            list = new ArrayList<>();

            ObservableList<T> pfList = tableView.getSelectionModel().getSelectedItems();

            for (T pf : pfList) {
                list.add(pf);
            }

            Dragboard dragboard = row.startDragAndDrop(TransferMode.MOVE);
            dragboard.setDragView(row.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(SERIALIZED_MIME_TYPE, index);
            dragboard.setContent(cc);
            event.consume();
        }
    }

    public static <T> void over(DragEvent event, TableRow<T> row) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(SERIALIZED_MIME_TYPE)) {
            if (row.getIndex() != ((Integer) dragboard.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        }
    }

    public static <T> void drop(TableView tableView, DragEvent event, TableRow<T> row) {
        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasContent(SERIALIZED_MIME_TYPE)) {
            int dropIndex;
            T item = null;

            if (row.isEmpty()) {
                dropIndex = tableView.getItems().size();
            } else {
                dropIndex = row.getIndex();
                item = (T) tableView.getItems().get(dropIndex);
            }

            int delta = 0;
            if (item != null) {
                while (list.contains(item)) {
                    delta = 1;
                    --dropIndex;
                    if (dropIndex < 0) {
                        item = null;
                        dropIndex = 0;
                        break;
                    }
                    item = (T) tableView.getItems().get(dropIndex);
                }
            }

            for (Object sI : list) {
                tableView.getItems().remove(sI);
            }

            if (item != null) {
                dropIndex = tableView.getItems().indexOf(item) + delta;
            } else if (dropIndex != 0) {
                dropIndex = tableView.getItems().size();
            }
            tableView.getSelectionModel().clearSelection();

            for (Object sI : list) {
                tableView.getItems().add(dropIndex, sI);
                tableView.getSelectionModel().select(dropIndex);
                dropIndex++;
            }

            event.setDropCompleted(true);
            list.clear();
            event.consume();
        }
    }
}
