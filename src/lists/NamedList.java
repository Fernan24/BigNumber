package lists;

import java.util.ArrayList;

public class NamedList<T> extends ArrayList<T> {

	private String name; 
	public NamedList(String name) { 
		this.name = name; 
	}
	
	public String getName() {
		return name;
	}
	
}
