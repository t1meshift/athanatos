package com.company;

import java.sql.SQLException;

public class SQLMain {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        sql.Conn();
        sql.CreateDB();
        sql.WriteDB();
        sql.ReadDB();
        sql.CloseDB();
    }
}
