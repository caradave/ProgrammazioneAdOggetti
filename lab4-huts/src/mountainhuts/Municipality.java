package mountainhuts;

import java.util.HashMap;

/**
 * Class representing a municipality that hosts a mountain hut.
 * It is a data class with getters for name, province, and altitude
 * 
 */
public class Municipality {
	
	String nameMun, provimceMun;
	int altitudeMun;
	HashMap<String, Municipality> nameMap = new HashMap<>();
	
	
	public Municipality(String name, String province, Integer altitude) {
		this.nameMun = name;
		this.altitudeMun = altitude;
		this.provimceMun = province;
	}
	

	public String getName() {
		return this.nameMun;
	}

	public String getProvince() {
		return this.provimceMun;
	}

	public Integer getAltitude() {
		return this.altitudeMun;
	}

}
