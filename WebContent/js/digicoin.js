(function() {
			$( "#dialog" ).dialog({
		        autoOpen: false, 
		        buttons: {
		           OK: function() {$(this).dialog("close");}
		        },
		        title: "InvestoBank DigiCoin Report",
		        position: {
		        	my: 'center',
		            at: 'top',
		        },
		        width: "50%",
		        maxWidth: "768px"
		     });
			$("#orderBtn").click(function() {
                $.ajax({
                    url : "digicoin",
                    type: "post",
                    data: {
                    	action: "order",
                    	client: $("#client").val(),
                    	operation: $("#operation").val(),
                    	amount: $("#amount").val(),
                    },                  
                    dataType: "text",                   
                    success: successOrder,
                    error: error
                });
            });
            
            $("#reportBtn").click(function() {
                $.ajax({
                    url : "digicoin",
                    type: "post",
                    data: {
                    	action: "report",
                    },                  
                    dataType: "text",                   
                    success: successReport,
                    error: error
                });
            });

            function successOrder(data, textStatus, jqXHR)
            {
            	var s = "SUCCESS";
            	$("#result").removeClass();
            	
            	if(data.indexOf(s) > -1){
            		$("#result").addClass("alert alert-success").html(data);
            		$("#result").fadeIn("fast");
            	}else{
            		$("#result").addClass("alert alert-danger").html(data);
            		$("#result").fadeIn("fast");
            	}
            	$("#amount").val("");
            	setTimeout(function(){ $("#result").fadeOut("fast") }, 4000);
            	
            }
            
            function successReport(data, textStatus, jqXHR)
            {
            	$("#dialog").dialog("open");
            	$("#dialog").html(data);
            	
            }

            function error(jqXHR, textStatus, errorThrown)
            {
                alert("Error: "+textStatus);
            }
            
            
})();