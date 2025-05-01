package hydraulic;

/**
 * Main class that acts as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	Element[] listElement = new Element[100];
	int numElement = 0;

// R1
	/**
	 * Adds a new element to the system
	 * @param elem the new element to be added to the system
	 */
	public void addElement(Element elem){
		this.listElement[numElement] = elem;
		this.numElement++;
		//TODO: to be implemented
	}
	
	/**
	 * returns the element added so far to the system
	 * @return an array of elements whose length is equal to 
	 * 							the number of added elements
	 */
	public Element[] getElements(){
		Element[] list = new Element[numElement];
		for(int i=0; i<numElement; i++) {
			list[i] = this.listElement[i];
		}
		return list;
	}

// R4
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		for(int i=0; i<numElement; i++) {
			if(this.listElement[i] instanceof Source) {
				/*Element elem;
				elem = this.listElement[i];
				while(!(elem instanceof Sink)) {
					
				}*/
			}
		}
	}
	
	private void next_element(Element elem, double sourceflow) {
		if(!(elem instanceof Split) && elem  instanceof Tap) {
			
		}
	}

// R6
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		//TODO: to be implemented
		return null;
	}

// R7
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public boolean deleteElement(String name) {
		//TODO: to be implemented
		return false;
	}

// R8
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		//TODO: to be implemented
	}
}
