package api;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import spark.Spark;

public class Main {
	
    public static void main(String[] args) {
    	
    	// https://stackoverflow.com/questions/39260529/change-java-spark-port-number
    	port(2000);
    	
    	// Test github
    	
    	// https://stackoverflow.com/questions/28125886/where-to-put-static-files-for-spark-web-framework
    	Spark.staticFiles.location("/public");
    	
    	// Simple Route
    	get("/text", (req, res) -> "Hello World");
    	
    	// JSON Route
    	JSONObject example = JsonMethods.newJSON();
    	get("/json", (req, res) -> example);
    	
    	// JSON from file
    	try {
			JSONArray array = JsonMethods.getJsonArrayFromFile("test.json");
			get("/read_file", (req, res) -> array);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// Write example to JSON file
    	// Probably better to throw exception and handle in try/catch here
    	get("/write_file", (req, res) -> {
    		JsonMethods.writeUsingFileWriter("example.json", example.toString());
    		return "Success! Check your folder for an example.json file";
    	});
    	
    	
    	// https://stackoverflow.com/questions/36665610/how-to-parse-json-request-in-java-spark
    	post("/echo_post", "application/json", (req, res) -> {
    		try {
    			String data = req.body().toString();
    			
        		JSONObject obj = new JSONObject(data);
        		System.out.println(obj.toString());
        		return data;
    		} catch (Exception e) {
    			e.printStackTrace();
    			return "Error!";
    		}
    	});
    	
    	post("/post_new_roll", "application/json", (req, res) -> {
    		try {
    			// Read file into array
    			JSONArray array = JsonMethods.getJsonArrayFromFile("rolls.json");
    			System.out.println(array.toString());
    			
    			
    			// Get new object
    			String data = req.body().toString();
        		JSONObject obj = new JSONObject(data);
        		//System.out.println(obj.toString());
        		
        		// Push into array
        		array.put(obj);
        		System.out.println(array.toString());
        		
        		
        		// Write to file 
        		JsonMethods.writeUsingFileWriter("rolls.json", array.toString());
        		
        		return array.toString();
        		
    		} catch (Exception e) {
    			e.printStackTrace();
    			return "Error!";
    		}
    		
  
    	});
    	
    	
    	
    	
    	
    	// Render HTML Page from resources folder
    	get("/render_html", (req, res) -> JsonMethods.renderContent("/index.html"));
    	  	
    }
    
}

