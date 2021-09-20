/**
 * HeftyInteger for CS1501 Project 5
 * @author	Dr. Farnan
 */
package cs1501_p5;

//import java.math.BigInteger;

//import java.util.Random;


public class HeftyInteger {

	private final byte[] ONE = {(byte) 1};
	private final int BYTE_SZ = 8;

	private byte[] val;

	/**
	 * Construct the HeftyInteger from a given byte array
	 * @param b the byte array that this HeftyInteger should represent
	 */
	public HeftyInteger(byte[] b) {
		val = b;
	}

	/**
	 * Return this HeftyInteger's val
	 * @return val
	 */
	public byte[] getVal() {
		return val;
	}

	/**
	 * Return the number of bytes in val
	 * @return length of the val byte array
	 */
	public int length() {
		return val.length;
	}

	/**
	 * Add a new byte as the most significant in this
	 * @param extension the byte to place as most significant
	 */
	public void extend(byte extension) {
		byte[] newv = new byte[val.length + 1];
		newv[0] = extension;
		for (int i = 0; i < val.length; i++) {
			newv[i + 1] = val[i];
		}
		val = newv;
	}

	

	/**
	 * If this is negative, most significant bit will be 1 meaning most
	 * significant byte will be a negative signed number
	 * @return true if this is negative, false if positive
	 */
	public boolean isNegative() {
		return (val[0] < 0);
	}

	/**
	 * Computes the sum of this and other
	 * @param other the other HeftyInteger to sum with this
	 */
	public HeftyInteger add(HeftyInteger other) {
		byte[] a, b;
		// If operands are of different sizes, put larger first ...
		if (val.length < other.length()) {
			a = other.getVal();
			b = val;
		}
		else {
			a = val;
			b = other.getVal();
		}

		// ... and normalize size for convenience
		if (b.length < a.length) {
			int diff = a.length - b.length;

			byte pad = (byte) 0;
			if (b[0] < 0) {
				pad = (byte) 0xFF;
			}

			byte[] newb = new byte[a.length];
			for (int i = 0; i < diff; i++) {
				newb[i] = pad;
			}

			for (int i = 0; i < b.length; i++) {
				newb[i + diff] = b[i];
			}

			b = newb;
		}

		// Actually compute the add
		int carry = 0;
		byte[] res = new byte[a.length];
		for (int i = a.length - 1; i >= 0; i--) {
			// Be sure to bitmask so that cast of negative bytes does not
			//  introduce spurious 1 bits into result of cast
			carry = ((int) a[i] & 0xFF) + ((int) b[i] & 0xFF) + carry;

			// Assign to next byte
			res[i] = (byte) (carry & 0xFF);

			// Carry remainder over to next byte (always want to shift in 0s)
			carry = carry >>> 8;
		}

		HeftyInteger res_li = new HeftyInteger(res);

		// If both operands are positive, magnitude could increase as a result
		//  of addition
		if (!this.isNegative() && !other.isNegative()) {
			// If we have either a leftover carry value or we used the last
			//  bit in the most significant byte, we need to extend the result
			if (res_li.isNegative()) {
				res_li.extend((byte) carry);
			}
		}
		// Magnitude could also increase if both operands are negative
		else if (this.isNegative() && other.isNegative()) {
			if (!res_li.isNegative()) {
				res_li.extend((byte) 0xFF);
			}
		}

		// Note that result will always be the same size as biggest input
		//  (e.g., -127 + 128 will use 2 bytes to store the result value 1)
		return res_li;
	}

	/**
	 * Negate val using two's complement representation
	 * @return negation of this
	 */
	public HeftyInteger negate() {
		byte[] neg = new byte[val.length];
		int offset = 0;

		// Check to ensure we can represent negation in same length
		//  (e.g., -128 can be represented in 8 bits using two's
		//  complement, +128 requires 9)
		if (val[0] == (byte) 0x80) { // 0x80 is 10000000
			boolean needs_ex = true;
			for (int i = 1; i < val.length; i++) {
				if (val[i] != (byte) 0) {
					needs_ex = false;
					break;
				}
			}
			// if first byte is 0x80 and all others are 0, must extend
			if (needs_ex) {
				neg = new byte[val.length + 1];
				neg[0] = (byte) 0;
				offset = 1;
			}
		}

		// flip all bits
		for (int i  = 0; i < val.length; i++) {
			neg[i + offset] = (byte) ~val[i];
		}

		HeftyInteger neg_li = new HeftyInteger(neg);

		// add 1 to complete two's complement negation
		return neg_li.add(new HeftyInteger(ONE));
	}

	/**
	 * Implement subtraction as simply negation and addition
	 * @param other HeftyInteger to subtract from this
	 * @return difference of this and other
	 */
	public HeftyInteger subtract(HeftyInteger other) {
		return this.add(other.negate());
	}





	

