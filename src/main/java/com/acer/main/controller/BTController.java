package com.acer.main.controller;

import com.acer.main.bean.ProjectFile;
import com.acer.main.fxml.tableview.ItemListener;
import com.acer.main.fxml.menu.FileMenu;
import com.acer.main.fxml.menu.OtherMenu;
import com.acer.main.fxml.tableview.RealPathTableView;
import com.acer.main.fxml.tableview.TomcatPathTableView;
import com.acer.main.model.folder.Folder;
import com.acer.main.model.folder.ProjectFolder;
import com.acer.main.model.folder.SourceCodeFolder;
import com.acer.main.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BTController {
    private SearchService searchService;
    private ConsoleService consoleService;
    private Map<String, Folder> folderMap;
    private Stage stage;
    @FXML
    private MenuBar menubar;
    @FXML
    private MenuItem open_menuitem;
    @FXML
    private MenuItem about_menuitem;
    @FXML
    private TextField path_text;
    @FXML
    private TextField filename_text;
    @FXML
    private TextArea filename_textarea;
    @FXML
    private TextArea console_textarea;
    @FXML
    private AnchorPane console_anchorpane;
    @FXML
    private CheckBox sourcecode_checkbox;
    @FXML
    private ComboBox sourcecode_combobox;
    @FXML
    private ComboBox ide_combobox;
    @FXML
    private TableView realpath_tableview;
    @FXML
    private TableView tomcatpath_tableview;
    @FXML
    private Button export_button;

    public BTController() {
        searchService = new SearchService();
        consoleService = new ConsoleService();
        folderMap = new HashMap<>();
    }

    //在BTApplication設置Stage，後續讓file_menu可以開新視窗
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * 初始化設定
     */
    //這是JavaFX在Controller提供的方法，會先執行建構子再執行initialize()
    public void initialize() {
        setFileMenu();
        setOtherMenu();
        setConsoleAnchorpane();
        setIDECombobox();
        setSourceCodeCombobox();
        setExportButtonDisableStatus();
    }

    //設置選擇資料夾
    private void setFileMenu() {
        open_menuitem.setOnAction(e -> new FileMenu(this.stage, path_text).handle());
    }

    //設置關於本程式
    private void setOtherMenu() {
        about_menuitem.setOnAction(e -> new OtherMenu().handle());
    }

    //設置邊界，讓console_textarea占滿整個console_anchorpane
    private void setConsoleAnchorpane() {
        console_anchorpane.setTopAnchor(console_textarea, -10.0);
        console_anchorpane.setBottomAnchor(console_textarea, -10.0);
        console_anchorpane.setLeftAnchor(console_textarea, -10.0);
        console_anchorpane.setRightAnchor(console_textarea, -10.0);
    }

    //設置IDE格式的選項(SearchTool內部判斷檔案路徑要用)
    private void setIDECombobox() {
        ObservableList<String> data = FXCollections.observableArrayList("Intellij IDEA", "Eclipse");
        ide_combobox.setItems(data);
        ide_combobox.valueProperty().addListener((observable, oldValue, newValue) -> setPathPromteText());
        //設置combobox預設index為0，代表預設"Intellij IDEA"
        ide_combobox.getSelectionModel().select(0);
    }

    //設置預設路徑
    private void setPathPromteText() {
        BTService.setIDE(ide_combobox.getSelectionModel().getSelectedIndex());
        path_text.setPromptText("預設路徑：" + BTService.getDefaultPath());
    }

    //設置原始檔案(含.java檔)匯出的選項
    private void setSourceCodeCombobox() {
        ObservableList<String> data = FXCollections.observableArrayList("同一資料夾", "原專案路徑");
        sourcecode_combobox.setItems(data);
        //設置combobox預設index為0，代表預設"同一資料夾"
        sourcecode_combobox.getSelectionModel().select(0);
    }

    //設置export_button是否可點選
    private void setExportButtonDisableStatus() {
        long javaFileNumber = tomcatpath_tableview.getItems().stream().filter(pf -> ((ProjectFile) pf).getFileType().equals("java")).count();
        int tableviewCount = tomcatpath_tableview.getItems().size();
        export_button.setDisable(tableviewCount == 0 || tableviewCount == javaFileNumber);
    }

    /**
     * FXML相關方法
     */
    @FXML
    protected void onSearchClick(ActionEvent actionEvent) {
        String searchPath = path_text.getText();
        String searchFileName = filename_text.getText();
        if (searchFileName.isEmpty()) {
            console_textarea.appendText(consoleService.inputCheck());
            return;
        }

        realpath_tableview.getItems().clear();
        List<ProjectFile> projectFileList = searchService.search(searchPath, searchFileName);
        setPathPromteText();
        console_textarea.appendText(consoleService.searchFileCheck(projectFileList));
        setRealPathTableView(projectFileList);
    }

    @FXML
    public void onImportClick(ActionEvent actionEvent) {
        String searchPath = path_text.getText();
        String searchFileNames = filename_textarea.getText();
        if (searchFileNames.isEmpty()) {
            console_textarea.appendText(consoleService.inputCheck());
            return;
        }

        tomcatpath_tableview.getItems().clear();
        List<ProjectFile> projectFileList = searchService.importSearch(searchPath, searchFileNames);
        setPathPromteText();
        console_textarea.appendText(consoleService.searchFileCheck(projectFileList));
        projectFileList.stream().forEach(pf -> setTomcatPathTableView(pf));
        setExportButtonDisableStatus();
    }

    @FXML
    protected void onExportClick(ActionEvent actionEvent) {
        setSourceCodeOnOff(sourcecode_checkbox.isSelected(), sourcecode_combobox.getSelectionModel().getSelectedIndex());
        StringBuilder sb = new StringBuilder();
        sb.append(consoleService.exportFile(tomcatpath_tableview.getItems()));
        sb.append(consoleService.deleteFolder());
        sb.append(consoleService.copyFile(tomcatpath_tableview.getItems(), folderMap));
        sb.append(consoleService.createTxt(folderMap));
        sb.append(consoleService.createZip());
        sb.append(consoleService.exportFinish());
        sb.append(consoleService.openFolder());
        console_textarea.appendText(sb.toString());
    }

    @FXML
    protected void onClearClick(ActionEvent actionEvent) {
        realpath_tableview.getItems().clear();
        tomcatpath_tableview.getItems().clear();
        filename_textarea.clear();
        setExportButtonDisableStatus();
        console_textarea.appendText(consoleService.clearList());
    }

    @FXML
    public void onClearConsoleClick(ActionEvent actionEvent) {
        console_textarea.clear();
    }

    /**
     * 內部呼叫方法
     */
    private void setRealPathTableView(List list) {
        new RealPathTableView(realpath_tableview, list, new ItemListener() {
            @Override
            public void callback(MouseEvent event, TableRow row) {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Object addItem = row.getItem();
                    setTomcatPathTableView(addItem);
                    setExportButtonDisableStatus();
                }
            }
        }).show();
    }

    private void setTomcatPathTableView(Object item) {
        new TomcatPathTableView(tomcatpath_tableview, item, new ItemListener() {
            @Override
            public void callback(MouseEvent event, TableRow row) {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Object item = tomcatpath_tableview.getSelectionModel().getSelectedItem();
                    tomcatpath_tableview.getItems().remove(item);
                    setExportButtonDisableStatus();
                }
            }
        }).show();
    }

    //設置是否要輸出SourceCode
    private void setSourceCodeOnOff(boolean selected, int typeIndex) {
        folderMap.clear();
        folderMap.put(ProjectFolder.FOLDER_NAME, new ProjectFolder(new StringBuilder()));
        if (selected) {
            folderMap.put(SourceCodeFolder.FOLDER_NAME, new SourceCodeFolder(typeIndex));
        }
    }
}
