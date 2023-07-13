package com.acer.main.model.console;

import java.util.List;

public class SearchConsole extends Console {

    private List list;

    public <T> SearchConsole(List<T> list) {
        this.list = list;
    }

    @Override
    public String getMessage() {
        //搜尋檢查
        return getDate() + ((list.size() == 0) ? "[INFO] 查無相關檔案！" :
                "[SUCCESS] 搜尋成功，總共找到" + list.size() + "筆資料！") + "\n";

    }

}
