public class Point3D{
	/**  axis **/
	/** true : 3d
		false : 2d
	**/
	private double x = 0.0;
	private double y = 0.0;
	private double z = 0.0;
	
	Point3D(){
	}
	Point3D( double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	Point3D( double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void set( double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void set( double x, double y){
		this.x = x;
		this.y = y;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getZ(){
		return z;
	}
}
