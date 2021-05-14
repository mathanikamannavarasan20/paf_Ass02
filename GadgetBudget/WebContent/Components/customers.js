/**
 * 
 */

$(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
 {
 $("#alertSuccess").hide();
 }
 $("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
// Clear alerts---------------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();
// Form validation-------------------
var status = validateCustomerForm();
if (status != true)
 {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }
// If valid------------------------
var type = ($("#hidCustomerIDSave").val() == "") ? "POST" : "PUT";
 $.ajax(
 {
 url : "CustomersAPI",
 type : type,
 data : $("#formCustomer").serialize(),
 dataType : "text",
 complete : function(response, status)
 {
  location.reload(true);
 onCustomerSaveComplete(response.responseText, status);

 }
 }); 
});
// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
$("#hidCustomerIDSave").val($(this).data("customerid"));
 $("#customerCode").val($(this).closest("tr").find('td:eq(0)').text());
 $("#customerName").val($(this).closest("tr").find('td:eq(1)').text());
 $("#customerAge").val($(this).closest("tr").find('td:eq(2)').text());
 $("#customerAddress").val($(this).closest("tr").find('td:eq(3)').text());
});

$(document).on("click", ".btnRemove", function(event)
{
 $.ajax(
 {
 url : "CustomersAPI",
 type : "DELETE",
 data : "customerID=" + $(this).data("customerid"),
 dataType : "text",
 complete : function(response, status)
 {

  location.reload(true);
 onItemDeleteComplete(response.responseText, status);

 }
 });
});

// CLIENT-MODEL================================================================
function validateCustomerForm()
{
// CODE
if ($("#customerCode").val().trim() == "")
 {
 return "Insert Customer Code.";
 }
// NAME
if ($("#customerName").val().trim() == "")
 {
 return "Insert Customer Name.";
 } 

// PRICE-------------------------------
if ($("#customerAge").val().trim() == "")
 {
 return "Insert Customer Age.";
 }
// is numerical value
var tmpAge = $("#customerAge").val().trim();
if (!$.isNumeric(tmpAge))
 {
 return "Insert a numerical value for Customer Age.";
 }
// convert to decimal price
 $("#customerAge").val(parseFloat(tmpAge).toFixed(2));
// DESCRIPTION------------------------
if ($("#customerAddress").val().trim() == "")
 {
 return "Insert Customer Address.";
 }
return true;
}

function onCustomerSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divCustomersGrid").html(resultSet.data);

 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while saving.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while saving..");
 $("#alertError").show();
 } 

 $("#hidCustomerIDSave").val("");
 $("#formCustomer")[0].reset();
}

function onCustomerDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divCustomersGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while deleting.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while deleting..");
 $("#alertError").show();
 }
}   
 