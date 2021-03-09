package com.covid.vaccine.covidvac.controller;

public class Rsponse {
    String msg;
    Rsponse(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
