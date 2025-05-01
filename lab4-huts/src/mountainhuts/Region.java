package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	String regionName;
	ArrayList<Altitude> altitudeList = new ArrayList<>();
	HashMap<String, Municipality> munNameMap = new HashMap<>();
	HashMap<String , MountainHut> hutNameMap = new HashMap<>();
	
	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.regionName = name;
	}

	
	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.regionName;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for(String s : ranges) {
			String[] k = s.split("-");
			this.altitudeList.add(new Altitude(Integer.parseInt(k[0]), Integer.parseInt(k[1])));
		}

	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		Altitude al = new Altitude(0, altitude);
		return al.interval(this.altitudeList);
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return this.munNameMap.values();
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return this.hutNameMap.values();
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		
		if(this.munNameMap.get(name) == null) {
			this.munNameMap.put(name, new  Municipality(name, province, altitude));
			return this.munNameMap.get(name);
		}
		return this.munNameMap.get(name);
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		if(this.hutNameMap.get(name) == null) {
			this.hutNameMap.put(name, new MountainHut(name, null, category, bedsNumber, municipality));
			return this.hutNameMap.get(name);
		}
		return this.hutNameMap.get(name);
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		if(this.hutNameMap.get(name) == null) {
			this.hutNameMap.put(name, new MountainHut(name, altitude, category, bedsNumber, municipality));
			return this.hutNameMap.get(name);
		}
		return this.hutNameMap.get(name);
	}

	/**
	 * Creates a new region and loads its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region Regione = new Region(name);
		
		List<String> lines = readData(file);
		lines.remove(0);
		for(String line:lines) {
			String[] data = line.split(";");
			Regione.createOrGetMunicipality(data[1], data[0], Integer.parseInt(data[2]));
			if(data[4] != "") {
				Regione.createOrGetMountainHut(data[3], Integer.parseInt(data[4]), data[5], Integer.parseInt(data[6]), new Municipality(data[0], data[1], Integer.parseInt(data[2])));
			}else {
				Regione.createOrGetMountainHut(data[3], data[5], Integer.parseInt(data[6]), new Municipality(data[0], data[1], Integer.parseInt(data[2])));
			}
		}
		return Regione;
	}

	/**
	 * Reads the lines of a text file.
	 *
	 * @param file path of the file
	 * @return a list with one element per line
	 */
	public static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		Map<String, Long> map = new HashMap<>();
		
		map = this.munNameMap.values().stream().collect(Collectors.groupingBy(Municipality::getProvince, Collectors.counting()));
		
		return map;
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return this.hutNameMap.values().stream().collect(Collectors.groupingBy(x -> x.getMunicipality().getName(), Collectors.groupingBy(x -> x.getMunicipality().getProvince(), Collectors.counting())));
		
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		HashMap<String, Long> map = new HashMap<>();
		
		for(Altitude a:this.altitudeList) {
			map.put(a.getMin() + "-" + a.getMax(), (long) 0);
		}
		
		map.put("0-INF", null);
		
		for(MountainHut mh: this.hutNameMap.values()) {
			if(mh.getAltitude().isEmpty() == false) {
				Altitude fake = new Altitude(0, mh.getAltitude().get());
				if(map.get(fake.interval(altitudeList)) != null) {
					map.replace(fake.interval(altitudeList), map.get(fake.interval(altitudeList)) + Long.valueOf(1));
				}else {
					map.replace(fake.interval(altitudeList), Long.valueOf(1));
				}
			}else {
				Altitude fake = new Altitude(0, mh.getMunicipality().getAltitude());
				if(map.get(fake.interval(altitudeList)) != null) {
					map.replace(fake.interval(altitudeList), map.get(fake.interval(altitudeList)) + Long.valueOf(1));
				}else {
					map.replace(fake.interval(altitudeList), Long.valueOf(1));
				}
			}
		}
		return map;
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return this.hutNameMap.values().stream().collect(Collectors.groupingBy(x->x.getMunicipality().getName(), Collectors.summingInt(x->x.getBedsNumber())));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		HashMap<String, Optional<Integer>> map = new HashMap<>();
		
		for(Altitude a:this.altitudeList) {
			map.put(a.getMin() + "-" + a.getMax(), null);
		}
		
		map.put("0-INF", null);
		
		for(MountainHut mh: this.hutNameMap.values()) {
			if(mh.getAltitude().isEmpty() == false) {
				Altitude fake = new Altitude(0, mh.getAltitude().get());
				if(map.get(fake.interval(this.altitudeList)) != null) {
					if(map.get(fake.interval(this.altitudeList)).get() < mh.getBedsNumber()){
						map.replace(fake.interval(this.altitudeList), Optional.ofNullable(mh.getBedsNumber()));
					}
				}else {
					map.replace(fake.interval(this.altitudeList), Optional.ofNullable(mh.getBedsNumber()));					
				}
			}else {
				Altitude fake = new Altitude(0, mh.getMunicipality().getAltitude());
				if(map.get(fake.interval(this.altitudeList)) != null) {
					if(map.get(fake.interval(this.altitudeList)).get() < mh.getBedsNumber()) {
						map.replace(fake.interval(this.altitudeList), Optional.ofNullable(mh.getBedsNumber()));
					}
				}else {
					map.replace(fake.interval(this.altitudeList), Optional.ofNullable(mh.getBedsNumber()));
				}
			}
		}
		
		return map;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {

		Map<String, Long> res = this.hutNameMap.values().stream()
				.collect(Collectors.groupingBy(x->x.getMunicipality().getProvince(), TreeMap::new, Collectors.counting()));
		
		
		return res.entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getValue,
				Collectors.mapping(Map.Entry::getKey, toList())
				));
	}

}