	/**
 	* 
 	* I have to write code beneath this line:
	* ~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~
 	* 
 	*/





	 



	/**
	 * Compute the product of this and other
	 * @param other HeftyInteger to multiply by this
	 * @return product of this and other
	 */
	public HeftyInteger multiply(HeftyInteger other) {
		HeftyInteger a, b, temp, product, aMult, bMult;
		a = this;
		b = other;

		// If operands are of different sizes, put larger first ...
		if (val.length < other.length()) {
			temp = a;
			a = b;
			b = temp;	
		}

		//transforms values into negatives into positives
		boolean aNeg = a.isNegative();
		boolean bNeg = b.isNegative();
		if(aNeg)
			aMult = a.negate();
		else
			aMult = a;
		if(bNeg)
			bMult = b.negate();
		else
			bMult = b;



		product = gradeschoolMult(aMult,bMult);



		if((aNeg && bNeg) || (!aNeg && !bNeg))
			return product;
		return product.negate();
	}





	private HeftyInteger gradeschoolMult(HeftyInteger a, HeftyInteger b){
		//both a and b are guaranteed to be positive.
		//we know that b is shorter in bit-length

		byte[] b_arr = b.getVal();
		byte mask;
		int index = a.getVal().length - 1;
		HeftyInteger sum = new HeftyInteger(new byte[]{0});


		for(int i = 0; i<b_arr.length * 8; i++){
			mask = (byte) (1<<i%8);
			index = (b_arr.length - i/8 - 1);

			if((mask & b_arr[index]) != 0)
				sum = sum.add(a.leftShift(i));
		}

		sum.contract();
		return sum;
	}



	/**
	 * Gets rid of leading zeros in val byte[]
	 */
	public void contract() {
		byte[] arr = this.getVal();
		int new_start_index = 0;
		int stop = arr.length - 2;
		if(arr[0] == 0 && arr.length > 2)
			while(arr[new_start_index+1] == 0 && new_start_index < stop)
				new_start_index++;

		byte[] new_val = new byte[arr.length - new_start_index];
		for(int i = 0; i < new_val.length; i++){
			new_val[i] = arr[new_start_index + i];
		}
		this.val = new_val;
	}



	/**
	 * Saves on computation time by computing division and mod simultanteously and returning them both in a HI array
	 * 
	 * @param b divisor
	 * @return array such that {quotient, remainder}
	 */
	private HeftyInteger[] divisionAndMod(HeftyInteger b){
		final HeftyInteger HI_ONE = new HeftyInteger(ONE);

		HeftyInteger a = new HeftyInteger(this.getVal());
		byte[] a_arr = a.getVal();

		HeftyInteger q = new HeftyInteger(new byte[]{0}); //quotient
		
		HeftyInteger part_div = new HeftyInteger(new byte[]{0});
		byte[] next_bit = {0};

		
		for(int i = (BYTE_SZ * a_arr.length) - 1; i >=0; i--){
			a_arr = a.rightShift(i).getVal();
			next_bit[0] = (byte) (a_arr[a_arr.length - 1] & 1);
			part_div = part_div.leftShift(1).add(new HeftyInteger(next_bit));

			if(part_div.compareTo(b) != 0)
				q = q.leftShift(1);
			else{
				q = q.leftShift(1).add(HI_ONE);
				part_div = part_div.subtract(b);
			}
		}


		//q has way to many leading zeros;
		//System.out.println("q: " + new BigInteger(q.getVal()));
		//System.out.println("length: " + q.getVal().length);

		q.contract();
		part_div.contract();

		return new HeftyInteger[]{q,part_div};
	}

	/*
	public HeftyInteger mod(HeftyInteger b){
		HeftyInteger temp = this.dividedBy(b);
		return this.subtract(temp.multiply(b));
	}
*/





