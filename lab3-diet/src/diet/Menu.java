package diet;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	String name;
	HashMap<String, NutritionalElement> valueRecipeMap = new HashMap<>();
	HashMap<String, NutritionalElement> valueProductMap = new HashMap<>();
	HashMap<String, Double> recipeMap = new HashMap<>();
	HashSet<String> productMap = new HashSet<>();
	
	public Menu(String name, HashMap<String, NutritionalElement> recipeMap, HashMap<String, NutritionalElement> productMap) {
		this.name = name;
		this.valueProductMap = productMap;
		this.valueRecipeMap = recipeMap;
	}
	
	/**
	 * Adds a given serving size of a recipe.
	 * The recipe is a name of a recipe defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
    public Menu addRecipe(String recipe, double quantity) {
    	this.recipeMap.put(recipe, quantity);
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
    public Menu addProduct(String product) {
    	this.productMap.add(product);
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		double tot=0;
		
		for(String name: productMap) {
			tot += this.valueProductMap.get(name).getCalories();
		}
		
		for(String name: this.recipeMap.keySet()) {
			tot += this.valueRecipeMap.get(name).getCalories()*this.recipeMap.get(name) / 100;
		}
		return tot;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		double tot=0;
		
		for(String name: productMap) {
			tot += this.valueProductMap.get(name).getProteins();
		}
		
		for(String name: this.recipeMap.keySet()) {
			tot += this.valueRecipeMap.get(name).getProteins()*this.recipeMap.get(name) / 100;
		}
		return tot;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		double tot=0;
		
		for(String name: productMap) {
			tot += this.valueProductMap.get(name).getCarbs();
		}
		
		for(String name: this.recipeMap.keySet()) {
			tot += this.valueRecipeMap.get(name).getCarbs()*this.recipeMap.get(name) / 100;
		}
		return tot;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		double tot=0;
		
		for(String name: productMap) {
			tot += this.valueProductMap.get(name).getFat();
		}
		
		for(String name: this.recipeMap.keySet()) {
			tot += this.valueRecipeMap.get(name).getFat()*this.recipeMap.get(name) / 100;
		}
		return tot;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return false;
	}
}