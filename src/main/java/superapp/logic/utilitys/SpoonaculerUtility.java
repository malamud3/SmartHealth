package superapp.logic.utilitys;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;




import superapp.data.subEntity.IngredientEntity;
import superapp.logic.service.SpoonaculerService;

@Service
public class SpoonaculerUtility implements SpoonaculerService{

	//private ObjectMapper jackson; //add if needed
	private RestTemplate restTemplate;


	private String API_KEY;
	private String API_BASE_URL;

	public SpoonaculerUtility() {
		//TODO - find a way to inject the API key
		API_KEY = "4484a885c6ed4e6281e94718c241a20e";

		API_BASE_URL = "https://api.spoonacular.com";
		//this.jackson = new ObjectMapper();
		this.restTemplate = new RestTemplate();
	}

	@Override
	public IngredientEntity getIngredientDataByName(String ingredientName) {
		String resultJSON = this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/search" +
						"?query=" + ingredientName + "&apiKey=" + API_KEY, String.class);

		Integer id = getIdOfTheFirstResult(resultJSON);
		System.err.println(resultJSON);
		//100 grams by default
		return getIngredientDataByIdAndAmountInGrams(id, 100);
	}

	@Override
	public IngredientEntity getIngredientDataByName(String ingredientName, int amountInGrams) {
		String resultJSON = this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/search" +
						"?query=" + ingredientName + "&apiKey=" + API_KEY, String.class);

		Integer id = getIdOfTheFirstResult(resultJSON);
		System.err.println(resultJSON);
		return getIngredientDataByIdAndAmountInGrams(id, 100);
	}


	@Override
	public IngredientEntity getIngredientDataByIdAndAmountInGrams(Integer id, int amountInGrams) {
		System.err.println(API_BASE_URL + "/food/ingredients/{id}/information" + "?apiKey=" + API_KEY 
				+ "&amount=" + amountInGrams +"&unit=grams");
		System.err.println(this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/{id}/information" + "?apiKey=" + API_KEY + "&amount=" + amountInGrams +"&unit=grams",
						String.class, id));
		return this.restTemplate
				.getForObject(API_BASE_URL + "/food/ingredients/{id}/information" + "?apiKey=" + API_KEY + "&amount=" + amountInGrams +"&unit=grams",
						IngredientEntity.class, id);
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
