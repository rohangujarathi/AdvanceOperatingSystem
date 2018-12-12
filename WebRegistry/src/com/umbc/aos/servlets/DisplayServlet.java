package com.umbc.aos.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.umbc.aos.beans.Registry;
import com.umbc.aos.beans.WebServiceBean;

public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DisplayServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, List<WebServiceBean>> webServiceMap = Registry.getServiceMap();
		StringBuilder details = new StringBuilder();
		for(String x: webServiceMap.keySet()) {
			details.append("<tr>");
			details.append("<td>");
			details.append(x);
			details.append("</td>");
			details.append("<td>");
			for(WebServiceBean wb: webServiceMap.get(x)) {
				
				details.append(wb.getiPAddress()+"|");
				
			}
			details.append("</td>");
			details.append("</tr>");
		}
		
		String start = "<html><body>";
		String table = "<table><tr><td>Service</td><td>Sites</td>"+details+"</table>";
		String end = "</body></html>";
		response.getWriter().append(start).append(table).append(end);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}
