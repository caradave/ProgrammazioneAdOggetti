package diet;

class PackagedProducts implements NutritionalElement{
	String name;
	double calories,  proteins, carbs, fat;
	boolean per100g = false;

	PackagedProducts(String name, double calories, double proteins, double carbs, double fat) {
		this.calories = calories;
		this.carbs = carbs;
		this.fat = fat;
		this.name = name;
		this.proteins = proteins;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getCalories() {
		return this.calories;
	}

	@Override
	public double getProteins() {
		return this.proteins;
	}

	@Override
	public double getCarbs() {
		return this.carbs;
	}

	@Override
	public double getFat() {
		return this.fat;
	}

	@Override
	public boolean per100g() {
		return this.per100g;
	}
	
	
}
