package com.investobank.digicoin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.investobank.digicoin.controller.DigicoinController;
import com.investobank.digicoin.model.Transaction;

/**
 * Servlet implementation class DigicoinServlet
 */
public class DigicoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DigicoinController controller = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DigicoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/*
		 * "action" parameter is used for to catch user operation wants to be performed
		 * There are 4 types of actions that user can perform:
		 *  - Order digicoin (buy or sell)
		 *  - Report Request to see overall statistics
		 *  - Add a new client
		 *  - Add a new broker
		 */
		String action = (String) request.getParameter("action");
		
		/*
		 * "action = null" means, it is the first time user visit the app. 
		 * No any button pressed and there are no any user action to be performed.
		 * So, app initialization will be performed here. 
		 * 
		 *  - DigicoinController class will be initialized.
		 *  - 3 clients will be created by default: Client A, Client B, Client C
		 *  - 2 brokers will be created by default: Broker 1, Broker 2
		 *  - Client and Broker preferences,  will be set and controlled by the controller automatically
		 */
		if(action == null || action.equals("")){
			this.controller = new DigicoinController();
			request.getRequestDispatcher("/view/digicoin.jsp").forward(request, response);
		}
		
		/*
		 * Order operations handled here
		 * There are two main returns from server. 
		 * 1. SUCCESS (returns TransactionID and totalCost)
		 * 2. FAILURE (return failure message for the user)
		 */
		else if (action.equalsIgnoreCase("order")){
			
			String client = (String) request.getParameter("client");
			String operation = (String) request.getParameter("operation");
			String amount = (String) request.getParameter("amount");
			PrintWriter out = response.getWriter();
			try {
				String result = this.controller.processOrder(client, operation, amount);
				String[] result_arr = result.split("-");
				out.println("SUCCESS: " + client + " " + operation.trim() + "s " + amount + " digicoins at " +  result_arr[1] + "\n"
						+ "Transaction #" + result_arr[0] + " has ben executed.");
			} catch (Exception e) {
				
				out.println("ERROR: " + e.getMessage());
			}
		}
		/*
		 * This part populates teh html code for reporting
		 * Report includes: 
		 * 	- Total costs executed by Clients
		 *  - Total served digicoins by Brokers
		 *  - All transactions listing for the user
		 */
		else if (action.equalsIgnoreCase("report")){
			PrintWriter out = response.getWriter();
			HashMap<String,String> clientReport = this.controller.getReportByClient();

			out.println("<div class='row'>");
			out.println("<div class='col-md-6'>");
			out.println("<div class='panel panel-default'>");
			out.println("<div class='panel-heading'>Clients</div>");
			out.println("<div class='panel-body'> Client A: "+ clientReport.get("Client A") 
												+"<br> Client B: " + clientReport.get("Client B")
												+"<br> Client C: " + clientReport.get("Client C") 
												+ "</div>");
			out.println("</div>"); //panel
			out.println("</div>"); //col
			
			HashMap<String,String> brokerReport = this.controller.getReportByBroker();
			
			out.println("<div class='col-md-6'>");
			out.println("<div class='panel panel-default'>");
			out.println("<div class='panel-heading'>Brokers</div>");
			out.println("<div class='panel-body'> Broker 1: "+ brokerReport.get("Broker 1") 
												+"<br> Broker 2: " + brokerReport.get("Broker 2")
												+ "<br/>&nbsp;</div>");
			out.println("</div>"); //panel
			out.println("</div>"); //col
			
			out.println("</div>"); //row
			ArrayList<Transaction> report = this.controller.getReport();
			Iterator<Transaction> it_report = report.iterator();
			
			out.println("<div class='row'>");
			
			out.println("<div class='panel panel-default'>");
			out.println("<div class='panel-heading'>Transactions</div>");
			out.println("<div class='panel-body'> ");
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
			out.println("<table class='table table-striped'>");
			out.println("<thead><tr>"
				          +"<th>#Id</th>"
				          +"<th>Client Name</th>"
				          +"<th>Operation</th>"
				          +"<th>Amount</th>"
				          +"<th>Cost</th>"
				          +"<th>Time</th>"
				        +"</tr></thead><tbody>");
						
			while(it_report.hasNext()){
				Transaction transaction = it_report.next();
				out.println("<tr>");
				out.println("<td>" + transaction.getId() + "</td><td>" 
						+ transaction.getOrder().getClient().getName() + "</td><td>"
						+ transaction.getOrder().getOperation()  + "</td><td>"
						+ transaction.getOrder().getAmount() + "</td><td>"
						+ transaction.getCost().toString() + "</td><td>"
						+ sdf.format(transaction.getTime()) + "</td>");
				out.println("</tr>");
			}
			
			out.println("</tbody></table>"); //table
			out.println("</div></div>");
			out.println("</div>"); //row
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
