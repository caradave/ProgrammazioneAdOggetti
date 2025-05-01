package mountainhuts;

import java.util.ArrayList;

public class Altitude {
	int minAlt, maxAlt;
	
	public Altitude(int min, int max){
		this.maxAlt = max;
		this.minAlt = min;
	}
	
	public int getMax() {
		return this.maxAlt;
	}
	
	public int getMin() {
		return this.minAlt;
	}
	
	public String interval(ArrayList <Altitude> altitudeList) {
		String k = "0-INF";
		for(Altitude a: altitudeList) {
			if(a.getMax() >= this.getMax() && a.getMin() < this.getMax()) {
				return a.getMin() + "-" + a.getMax();
			}
		}
		return k;
	}
	


}
