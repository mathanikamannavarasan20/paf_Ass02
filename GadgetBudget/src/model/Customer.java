package model;

import java.sql.*;
public class Customer
{ //A common method to connect to the DB
private Connection connect()
 {
 Connection con = null;
 try
 {
 Class.forName("com.mysql.jdbc.Driver");

 //Provide the correct details: DBServer/DBName, username, password
 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbudget", "root", "AKAI@1997");
 }
 catch (Exception e)
 {e.printStackTrace();}
 return con;
 }
public String insertCustomer(String code, String name, String age, String address)
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for inserting."; }
 // create a prepared statement
 String query = " insert into customers(`customerID`,`customerCode`,`customerName`,`customerAge`,`customerAddress`) values (?, ?, ?, ?, ?)";
 PreparedStatement preparedStmt = con.prepareStatement(query);
 // binding values
 preparedStmt.setInt(1, 0);
 preparedStmt.setString(2, code);
 preparedStmt.setString(3, name);
 preparedStmt.setDouble(4, Double.parseDouble(age));
 preparedStmt.setString(5, address);
// execute the statement

 preparedStmt.execute();
 con.close();
 String newCustomers = readCustomers();
 output = "{\"status\":\"success\", \"data\": \"" +newCustomers + "\"}";
 }
 catch (Exception e)
 {
 output = "{\"status\":\"error\", \"data\":\"Error while inserting the customer.\"}";
 System.err.println(e.getMessage());
 } 
 return output;
 }
public String readCustomers()
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for reading."; }
//Prepare the html table to be displayed
output = "<table border=\"1\"><tr><th>Customer Code</th><th>Customer Name</th><th>Customer Age</th><th>Customer Customer</th><th>Update</th><th>Remove</th></tr>";
String query = "select * from customers";
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
// iterate through the rows in the result set
while (rs.next())
{
String customerID = Integer.toString(rs.getInt("customerID"));
String customerCode = rs.getString("customerCode");
String customerName = rs.getString("customerName");
String customerAge = Double.toString(rs.getDouble("customerAge"));
String customerAddress = rs.getString("customerAddress");

// Add into the html table
output += "<tr><td>"+ customerCode + "</td>";
output += "<td>" + customerName + "</td>";
output += "<td>" + customerAge + "</td>";
output += "<td>" + customerAddress + "</td>";
// buttons
output += "<td><input name='btnUpdate' type='button' value='Update' "
+ "class='btnUpdate btn btn-secondary' data-customerid='" + customerID + "'></td>"
+ "<td><input name='btnRemove' type='button' value='Remove' "
+ "class='btnRemove btn btn-danger' data-customerid='" + customerID + "'></td></tr>";
} 
 con.close();
 // Complete the html table
 output += "</table>";
 }
 catch (Exception e)
 {
 output = "Error while reading the customers.";
 System.err.println(e.getMessage());
 }
 return output;
 }
public String updateCustomer(String ID, String code, String name, String age, String address)

 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for updating."; }
 // create a prepared statement
 String query = "UPDATE customers SET customerCode=?,customerName=?,customerAge=?,customerAddress=?WHERE customerID=?";
 PreparedStatement preparedStmt = con.prepareStatement(query);
 // binding values
 preparedStmt.setString(1, code);
 preparedStmt.setString(2, name);
 preparedStmt.setDouble(3, Double.parseDouble(age));
 preparedStmt.setString(4, address);
 preparedStmt.setInt(5, Integer.parseInt(ID));
 // execute the statement
 preparedStmt.execute();
 con.close();
 String newCustomers = readCustomers();
 output = "{\"status\":\"success\", \"data\": \"" +newCustomers + "\"}";
 }
 catch (Exception e)
 {
 output = "{\"status\":\"error\", \"data\": \"Error while updating the customer.\"}";
 System.err.println(e.getMessage());
 } 
 return output;
 }
public String deleteCustomer(String customerID)
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for deleting."; }
 // create a prepared statement
 String query = "delete from customers where customerID=?";
 PreparedStatement preparedStmt = con.prepareStatement(query);
 // binding values
 preparedStmt.setInt(1, Integer.parseInt(customerID));
 // execute the statement
 preparedStmt.execute();
 con.close();
 String newCustomers = readCustomers();
 output = "{\"status\":\"success\", \"data\": \"" +newCustomers + "\"}";
 }
 catch (Exception e)
 {
 output = "{\"status\":\"error\", \"data\":\"Error while deleting the customer.\"}";
 System.err.println(e.getMessage());
 } 
 return output;
 }
} 