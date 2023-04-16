package SuperApp.data;

import java.util.*;

public class RecipeEntity {
	private UUID r_id;
	private String name;
	
	private HashMap<IngridientEntity, AmountEntity> ingridients;
	
	//private List<GenreEntity> genres; - Genre deleted

	public RecipeEntity() {
		super();
	}

	public UUID getR_id() {
		return r_id;
	}

	public void setR_id(UUID r_id) {
		this.r_id = r_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<IngridientEntity, AmountEntity> getIngridients() {
		return ingridients;
	}

	public void setIngridients(HashMap<IngridientEntity, AmountEntity> ingridients) {
		this.ingridients = ingridients;
	}

	
	//Genre deleted
//	public List<GenreEntity> getGenres() {
//		return genres;
//	}
//
//	public void setGenres(List<GenreEntity> genres) {
//		this.genres = genres;
//	}	
	
	
	

}
