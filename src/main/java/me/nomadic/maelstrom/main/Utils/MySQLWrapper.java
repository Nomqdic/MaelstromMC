package me.nomadic.maelstrom.main.Utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//* by auxdible
public class MySQLWrapper {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;




    //* MySQL Database Info
    private String host, database, username, password;
    private int port;
    private static Connection connection;
    //* Constructor
    public MySQLWrapper(String host, String database, String username, String password, int port) {
        if(config.getBoolean("MySQLEnabled")) {
            this.host = this.config.getString("SQL.host");
            this.database = this.config.getString("SQL.database");
            this.username = this.config.getString("SQL.username");
            this.password = this.config.getString("SQL.password");
            this.port = this.config.getInt("SQL.port");

            try {
                openConnection();
            } catch (SQLException x) {
                System.out.println(" V Error with opening the connection to the mySQL database! Stack trace printed below V ");
                x.printStackTrace();
            }
        }



    }
    //* Open the connection to the database
    private void openConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                this.username, this.password);
    }
    //* Prepare statement, requires .executeQuery() or .executeUpdate() to execute.
    public static PreparedStatement prepareStatement(String query) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
        } catch (SQLException x) {
            x.printStackTrace();
        }
        return ps;
    }
}