package diet;

import java.util.HashMap;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	
	String name;
	HashMap<String, NutritionalElement> materialMap = new HashMap<>();
	HashMap<String, Double> ingridientMap = new HashMap<>();
	/**
	 * Adds the given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	
	public Recipe(String name, HashMap<String, NutritionalElement> materialmap){
		this.name = name;
		this.materialMap = materialmap;
	}
	
	public Recipe addIngredient(String material, double quantity) {
		this.ingridientMap.put(material, quantity);
		return this;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	
	@Override
	public double getCalories() {
		double tot = 0;
		double mass = 0;
		for(String name:this.ingridientMap.keySet()) {
			mass += this.ingridientMap.get(name);
			tot += this.materialMap.get(name).getCalories()*this.ingridientMap.get(name);
		}
		return tot/mass;
	}
	

	@Override
	public double getProteins() {
		double tot = 0;
		double  mass = 0;
		for(String name:this.ingridientMap.keySet()) {
			mass += this.ingridientMap.get(name);
			tot += this.materialMap.get(name).getProteins()*this.ingridientMap.get(name);
		}
		return tot/mass;
	}

	@Override
	public double getCarbs() {
		double tot = 0;
		double mass = 0;
		for(String name:this.ingridientMap.keySet()) {
			mass += this.ingridientMap.get(name);
			tot += this.materialMap.get(name).getCarbs()*this.ingridientMap.get(name);
		}
		return tot/mass;
	}

	@Override
	public double getFat() {
		double tot = 0;
		double mass = 0;
		for(String name:this.ingridientMap.keySet()) {
			mass += this.ingridientMap.get(name);
			tot += this.materialMap.get(name).getFat()*this.ingridientMap.get(name);
		}
		return tot/mass;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
}
