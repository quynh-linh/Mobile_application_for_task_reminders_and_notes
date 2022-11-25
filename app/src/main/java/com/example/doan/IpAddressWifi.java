package com.example.doan;

public class IpAddressWifi {
    public String ip;
    public String fileNameDB;
    public String portLocalHost;

    public IpAddressWifi() {
        this.ip = "192.168.125.185";
        this.fileNameDB = "Mobile_App";
        this.portLocalHost = ":8080";
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFileNameDB() {
        return fileNameDB;
    }

    public void setFileNameDB(String fileNameDB) {
        this.fileNameDB = fileNameDB;
    }

    public String getPortLocalHost() {
        return portLocalHost;
    }

    public void setPortLocalHost(String portLocalHost) {
        this.portLocalHost = portLocalHost;
    }
}
