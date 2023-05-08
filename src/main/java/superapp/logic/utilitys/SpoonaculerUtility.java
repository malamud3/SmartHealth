package superapp.logic.utilitys;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import superapp.data.subEntity.IngridientEntity;

public class SpoonaculerUtility {
	private static SpoonaculerUtility instance;
	
	private String API_KEY;
	private String API_URL;
	
	private SpoonaculerUtility() {
		//TODO - find a way to inject the values
		API_KEY = "4484a885c6ed4e6281e94718c241a20e";
		API_URL = "https://api.spoonacular.com/food/ingredients";
	}
	public static SpoonaculerUtility getInstance() {
		if (instance == null) {
            instance = new SpoonaculerUtility();
        }
        return instance;
	}
	
	public IngridientEntity getIngredientDataByName(String ingredientName) throws IOException {
	    String urlType = "/search";
	    String resultJSON = getDataFromAPI(urlType, "&query=" + ingredientName);
	    Integer id = getIdOfTheFirstResult(resultJSON);
	    System.err.println(id);
	    return getIngredientDataById(id);
	}
	
	public IngridientEntity getIngredientDataById(Integer id) throws IOException {
		Gson gson = new Gson();
		String ingredientJSON = getDataFromAPI("/" + id.toString() + "/information", "");
		 System.err.println(ingredientJSON);
		return gson.fromJson(ingredientJSON, IngridientEntity.class);
	}
	
	
	private String getDataFromAPI(String urlType, String urlParam) throws IOException {
		//urlParam can be empty if it not needed
		OkHttpClient client = new OkHttpClient();
		

		
		String urlWithParams = API_URL + urlType + "?apiKey=" + API_KEY  + urlParam;

        // Create the HTTP request object
        Request request = new Request.Builder()
                .url(urlWithParams)
                .build();
        
        // Send the HTTP request and get the response
        Response response = client.newCall(request).execute();

        // Get the response body as a string
        return response.body().string();
		
	}

	
	private int getIdOfTheFirstResult (String result) {
		//can add an index of a result
		
		JSONObject jsonObject = new JSONObject(result);
		
        JSONArray resultsArray = jsonObject.getJSONArray("results");
        
        if (resultsArray.length() > 0) {
            JSONObject firstResult = resultsArray.getJSONObject(0);
            int id = firstResult.getInt("id");
            return id;
        }
        return -1;//can change to throw a new resultemptyException
	}
	
}
