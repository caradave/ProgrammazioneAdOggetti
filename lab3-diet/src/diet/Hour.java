package diet;

import java.util.ArrayList;

public class Hour {
	int h, min;
	
	public Hour(int h, int min) {
		this.h = h;
		this.min = min;
	}
	
	public Hour(String h, String min) {
		this.h = Integer.parseInt(h);
		this.min = Integer.parseInt(min);
	}
	
	public boolean isOpen(ArrayList<Hour> openingHour) {
		if(openingHour.get(0).toString().compareTo(this.toString())<=0 && openingHour.get(1).toString().compareTo(this.toString())>0)
			return true;
		if(openingHour.size()>2)
			if(openingHour.get(2).toString().compareTo(this.toString())<=0 && openingHour.get(3).toString().compareTo(this.toString())>0)
				return true;
		return false;
	}
	
	public Hour nextOpeningHour(ArrayList<Hour> openingHour) {
		int n = 0;
		if(openingHour.get(1).toString().compareTo(this.toString()) < 0 && openingHour.get(2).toString().compareTo(this.toString()) > 0) {
			return openingHour.get(2);
		}else {
			return openingHour.get(0);
		}
	}
	
	@Override
	public String toString() {
		return this.min10(this.h)+":"+this.min10(min);
	}

	private String min10(int n) {
		if(n<10) {
			return "0" + n;
		}
	return "" + n;
	}
}