	/**
	 * Run the extended Euclidean algorithm on this and other
	 * @param other another HeftyInteger
	 * @return an array structured as follows:
	 *   0:  the GCD of this and other
	 *   1:  a valid x value
	 *   2:  a valid y value
	 * such that this * x + other * y == GCD in index 0
	 */
	public HeftyInteger[] XGCD(HeftyInteger other) {
		if (this.isNegative() || other.isNegative())
			return null;

		boolean swapped; //in use in case I have to swap this and other
		HeftyInteger[] vals = new HeftyInteger[10];
		HeftyInteger[] div_and_mod;

		/**
		 * Array vals:
		 * --------------------------------------------------
		 * index:   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |
		 * meaning: | q | a | b | r | s1| s2| s | t1| t2| t |
		 * --------------------------------------------------
		 */


		//assumption starting values:
		//s1 = 1, s2 = 0
		//t1 = 0, t2 = 1
		vals[4] = new HeftyInteger(new byte[]{1});
		vals[5] = new HeftyInteger(new byte[]{0});
		vals[7] = new HeftyInteger(new byte[]{0});
		vals[8] = new HeftyInteger(new byte[]{1});
		if(this.compareTo(other) == -1) //if this is less than other, swap
		{
			vals[1] = other;
			vals[2] = this;
			swapped = true;
		}
		else{
			vals[1] = this;
			vals[2] = other;
			swapped = false;
		}

		HeftyInteger one = new HeftyInteger(new byte[]{1});
		while(!vals[2].subtract(one).isNegative()){
			//setting quotient and remainder
			div_and_mod = vals[1].divisionAndMod(vals[2]);
			vals[0] = div_and_mod[0];
			vals[3] = div_and_mod[1];

			//setting s and t:
			//----------------------------------------------------
			//s = s1 - s2*q;
			vals[6] = vals[4].subtract(vals[5].multiply(vals[0]));
			//t = t1 - t2*q;
			vals[9] = vals[7].subtract(vals[8].multiply(vals[0]));

			//shift vals left by one.
			vals[1] = vals[2];
			vals[2] = vals[3];
			vals[4] = vals[5];
			vals[5] = vals[6];
			vals[7] = vals[8];
			vals[8] = vals[9];
			//System.out.println("HeftyInteger: " + new BigInteger(vals[2].getVal()));
		}

		
		if(swapped)
		return new HeftyInteger[]{vals[1], vals[7], vals[4]};
		return new HeftyInteger[]{vals[1], vals[4], vals[7]};
	}







	/**
	 * 
	 * @param arr array to left shift
	 * @param amount how many places to left shift
	 * @return returns new HeftyInteger of this left shifted
	 */
	public HeftyInteger leftShift(int amount){
		int shift = amount/8 + 1;
		int bits = amount%8;
		byte[] val = this.getVal();
		byte[] newVal = new byte[val.length + shift];

		for(int i = 0; i < newVal.length; i++)
			if(i < val.length)
				newVal[i] = val[i];
			else
				newVal[i] = 0;
		

		return new HeftyInteger(newVal).rightShift(BYTE_SZ - bits);		
	}


	/**
	 * 
	 * @param arr array to right shift
	 * @param amount how many places to right shift
	 * @return returns new HeftyInteger of this right shifted
	 */
	public HeftyInteger rightShift(int amount){
		int bitShift = amount%8;
		int byteShift = amount/8;
		byte mask = (byte) ((0b11111111) << (BYTE_SZ - bitShift));

		byte[] val = this.getVal();
		int len = val.length;

		byte[] newVal = new byte[len];

		int j = 0; // j is this index of the original val. it's used to 
		for(int i = len-1; i>=0; i--){
			j = i-byteShift;
			if(j<0)
				newVal[i] = 0;
			else{
				byte nxt = (byte)((0b11111111 & val[j]) >>> bitShift); //I don't know why I need the 0b111... mask, but it solves the problem that I was having
				//System.out.println("newVal[" + i +"]: " + dst);
				if(j - 1 >=0)
					nxt = (byte) ((nxt) | (val[j-1] << (BYTE_SZ - bitShift) & mask));
				newVal[i] = nxt;
			}
		}
		return new HeftyInteger(newVal);
	}




	/**
	 * Deletes the most significant byte in this
	 */
	private byte[] contract(byte[] arr){
		byte[] newv = new byte[arr.length - 1];
		for (int i = 0; i < arr.length - 1; i++) 
			newv[i] = arr[i+1];
		return newv;	
	}


	public boolean equals(HeftyInteger b){
		byte[] b_arr = b.getVal();
		while(this.val.length > 2)
			if(val[0] == 0 && val[1] == 0)
				val = contract(val);
			else
				break;
		while(b_arr.length >= 2)
			if(b_arr[0] == 0 && b_arr[1] >= 0)
				b_arr = contract(b_arr);
			else
				break;

		if(b_arr.length != val.length)
			return false;
		for(int i =0; i<val.length; i++)
			if(b_arr[i] != val[i])
				return false;
		return true;
	}

	
	public boolean equals(byte[] b){
		return this.equals(new HeftyInteger(b));
	}

	/**
	 * compares this to other
	 * @param other other HeftyInteger
	 * @return -1 if other > this, 0 if this>= other
	 */
	public int compareTo (HeftyInteger other){
		HeftyInteger t = this.subtract(other);
		if(t.isNegative())
			return -1;
		return 0;
	}

	/*
	//REMEMBER TO DELETE THIS:
	private static void printVal(HeftyInteger arg){
        System.out.println("HeftyInteger value: " + new BigInteger(arg.getVal()));
    }
	*/

}
