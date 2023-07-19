package com.acer.main.fxml.anchorpane;

import com.acer.main.fxml.fxmlinterface.FxmlHandler;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;


public class ConsoleAnchorPane implements FxmlHandler {
    private final AnchorPane anchorPane;
    private final TextArea textArea;

    public ConsoleAnchorPane(AnchorPane anchorPane, TextArea textArea) {
        this.anchorPane = anchorPane;
        this.textArea = textArea;
    }

    //設置邊界，讓console_textarea占滿整個console_anchorpane
    @Override
    public void handle() {
        anchorPane.setTopAnchor(textArea, -10.0);
        anchorPane.setBottomAnchor(textArea, -10.0);
        anchorPane.setLeftAnchor(textArea, -10.0);
        anchorPane.setRightAnchor(textArea, -10.0);
    }
}
