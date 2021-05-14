<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@page import="model.Customer"%>
<%
	//Save---------------------------------
if (request.getParameter("customerCode") != null)
{
Customer customerObj = new Customer();
String stsMsg = "";
//Insert--------------------------
if (request.getParameter("hidCustomerIDSave") == "")
{
stsMsg = customerObj.insertCustomer(request.getParameter("customerCode"),
request.getParameter("customerName"),
request.getParameter("customerAge"),
request.getParameter("customerAddress"));
}
else//Update----------------------
{
stsMsg = customerObj.updateCustomer(request.getParameter("hidCustomerIDSave"),
request.getParameter("customerCode"),
request.getParameter("customerName"),
request.getParameter("customerAge"),
request.getParameter("customerAddress"));
}
session.setAttribute("statusMsg", stsMsg);
}
//Delete-----------------------------
if (request.getParameter("hidCustomerIDDelete") != null)
{
Customer customerObj = new Customer();
String stsMsg =
customerObj.deleteCustomer(request.getParameter("hidCustomerIDDelete"));
session.setAttribute("statusMsg", stsMsg);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="Views/bootstrap.min.css">

<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/customers.js"></script>
<title>Customers Management</title>
</head>
<body>
<h1>Customers Management</h1>

<form id="formCustomer" name="formCustomer" method="post" action="customers.jsp">
 Customer code:
<input id="customerCode" name="customerCode" type="text"
 class="form-control form-control-sm">
<br> Customer name:
<input id="customerName" name="customerName" type="text"
 class="form-control form-control-sm">
<br> Customer age:
<input id="customerAge" name="customerAge" type="text"
 class="form-control form-control-sm">
<br> Customer address:
<input id="customerAddress" name="customerAddress" type="text"
 class="form-control form-control-sm">
<br>
<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
<input type="hidden" id="hidCustomerIDSave" name="hidCustomerIDSave" value="">
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<%
	out.print(session.getAttribute("statusMsg"));
%>
<br>
<div id="divCustomersGrid">
<%
	Customer customerObj = new Customer();
 out.print(customerObj.readCustomers());
%>
</div>
</body>
</html>