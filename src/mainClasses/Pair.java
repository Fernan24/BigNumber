package mainClasses;

public class Pair {
	private Numbers n;
	private String varname;
	public Pair(Numbers n, String varname) {
		super();
		this.n = n;
		this.varname = varname;
	}
	public Numbers getNumbers() {
		return n;
	}
	public void setNumbers(Numbers n) {
		this.n = n;
	}
	public String getName() {
		return varname;
	}
	public void setName(String varname) {
		this.varname = varname;
	}
	public String toString(){
		String str = "";
		str+= this.getName();
		str += " = ";
		str+= this.n.toString();
		return str;
	}
	

}
