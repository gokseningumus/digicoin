<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>InvestoBank DigiCoin</title>
<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">

    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-5" aria-expanded="false"></button>
          <a class="navbar-brand" href="#">InvestoBank DigiCoin Exchange Center</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-5">
          <p class="navbar-text navbar-right">Hello, CitiUser!</p>
        </div>
      </div>
    </nav>
    
    <div class="panel panel-default">
	  <div class="panel-body">
	    <div class="page-header">
		    <h2>
	  			<span>Order</span>
	  			<button type="button" class='btn btn-success pull-right input-lg' name="reportBtn" value="report" id="reportBtn">Get Report!</button>
			</h2>
    	</div>
    	<div id="result"></div>
		<div class="panel-body">
		
		<form class="form-horizontal" action="digicoin" method="post" id="orderForm">
		
		  <div class="form-group">  
		    <div class="input-group">
		      <div class="input-group-addon">Select Client</div>
		      <select class="form-control input-lg" placeholder="Client" id="client" name="client">
				  <option value="Client A">  Client A  </option>
				  <option value="Client B">  Client B  </option>
				  <option value="Client C">  Client C  </option>
			  </select>
		    </div>    
		  </div>
		    
		  <div class="form-group">  
		    <div class="input-group">
		      <div class="input-group-addon">Select Operation</div>
		      <select class="form-control input-lg" placeholder="Operation" id="operation" name="operation">
				  <option value="Buy">  Buy  </option>
				  <option value="Sell">  Sell  </option>
			  </select>
		    </div>    
		  </div> 
		  
		  <div class="form-group">  
		    <div class="input-group">
		      <div class="input-group-addon">DigiCoin</div>
		      <input type="text" class="form-control input-lg" id="amount" placeholder="Amount" name ="amount">
		      <div class="input-group-addon">.00</div>
		    </div>    
		  </div>
		  
		  <button type="button" class="btn btn-primary input-lg btn-block" name="orderBtn" value="order" id="orderBtn">Order Now!</button>

		</form>
		<br>
		</div>
	  </div>
	</div>

</div>

<div id="dialog" title="Report">
</div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/digicoin.js"></script>
</body>
</html>