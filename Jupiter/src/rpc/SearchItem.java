package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.TicketMasterAPI;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		// Term can be empty or null.
		String term = request.getParameter("term");
		DBConnection connection = DBConnectionFactory.getConnection();
		String userId = request.getParameter("user_id");
		try {
			List<Item> items = connection.searchItems(lat, lon, term);
			Set<String> favoritedItemIds = connection.getFavoriteItemIds(userId);
		    JSONArray array = new JSONArray();
		    for (Item item : items) {
		    	JSONObject obj = item.toJSONObject();
				obj.put("favorite", favoritedItemIds.contains(item.getItemId()));
				array.put(obj);

		    }
		    RpcHelper.writeJsonArray(response, array);
		   		 
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    connection.close();
		}

//		TicketMasterAPI tmAPI = new TicketMasterAPI();
//		List<Item> items = tmAPI.search(lat,  lon, null);
//		
//		JSONArray array = new JSONArray();
//		for (Item item : items) {
//			array.put(item.toJSONObject());
//		}
//		RpcHelper.writeJsonArray(response, array);

		// TODO Auto-generated method stub
//		response.setContentType("text/html");
//		
//		PrintWriter out = response.getWriter();
//		
//		out.print("<html><body>");
//		out.print("<h1>Hello World</h1>");
//		out.print("</body></html>");
//
//		out.close();
		
//		response.setContentType("text/html");
//		
//		PrintWriter out = response.getWriter();
//		if (request.getParameter("username") != null) {
//			String username = request.getParameter("username");
//			out.print("<html><body>");
//			out.print("<h1>Hello " + username + "</h1>");
//			out.print("</body></html>");
//		} 
//
//		out.close();	
		
//		response.setContentType("application/json");
//
//		PrintWriter out = response.getWriter();
//		if (request.getParameter("username") != null) {
//			String username = request.getParameter("username");
//			JSONObject obj = new JSONObject();
//
//			try {
//				obj.put("username", username);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			out.print(obj);
//		}
//
//		out.close();	
		
		
		
//		JSONArray array = new JSONArray();
//		
//		try {
//			array.put(new JSONObject().put("username", "abcd"));
//			array.put(new JSONObject().put("username", "1234"));
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		RpcHelper.writeJsonArray(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
