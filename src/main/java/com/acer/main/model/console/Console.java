package com.acer.main.model.console;

import java.util.Date;

public abstract class Console {

    private Date date = new Date();

    public abstract String getMessage();

    //取得現在時間
    public String getDate() {
        return " " + date.toString() + " 資訊 ";
    }

}
