package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut
 * 
 * It includes a name, optional altitude, category,
 * number of beds and location municipality.
 *  
 *
 */
public class MountainHut {
	
	String hutName, hutCategory;
	Integer hutAltitude = null, hutBedsNum;
	Municipality hutMunicipality;
	
	public MountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		this.hutName = name;
		this.hutAltitude = altitude;
		this.hutCategory = category;
		this.hutBedsNum = bedsNumber;
		this.hutMunicipality = municipality;
	}

	public MountainHut(String name, String category, Integer bedsNumber, Municipality municipality) {
		this.hutName = name;
		this.hutCategory = category;
		this.hutBedsNum = bedsNumber;
		this.hutMunicipality = municipality;
	}
	
	public String getName() {
		return this.hutName;
	}

	public Optional<Integer> getAltitude() {
		return Optional.ofNullable(this.hutAltitude);
	}

	public String getCategory() {
		return this.hutCategory;
	}

	public Integer getBedsNumber() {
		return this.hutBedsNum;
	}

	public Municipality getMunicipality() {
		return this.hutMunicipality;
	}
}
