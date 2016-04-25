package mainClasses;

import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.plaf.synth.SynthSpinnerUI;

/**
 * This class is Used to perform operations on numbers that exceed the max
 * capacity of primitive data types such as double and int.
 * 
 * @author fernando Rodriguez
 *
 */
public class Numbers implements Comparable<Numbers> {
	private char sign; // + or -
	private ArrayList<Integer> iPart; // numbers after the decimal period
	private ArrayList<Integer> fPart; // numbers after the decimal period

	/**
	 * Default constructor for Numbers, it converts a valid string into a
	 * representation of a number the number can only contain numbers, one
	 * decimal period and a positive or negative sign in front of the number and
	 * the rest must be numbers between 0 and 9
	 * 
	 * @param s
	 *            String representation of Number
	 * @throws IllegalArgumentException
	 *             if there are characters are not included in the description
	 *             above it will launch exception
	 */
	public Numbers(String s) throws IllegalArgumentException {
		boolean decimal = false;
		iPart = new ArrayList<>();
		fPart = new ArrayList<>();
		boolean decimalperiod = false;
		if (s.charAt(0) == '-') {
			sign = '-';
		} else {
			sign = '+';
		}

		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				if (s.charAt(i) == '.' && !decimalperiod) {
					if (decimalperiod)
						throw new IllegalArgumentException(
								"invalid number format (number already had a decimal period");
					decimal = true;
					decimalperiod = true;
				}
				if (!(s.charAt(i) == '.' || s.charAt(i) == '+' || s.charAt(i) == '-')) {
					throw new IllegalArgumentException("invalid character in number");
				}
				i = 1 + i;
			}

