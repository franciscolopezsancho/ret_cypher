package com.cognia.reader;

import com.cognia.cypher.bo.DBFile;
import com.cognia.cypher.bo.DecodingIndex;
import scala.collection.mutable.StringBuilder;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * User: fran
 * Date: 21/06/2015
 */
public class SqlReader
{


    public static DBFile load() throws ClassNotFoundException
    {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;



        final Queue<DecodingIndex> decodingIndexes = new LinkedList();
        final StringBuilder sb = new StringBuilder();

        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/fran/Downloads/problem6/puzzle.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.


            //ResultSet rs1 = statement.executeQuery("ATTACH /Users/fran/Downloads/problem6/puzzle.db AS my_db;");

            ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table'");
            while(rs.next())
            {
                System.out.println("name = " + rs.getString("name"));
            }
            ResultSet rs2 = statement.executeQuery("SELECT * FROM b0dy");
            int counter = 0;
            while(rs2.next())
            {
                // read the result set
                sb.append(rs2.getString("data"));
            }
            ResultSet rs3 = statement.executeQuery("SELECT * FROM crypt");
            while(rs3.next())
            {

                decodingIndexes.add(DecodingIndex.apply(Integer.valueOf(rs3.getString("start")),Integer.valueOf(rs3.getString("length")),Integer.valueOf(rs3.getString("rotate"))));
            }

        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
                return DBFile.apply(sb,decodingIndexes);

            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
        return DBFile.apply(sb,decodingIndexes);

    }
}