package com.github.clockclap.gunwar.mysql;

final class GwMySqlSetting {

    private final String host;
    private final int port;
    private final String database;
    private final String option;
    private final String username;
    private final String password;
    private final String url;

    GwMySqlSetting(String host, int port, String database, String option, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.option = option;
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database + option;
    }

    String getHost() {
        return host;
    }

    int getPort() {
        return port;
    }

    String getDatabase() {
        return database;
    }

    String getOption() {
        return option;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    String getUrl() {
        return url;
    }
}
