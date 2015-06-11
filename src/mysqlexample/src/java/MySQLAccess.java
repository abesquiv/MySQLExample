package mysqlexample.src.java;


import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import com.mysql.jdbc.ResultSetMetaData;

public class MySQLAccess {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  private String nameDB = "res";
  private String nameTableMachine = "OLD_Targetpool_machines";
  private String nameTableUserset = "OLD_datapool_sets";
  public static enum TableType{MACHINE, USERSET};

  public void readDataBase() throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost/" + nameDB +"?"
              + "user=sqluser&password=sqluserpw");

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from " +nameDB+ "." +nameTableMachine);
      writeResultSet(resultSet);
      
      resultSet = statement
      .executeQuery("select * from "+nameDB+"." +nameTableMachine);
      writeMetaData(resultSet);
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }

  private void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query
    
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    while (resultSet.next()) {
      StringBuilder builder = new StringBuilder();
      for(int i = 1; i<=12; i++){
        if(i!=6 && i!=11)
          builder.append(resultSet.getObject(i));
        else{
          try {
            builder.append(resultSet.getString(i));
          } catch (Exception e) {
            builder.append("0000-00-00 00:00:00");
          }
        }
        builder.append(" ");
      }
      System.out.println(builder.toString());
      
    }
  }
  public static String getString(ResultSet resultSet) throws SQLException {
    StringBuilder builder = new StringBuilder();
    java.sql.ResultSetMetaData meta = resultSet.getMetaData();
    int numCol = meta.getColumnCount();
    while (resultSet.next()) {
      
      StringBuilder lineBuilder = new StringBuilder();
      for(int i = 1; i<=numCol; i++){
        try {
          lineBuilder.append(resultSet.getObject(i));
        } catch (Exception e) {
          lineBuilder.append("0000-00-00 00:00:00");
        }
        lineBuilder.append(" ");
      }
      builder.append(lineBuilder.toString());
      builder.append("\n");
      
    }
    return builder.toString();
  }
  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }
  
  public ResultSet getEntriesForPool(int pool, TableType type){
    try {
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      Connection tmpConnect = DriverManager
          .getConnection("jdbc:mysql://localhost/" + nameDB +"?"
              + "user=sqluser&password=sqluserpw");
      // Statements allow to issue SQL queries to the database
      Statement tmpStatement = tmpConnect.createStatement();
      // Result set get the result of the SQL query
      ResultSet tmpResultSet;
      if (type.equals(TableType.MACHINE))
        tmpResultSet = tmpStatement.executeQuery("select * from " + nameDB
            + "." + nameTableMachine + " where targetpool_id = " + pool);
      else
        tmpResultSet = tmpStatement.executeQuery("select * from " + nameDB
            + "." + nameTableUserset + " where targetpool_id = " + pool);
     
      return tmpResultSet;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  public ResultSet getEntriesForTarget(int target, TableType type){
    try {
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      Connection tmpConnect = DriverManager
          .getConnection("jdbc:mysql://localhost/" + nameDB +"?"
              + "user=sqluser&password=sqluserpw");
      // Statements allow to issue SQL queries to the database
      Statement tmpStatement = tmpConnect.createStatement();
      // Result set get the result of the SQL query
      ResultSet tmpResultSet;
      if (type.equals(TableType.MACHINE))
        tmpResultSet = tmpStatement.executeQuery("select * from " + nameDB
            + "." + nameTableMachine + " where targetid = " + target);
      else
        tmpResultSet = tmpStatement.executeQuery("select * from " + nameDB
            + "." + nameTableUserset + " where targetid = " + target);

      return tmpResultSet;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  public ResultSet getEntriesForOwner(String owner, TableType type){
    try {
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      Connection tmpConnect = DriverManager
          .getConnection("jdbc:mysql://localhost/" + nameDB +"?"
              + "user=sqluser&password=sqluserpw");
      // Statements allow to issue SQL queries to the database
      Statement tmpStatement = tmpConnect.createStatement();
      // Result set get the result of the SQL query
      ResultSet tmpResultSet;
      if (type.equals(TableType.MACHINE))
        tmpResultSet = tmpStatement.executeQuery("select * from " + nameDB
            + "." + nameTableMachine + " where owner = '" + owner + "'");
      else
        tmpResultSet = tmpStatement.executeQuery("select * from " + nameDB
            + "." + nameTableUserset + " where owner = '" + owner + "'");
     
      return tmpResultSet;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
 
  
  
  

} 