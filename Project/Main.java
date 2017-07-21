import java.io.*;
import java.net.*;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

// This class sends a request to http://ip-api.com with the user's ip address and returns its location
public class Main {
   public static String getLocation(String targetURL, String urlParameters) {
  		HttpURLConnection con = null;

	  	try {
	    	// Establishes a connection
	    	URL url = new URL(targetURL + "/" + urlParameters);
	    	con = (HttpURLConnection) url.openConnection();

		    // Get a response  
	   		InputStream is = con.getInputStream();
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    	StringBuilder response = new StringBuilder();
	    	String line;
	    	while ((line = rd.readLine()) != null) {
	    		response.append(line);
	    		response.append('\n');
	    	}
	    	rd.close();
	    	return response.toString();
	  	}
	  	catch (Exception e) {
	    	e.printStackTrace();
	    	return null;
	  	}
	  	finally {
	    	if (con != null) {
	    	  con.disconnect();
	    	}
	  	}
	}

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/test", new MyHandler());
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
        	String ipAddress = t.getRemoteAddress().toString();
        	String location = getLocation("http://ip-api.com/line", ipAddress);

            t.sendResponseHeaders(200, location.length());
            OutputStream os = t.getResponseBody();
            os.write(location.getBytes());
            os.close();
        }
    }
}