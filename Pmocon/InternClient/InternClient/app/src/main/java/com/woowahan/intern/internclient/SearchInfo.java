package com.woowahan.intern.internclient;

/**
 * Created by user on 2015. 6. 29..
 */
public class SearchInfo {
    private String Name;
    private String Ip;
    private String Port;

    public SearchInfo(String name, String ip, String port) {
        Name = name;
        Ip = ip;
        Port = port;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }
}
