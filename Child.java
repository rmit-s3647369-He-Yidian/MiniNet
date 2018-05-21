/**
 * 
 */
package MiniNet_2;


/**
 * @author s3647369_Yidian_He
 *
 */
public class Child extends Person {

	/**
	 * 
	 */
	private String prt1;
	private String prt2;
	
	/**
	 * @return the prt1
	 */
	public String getParent1() {
		return prt1;
	}
	/**
	 * @param father the father to set
	 */
	public void setParent1(String prt1) {
		this.prt1 = prt1;
	}
	/**
	 * @return the prt2
	 */
	public String getParent2() {
		return prt2;
	}
	/**
	 * @param mother the mother to set
	 */
	public void setParent2(String prt2) {
		this.prt2 = prt2;
	}
	
	public Child(String firstname, String lastname, String image, String status, 
			char gender, int age, String state, String prt1, String prt2) {
		super(firstname, lastname, image, status, gender, age, state);
		this.prt1 = prt1;
		this.prt2 = prt2;
	}


}
