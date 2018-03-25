/**
 * 
 */
package MiniNet;


/**
 * @author s3655108_Yifan_Lao
 *
 */
abstract class Persons {
	private String name;
	private char gender;
	private int age;
	private String image;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @param name
	 * @param gender
	 * @param age
	 * @param status
	 */
	public Persons(String name, char gender, int age, String image) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.image = image;
	}
	public Persons(String name, char gender, int age) {
		this(name, gender, age, "");
	}
	
	public abstract boolean canBecomeFriends(Persons ps1, Persons ps2);

}
