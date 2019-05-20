package api;

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

public class JsonMethods {
	public static JSONObject newJSON() {
		// Create a JSON file
		Map<String, String> map = new HashMap<>();
		map.put("name", "jon doe");
		map.put("age", "22");
		map.put("city", "chicago");
		JSONObject jo = new JSONObject(map);
		return jo;
	}
	
	public static JSONArray getJsonArrayFromFile(String filename) throws Exception{
		String fileData = readFileAsString(filename);
		//System.out.println(fileData);
		JSONArray fileJSONArray = new JSONArray(fileData);
		//Object item = fileJSONArray.get(0);		
		//System.out.println(item.getClass() + ": " + item.toString());
		//JSONObject jsonObj = (JSONObject) item;
		//System.out.println("Student ID: " + jsonObj.get("StudentID"));
		return fileJSONArray;
	}
	
	// https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
	public static String readFileAsString(String fileName)throws Exception 
	  { 
	    String data = ""; 
	    data = new String(Files.readAllBytes(Paths.get(fileName))); 
	    return data; 
	  } 
	
	// https://www.journaldev.com/878/java-write-to-file
	public static void writeUsingFileWriter(String filename, String data) {
        File file = new File(filename);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	
	//https://stackoverflow.com/questions/33769455/java-spark-framework-use-straight-html-template
	public static String renderContent(String htmlFile) {
	    try {
	        // If you are using maven then your files
	        // will be in a folder called resources.
	        // getResource() gets that folder
	        // and any files you specify.
	        //URL url = getClass().getResource(htmlFile);
	    	
	    	//https://stackoverflow.com/questions/8275499/how-to-call-getclass-from-a-static-method-in-java
	    	URL url = Main.class.getResource(htmlFile);
	    	
	        // Return a String which has all
	        // the contents of the file.
	        Path path = Paths.get(url.toURI());
	        return new String(Files.readAllBytes(path), Charset.defaultCharset());
	    } catch (IOException | URISyntaxException e) {
	        // Add your own exception handlers here.
	    }
	    return "Error!";
	}	
}
