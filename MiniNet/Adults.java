/**
 * 
 */
package MiniNet;


/**
 * @author s3655108_Yifan_Lao
 *
 */
class Adults extends Persons {

	/**
	 * 
	 */	
	private String status;
	
	
	/**
	 * @param name
	 * @param gender
	 * @param age
	 */
	public Adults(String name, char gender, int age, String image, String status) {
		super(name, gender, age, image);
		this.status = status;
	}
	public Adults(String name, char gender, int age) {
		this(name, gender, age, "", "");
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public boolean canBecomeFriends(Persons ps1, Persons ps2) {
		// TODO Auto-generated method stub
		if(ps2 instanceof Adults && !ps1.getName().equals(ps2.getName())) {
			return true;
		}else if(ps2 instanceof Dependents){
			System.out.println("An adult cannot become friends with an dependent.");
			return false;
		}else {
			return false;
		}
	}

	





	





}
