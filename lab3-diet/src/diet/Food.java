package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;



/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food {
	
	HashMap<String, NutritionalElement> materialMap = new HashMap<>(); 
	HashMap<String, NutritionalElement> productMap = new HashMap<>();
	HashMap<String, NutritionalElement> recipesMap = new HashMap<>();

	/**
	 * Define a new raw material.
	 * The nutritional values are specified for a conventional 100g quantity
	 * @param name unique name of the raw material
	 * @param calories calories per 100g
	 * @param proteins proteins per 100g
	 * @param carbs carbs per 100g
	 * @param fat fats per 100g
	 */
	
	public void defineRawMaterial(String name, double calories, double proteins, double carbs, double fat) {
		this.materialMap.put(name, new RawMaterial(name, calories, proteins, carbs, fat));
	}

	/**
	 * Retrieves the collection of all defined raw materials
	 * @return collection of raw materials though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> rawMaterials() {
		Collection<NutritionalElement> materialList = new ArrayList<>(this.materialMap.values());
		List<NutritionalElement> sortedList = materialList.stream().sorted((a, b) -> a.getName().compareTo(b.getName())).collect(Collectors.toList());
		return sortedList;
	}
	
	/**
	 * Retrieves a specific raw material, given its name
	 * @param name  name of the raw material
	 * @return  a raw material though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRawMaterial(String name) {
		return this.materialMap.get(name);
	}

	/**
	 * Define a new packaged product.
	 * The nutritional values are specified for a unit of the product
	 * @param name unique name of the product
	 * @param calories calories for a product unit
	 * @param proteins proteins for a product unit
	 * @param carbs carbs for a product unit
	 * @param fat fats for a product unit
	 */
	public void defineProduct(String name, double calories, double proteins, double carbs, double fat) {
		this.productMap.put(name, new PackagedProducts(name, calories, proteins, carbs, fat));
		
	}

	/**
	 * Retrieves the collection of all defined products
	 * @return collection of products though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> products() {
		Collection<NutritionalElement> productList = new ArrayList<>(this.productMap.values());
		List<NutritionalElement> sortedList = productList.stream().sorted((a, b)-> a.getName().compareTo(b.getName())).collect(Collectors.toList());
		return sortedList;
	}

	/**
	 * Retrieves a specific product, given its name
	 * @param name  name of the product
	 * @return  a product though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getProduct(String name) {
		return productMap.get(name);
	}

	/**
	 * Creates a new recipe stored in this Food container.
	 *  
	 * @param name name of the recipe
	 * @return the newly created Recipe object
	 */
	public Recipe createRecipe(String name) {
		this.recipesMap.put(name, new Recipe(name, this.materialMap));
		return (Recipe)this.recipesMap.get(name);
	}
	
	/**
	 * Retrieves the collection of all defined recipes in alphabetic order
	 * @return collection of recipes though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> recipes() {
		Collection<NutritionalElement> recipeCollection = new ArrayList<>(this.recipesMap.values());
		List<NutritionalElement> sortedList = recipeCollection.stream().sorted((a, b)-> a.getName().compareTo(b.getName())).collect(Collectors.toList());
		return sortedList;
	}

	/**
	 * Retrieves a specific recipe, given its name
	 * @param name  name of the recipe
	 * @return  a recipe though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRecipe(String name) {
		return this.recipesMap.get(name);
	}

	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu menu = new Menu(name, this.recipesMap, this.productMap);
		return menu;
	}
}