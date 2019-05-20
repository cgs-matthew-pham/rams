package rfid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpMethods {
	
	// https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
	
	public static String sendGet(String url, String user_agent) throws Exception {
		
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", user_agent);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

		
		return response.toString();
	}

	// HTTP POST request
	// https://www.baeldung.com/httpurlconnection-post
	public static String sendPost(String url, String user_agent, String jsonInputString) throws Exception {

		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", user_agent);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		
		
		// Need this later when actually getting a proper response to POST
		con.setRequestProperty("Accept", "application/json");

		
		
		// Ensure content
		con.setDoOutput(true);
		
		// Write Request Body
		OutputStream os = con.getOutputStream();
		byte[] input = jsonInputString.getBytes("utf-8");
		os.write(input, 0, input.length); 
		
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());
		
		return response.toString();

	}
	

	public static void sendPostHttpClient(String url, String json) 
			  throws ClientProtocolException, IOException, Exception {
			    CloseableHttpClient client = HttpClients.createDefault();
			    HttpPost httpPost = new HttpPost(url);
			 
			    //String json = "{\"id\":1,\"name\":\"John\"}";
			    StringEntity entity = new StringEntity(json);
			    httpPost.setEntity(entity);
			    httpPost.setHeader("Accept", "application/json");
			    httpPost.setHeader("Content-type", "application/json");
			 
			    CloseableHttpResponse response = client.execute(httpPost);
			    
			    //assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
			    if (response.getStatusLine().getStatusCode() != 200) {
			    	throw new Exception("Non 200 status code.");
			    }
			    
			    client.close();
			}
			
	
}
