package ru.infiniteam;

import java.sql.SQLException;

/**
 * Created by Диана on 07.04.2017.
 */
public class SQLMain {
    public static void SQLMain(String[] args) throws ClassNotFoundException, SQLException {
        sql.Conn();
        sql.CreateDB();
        sql.WriteDB();
        sql.ReadDB();
        sql.CloseDB();

    }
}
