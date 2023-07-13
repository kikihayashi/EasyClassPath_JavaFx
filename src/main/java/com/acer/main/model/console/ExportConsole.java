package com.acer.main.model.console;

import java.util.List;

public class ExportConsole extends Console {

    private List list;
    public <T> ExportConsole(List<T> list) {
        this.list = list;
    }

    @Override
    public String getMessage() {
        return getDate() + ((list.size() > 0) ? "[INFO] 開始進行輸出作業..." : "[WARN] 找不到檔案！") + "\n";
    }
}