			if (!decimal) {
				iPart.add(Character.getNumericValue(s.charAt(i)));
			}
			if (decimal) {
				fPart.add(Character.getNumericValue(s.charAt(i)));
			}
		}
	}

	/**
	 * Constructor used to make new number in operations when parameters are
	 * already found and not in String representation
	 * 
	 * @param sign
	 *            Character that is only positive or negative
	 * @param whole
	 *            ArrayList of integers which holds the numbers that correspond
	 *            to the whole part of the number
	 * @param fraction
	 *            ArrayList of integers which holds the numbers that correspond
	 *            to the fractional part of the number
	 */
	public Numbers(char sign, ArrayList<Integer> whole, ArrayList<Integer> fraction) {
		this.sign = sign;
		this.iPart = whole;
		this.fPart = fraction;
	}

	/**
	 * Adds 2 numbers together
	 * 
	 * @param a
	 *            First Number to add to b
	 * @param b
	 *            Second number to add to a
	 * @return the result of the addition of a and b
	 */
	public static Numbers add(Numbers a, Numbers b) {
		int carry = 0;
		ArrayList<Integer> whole = new ArrayList<>();
		ArrayList<Integer> fraction = new ArrayList<>();
		// addition only happens if both numbers have equal sign
		// check if whole parts are equal in size
		if (a.iPart.size() != b.iPart.size()) {
			if (a.iPart.size() > b.iPart.size())
				b = addZeros(b, a.iPart.size() - b.iPart.size(), true);
			else
				a = addZeros(a, b.iPart.size() - a.iPart.size(), true);
		}
		// check if fraction parts are equal in size
		if (a.fPart.size() != b.fPart.size()) {
			if (a.fPart.size() > b.fPart.size())
				b = addZeros(b, a.fPart.size() - b.fPart.size(), false);
			else
				a = addZeros(a, b.fPart.size() - a.fPart.size(), false);
		}
		for (int i = a.fPart.size() - 1; i >= 0; i--) {
			int first = a.fPart.get(i);
			int second = b.fPart.get(i);
			int result = first + second + carry;
			if (result > 9) {
				// check if digit is more than 9
				result = result - 10;
				carry = 1;
			} else {
				carry = 0;
			}
			// add number to fraction part
			fraction.add(0, result);
		}
		for (int i = a.iPart.size() - 1; i >= 0; i--) {
			int first = a.iPart.get(i);
			int second = b.iPart.get(i);
			int result = first + second + carry;
			if (result > 9) {
				// check if digit is more than 9
				result = result - 10;
				carry = 1;
			} else {
				carry = 0;
			}
			// add number to fraction part
			whole.add(0, result);
		}
		if (carry == 1) {
			whole.add(0, 1);
		}

		return new Numbers(a.sign, whole, fraction);
	}

	/**
	 * This method will add leading or trailing zeroes to Number so that they
	 * are of the same size. The purpose is to simplify the addition
	 * 
	 * @param num
	 *            Number to add zeroes
	 * @param difference
	 *            Amount of zeros to add
	 * @param whole
	 *            True if the zeros to add will be in the whole part if in
	 *            decimal part false
	 * @return the new number with zeros
	 */
	private static Numbers addZeros(Numbers num, int difference, boolean whole) {
		if (whole) {
			for (int i = 0; i < difference; i++)
				num.iPart.add(0, 0);
		} else {
			for (int i = 0; i < difference; i++)
				num.fPart.add(0);
		}

		return num;
	}

	/**
	 * This method will subtract two Numbers a and b and return a Number object
	 * corresponding to the result
	 * 
	 * @param a
	 *            Number to be subtracted
	 * @param b
	 *            Number to subtract
	 * @return new number with the result of the subtraction
	 */
	public static Numbers subtract(Numbers a, Numbers b) {
		ArrayList<Integer> whole = new ArrayList<>();
		ArrayList<Integer> fraction = new ArrayList<>();
		boolean swapped = false;
		if (a.compareTo(b) > 0) {
			Numbers temp = a;
			a = b;
			b = temp;
			swapped = true;
		}
		// check if whole parts are equal in size
		if (a.iPart.size() != b.iPart.size()) {
			if (a.iPart.size() > b.iPart.size())
				b = addZeros(b, a.iPart.size() - b.iPart.size(), true);
			else
				a = addZeros(a, b.iPart.size() - a.iPart.size(), true);
		}
		// check if fraction parts are equal in size
		if (a.fPart.size() != b.fPart.size()) {
			if (a.fPart.size() > b.fPart.size())
				b = addZeros(b, a.fPart.size() - b.fPart.size(), false);
			else
				a = addZeros(a, b.fPart.size() - a.fPart.size(), false);
		}
		for (int i = a.fPart.size() - 1; i >= 0; i--) {

			if (a.fPart.get(i) < b.fPart.get(i)) {
				a.adjustnext(i, false);
			}
			int result = a.fPart.get(i) - b.fPart.get(i);
			fraction.add(0, result);
		}
		for (int i = a.iPart.size() - 1; i >= 0; i--) {

			if (a.iPart.get(i) < b.iPart.get(i)) {
				a.adjustnext(i, true);
			}
			int result = a.iPart.get(i) - b.iPart.get(i);
			whole.add(0, result);
		}
		char sign = a.sign;
		if (swapped)
			sign = '-';
		return new Numbers(sign, whole, fraction);
	}

	/**
	 * This method is used to change the next number in the list to one less so
	 * that ten can be added to the previous number to successfully complete the
	 * operation.
	 * 
	 * @param index
	 *            index of the number to be changed
	 * @param whole
	 *            true if editing the whole part of the list, false if the
	 *            decimal part.
	 * @param a
	 *            Number to compare
	 * @param b
	 *            Number to compare
	 */
	private void adjustnext(int index, boolean whole) {
		if (whole) {
			int temp = this.iPart.get(index - 1);
			temp = temp - 1;
			this.iPart.add(index, (this.iPart.remove(index) + 10));
			if (temp < 0) {
				adjustnext(index - 1, true);
			} else {
				this.iPart.remove(index - 1);
				this.iPart.add(index - 1, temp);
			}
		} else {
			int temp;
			if (index == 0) {
				temp = this.iPart.get(this.iPart.size() - 1);
				temp = temp - 1;
				this.fPart.add(0, (this.fPart.remove(0) + 10));
				this.iPart.remove(this.iPart.size() - 1);
				this.iPart.add(this.iPart.size(), temp);
			} else {
				temp = this.fPart.get(index - 1);

				temp = temp - 1;
				this.fPart.add(index, (this.fPart.remove(index) + 10));
				if (temp < 0) {
					adjustnext(index - 1, false);
				} else {
					this.fPart.remove(index - 1);
					this.fPart.add(index - 1, temp);
				}
			}

		}

	}

	/**
	 * This method is used to assign the correct sign to the result of the
	 * Operation and to choose the correct operation to perform
	 * 
	 * @param a
	 *            first Number
	 * @param b
	 *            second Number
	 * @param opp
	 *            operation being performed
	 * @return
	 */
	public static Numbers correctOperation(Numbers a, Numbers b, String opp) {
		Numbers res;
		char original1;
		char original2;
		if (opp.equals("add")) {
			if (a.sign == b.sign) {
				res = add(a, b);
				res.sign = a.sign;
				return res.trimZeros();
			}
			if (a.sign != b.sign) {
				original1 = a.sign;
				original2 = b.sign;
				a.sign = b.sign = '+';
				res = subtract(a, b);
				if (a.compareTo(b) < 0) {
					res.sign = original1;
				} else {
					res.sign = original2;
				}
				return res.trimZeros();
			}

		}
		if (opp.equals("sub")) {
			if (a.sign == '+' && b.sign == '+') {
				res = subtract(a, b);
				res.sign = '+';
				return res.trimZeros();
			}
			if (a.sign == '+' && b.sign == '-') {
				b.sign = '+';
				res = add(a, b);
				res.sign = '+';
				return res.trimZeros();

			}
			if (a.sign == '-' && b.sign == '-') {
				original1 = a.sign;
				original2 = b.sign;
				a.sign = b.sign = '+';
				res = add(a, b);
				if (a.compareTo(b) > 0) {
					res.sign = original1;
				} else {
					res.sign = original2;
				}
				return res.trimZeros();
			}
			if (a.sign == '-' && b.sign == '+') {
				original1 = a.sign;
				original2 = b.sign;
				a.sign = b.sign = '+';
				res = add(a, b);
				res.sign = '-';
				return res.trimZeros();
			}

		}
		if (opp.equals("mult")) {
			a.sign = b.sign = '+';
			res = multiply(a, b);
			if (a.sign == b.sign) {
				res.sign = '+';
			} else {
				res.sign = '-';
			}
			return res.trimZeros();
		}
		return null;
	}

	/**
	 * This method is used to remove zeros of a number in both the whole and
	 * fractional part
	 */
	private Numbers trimZeros() {
		boolean nonzero = false;
		int i = 0;
		while (!nonzero && i < this.iPart.size()) {
			if (this.iPart.get(i) == 0) {
				this.iPart.remove(i);
			} else {
				nonzero = true;
			}
			i++;
		}
		nonzero = false;
		i = this.fPart.size() - 1;
		while (!nonzero && i >= 0) {
			if (this.fPart.get(i) == 0) {
				this.fPart.remove(i);
			} else {
				nonzero = true;
			}
			i--;
		}
		return this;
	}

	public static Numbers multiply(Numbers a, Numbers b) {

		// check if whole parts are equal in size
		if (a.iPart.size() != b.iPart.size()) {
			if (a.iPart.size() > b.iPart.size())
				b = addZeros(b, a.iPart.size() - b.iPart.size(), true);
			else
				a = addZeros(a, b.iPart.size() - a.iPart.size(), true);
		}
		// check if fraction parts are equal in size
		if (a.fPart.size() != b.fPart.size()) {
			if (a.fPart.size() > b.fPart.size())
				b = addZeros(b, a.fPart.size() - b.fPart.size(), false);
			else
				a = addZeros(a, b.fPart.size() - a.fPart.size(), false);
		}
		ArrayList<ArrayList<Integer>> sumations = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> a1 = new ArrayList<>();
		ArrayList<Integer> b1 = new ArrayList<>();
		a1.addAll(a.iPart);
		a1.addAll(a.fPart);
		b1.addAll(b.iPart);
		b1.addAll(b.fPart);
		char sign;
		int carry = 0;
		int zeroes = 0;
		ArrayList<Integer> numbers;
		for (int i = b1.size() - 1; i >= 0; i--) {
			numbers = new ArrayList<>();
			for (int z = 0; z < zeroes; z++) {
				numbers.add(0, 0);
			}
			for (int j = a1.size() - 1; j >= 0; j--) {
				int result = a1.get(j) * b1.get(i) + carry;
				if (result > 9) {
					carry = Character.getNumericValue(Integer.toString(result).charAt(0));
					numbers.add(0, Character.getNumericValue(Integer.toString(result).charAt(1)));
				} else {
					carry = 0;
					numbers.add(0, result);
				}
			}
			numbers.add(0, carry);
			sumations.add(numbers);
			zeroes++;
		}
		ArrayList<Integer> fraction = new ArrayList<>();
		ArrayList<Integer> whole = new ArrayList<>();
		sign = '+';
		Numbers result = add(new Numbers(sign, sumations.get(0), fraction),
				new Numbers(sign, sumations.get(1), fraction));
		if (sumations.size() > 2) {
			for (int i = 2; i < sumations.size(); i++) {
				result = add(new Numbers(sign, sumations.get(i), fraction), result);
			}
		}
		int fractionSize = a.fPart.size() + b.fPart.size();

		int i = result.iPart.size() - 1;
		int count = 0;
		while (i >= 0) {
			if (count < fractionSize)
				fraction.add(0, result.iPart.get(i));
			else {
				whole.add(0, result.iPart.get(i));
			}
			count++;
			i--;

		}
		return new Numbers(sign, whole, fraction);
	}
	/**
	 * 
	 * @param n
	 * @return
	 */
	public static Numbers factorial(Numbers n) {
		Numbers num = new Numbers("1.0");
		Numbers result = num;
		while(num.compareTo(n)!=0){
			num =  increment(num);
			result = multiply(n,num);
		}
		
		return result;
	}
	private static Numbers increment(Numbers n){
		Numbers num = add(n, new Numbers("1.0"));
		return num;
	}

	public static Numbers validatePositiveInteger(String s) throws IllegalArgumentException {
		ArrayList<Integer> whole = new ArrayList<>();
		ArrayList<Integer> fraction = new ArrayList<>();
		char sign = '+';
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				if (i == 0) {
					if (s.charAt(i) != '+') {
						System.out.println("Invalid Input. Number must be a valid positive integer.");
						throw new IllegalArgumentException();
					}
				}else{
					System.out.println("Invalid Input. Number must be a valid positive integer.");
					throw new IllegalArgumentException();
				}
			}else{
				whole.add(Character.getNumericValue(s.charAt(i)));
			}

		}
		
		return new Numbers(sign,whole, fraction);
	}

	/**
	 * This method will return a string representation of the Number in question
	 */
	public String toString() {
		String str = "";
		if (this.sign == '-')
			str += sign;
		if (iPart.size() > 0) {
			for (Integer num : iPart) {
				str += num.toString();
			}
		} else {
			str += "0";
		}
		str += ".";
		if (fPart.size() > 0) {
			for (Integer num : fPart) {
				str += num.toString();
			}
		} else {
			str += "0";
		}

		return str;

	}

	@Override
	public int compareTo(Numbers o) {
		Numbers o1 = o;
		Numbers o2 = this;
		if (o1.sign == '+' && o2.sign == '+') {
			if (o1.iPart.size() != o2.iPart.size())
				return o1.iPart.size() - o2.iPart.size();
			else {
				for (int i = 0; i < iPart.size(); i++) {
					if (o1.iPart.get(i) > o2.iPart.get(i)) {
						return 1;
					}
					if (o1.iPart.get(i) < o2.iPart.get(i)) {
						return -1;
					}
				}
				for (int i = 0; i < fPart.size(); i++) {
					if (o1.fPart.get(i) > o2.fPart.get(i)) {
						return 1;
					}
					if (o1.fPart.get(i) < o2.fPart.get(i)) {
						return -1;
					}
				}
				return 0;
			}
		}
		if (o1.sign == '-' && o2.sign == '-') {
			if (o1.iPart.size() != o2.iPart.size()) {
				if (o1.iPart.size() > o2.iPart.size()) {
					return -1;
				}
				if (o1.iPart.size() < o2.iPart.size()) {
					return 1;
				}
			} else {
				for (int i = 0; i < iPart.size(); i++) {
					if (o1.iPart.get(i) > o2.iPart.get(i)) {
						return -1;
					}
					if (o1.iPart.get(i) < o2.iPart.get(i)) {
						return 1;
					}
				}
				for (int i = 0; i < fPart.size(); i++) {
					if (o1.fPart.get(i) > o2.fPart.get(i)) {
						return -1;
					}
					if (o1.fPart.get(i) < o2.fPart.get(i)) {
						return 1;
					}
				}
				return 0;

			}

		}
		if (o1.sign == '-' && o2.sign == '+') {
			return -1;
		}
		if (o1.sign == '+' && o2.sign == '-')
			return 1;
		return 0;

	}

}
