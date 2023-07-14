package com.acer.main.controller;

import com.acer.main.bean.ProjectFile;
import com.acer.main.fxml.anchorpane.ConsoleAnchorPane;
import com.acer.main.fxml.combobox.IDEComboBox;
import com.acer.main.fxml.combobox.SourceCodeComboBox;
import com.acer.main.fxml.fxmlinterface.ComboBoxListener;
import com.acer.main.fxml.fxmlinterface.TableViewListener;
import com.acer.main.fxml.menu.FileMenu;
import com.acer.main.fxml.menu.OtherMenu;
import com.acer.main.fxml.tableview.RealPathTableFxml;
import com.acer.main.fxml.tableview.TomcatPathTableFxml;
import com.acer.main.model.folder.Folder;
import com.acer.main.model.folder.ProjectFolder;
import com.acer.main.model.folder.SourceCodeFolder;
import com.acer.main.service.*;
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
        new FileMenu(open_menuitem, stage, path_text).handle();
        new OtherMenu(about_menuitem).handle();
        new ConsoleAnchorPane(console_anchorpane, console_textarea).handle();
        new SourceCodeComboBox(sourcecode_combobox).handle();
        new IDEComboBox(ide_combobox, new ComboBoxListener() {
            @Override
            public void callback() {
                setPathPromteText();
            }
        }).handle();
        setExportButtonDisableStatus();
    }

    //設置預設路徑
    private void setPathPromteText() {
        BTService.setIDE(ide_combobox.getSelectionModel().getSelectedIndex());
        path_text.setPromptText("預設路徑：" + BTService.getDefaultPath());
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
        StringBuilder console = exportFileAndCreateConsole();
        console_textarea.appendText(console.toString());
    }

    @FXML
    protected void onClearClick(ActionEvent actionEvent) {
        filename_text.clear();
        filename_textarea.clear();
        realpath_tableview.getItems().clear();
        tomcatpath_tableview.getItems().clear();
        console_textarea.appendText(consoleService.clearList());
        setExportButtonDisableStatus();
    }

    @FXML
    public void onClearConsoleClick(ActionEvent actionEvent) {
        console_textarea.clear();
    }

    /**
     * 內部呼叫方法
     */
    private void setRealPathTableView(List list) {
        new RealPathTableFxml(realpath_tableview, list, new TableViewListener() {
            @Override
            public void callback(MouseEvent event, TableRow row) {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Object addItem = row.getItem();
                    setTomcatPathTableView(addItem);
                    setExportButtonDisableStatus();
                }
            }
        }).handle();
    }

    private void setTomcatPathTableView(Object item) {
        new TomcatPathTableFxml(tomcatpath_tableview, item, new TableViewListener() {
            @Override
            public void callback(MouseEvent event, TableRow row) {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Object item = tomcatpath_tableview.getSelectionModel().getSelectedItem();
                    tomcatpath_tableview.getItems().remove(item);
                    setExportButtonDisableStatus();
                }
            }
        }).handle();
    }

    private void setSourceCodeOnOff(boolean selected, int typeIndex) {
        folderMap.clear();
        folderMap.put(ProjectFolder.FOLDER_NAME, new ProjectFolder(new StringBuilder()));
        if (selected) {
            folderMap.put(SourceCodeFolder.FOLDER_NAME, new SourceCodeFolder(typeIndex));
        }
    }

    private StringBuilder exportFileAndCreateConsole() {
        StringBuilder sb = new StringBuilder();
        sb.append(consoleService.exportFile(tomcatpath_tableview.getItems()));
        sb.append(consoleService.deleteFolder());
        sb.append(consoleService.copyFile(tomcatpath_tableview.getItems(), folderMap));
        sb.append(consoleService.createTxt(folderMap));
        sb.append(consoleService.createZip());
        sb.append(consoleService.exportFinish());
        sb.append(consoleService.openFolder());
        return sb;
    }

}
