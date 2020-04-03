public class PointManager{
	/**  axis **/
	/** true : 3d
		false : 2d
	**/
	private Point3D p3d = new Point3D();
	private Point3D camera = new Point3D( 0.0, 0.0, 0.0);
	private Point3D cameraR = new Point3D( 0.0, 0.0, 0.0);
	private boolean axis = true;
	PointManager(){
	}
	PointManager( boolean axis){
		this.axis = axis;
	}
	PointManager( double x, double y, double z){
		this.axis = true;
		p3d = new Point3D( x, y, z);
	}
	PointManager( double x, double y){
		this.axis = false;
		p3d = new Point3D( x, y);
	}
	public void setAxis( boolean axis){
		this.axis = axis;
	}
	public void set( double x, double y, double z){
		p3d.set( x, y, z);
	}
	public void set( double x, double y){
		p3d.set( x, y);
	}
	
	// same
	public void set( Point3D p3d){
		this.p3d = p3d;
	}
	public void setPoint3D( Point3D p3d){
		this.p3d = p3d;
	}
	
	// camera
	public void setCamera( Point3D camera, Point3D cameraR){
		this.camera = camera;
		this.cameraR = cameraR;
	}
	
	public double getX2D(){
		if ( axis){
			double dir = Math.sqrt((camera.getX()-p3d.getX())*(camera.getX()-p3d.getX())+(camera.getZ()-p3d.getZ())*(camera.getZ()-p3d.getZ()));
			double rad = -Math.atan2(  camera.getX()-p3d.getX(), camera.getZ()-p3d.getZ())-cameraR.getY();
			if ( rad >  Math.PI) rad -= Math.PI*2;
			if ( rad < -Math.PI) rad += Math.PI*2;
			return 320.0+320.0*(rad/Math.PI*4.0);
		}else{
			return p3d.getX();
		}
	}
	public double getY2D(){
		if ( axis){
			double dir = Math.sqrt((camera.getX()-p3d.getX())*(camera.getX()-p3d.getX())+(camera.getY()-p3d.getY())*(camera.getY()-p3d.getY())+(camera.getZ()-p3d.getZ())*(camera.getZ()-p3d.getZ()));
			double dir1 = Math.sqrt((camera.getX()-p3d.getX())*(camera.getX()-p3d.getX())+(camera.getZ()-p3d.getZ())*(camera.getZ()-p3d.getZ()));
			double rad = Math.atan2( camera.getY()-p3d.getY(), dir1)+cameraR.getX();
			return 240.0+240.0*(rad/Math.PI*4.0);
		}else{
			return p3d.getY();
		}
	}
	public double getDistance(){
		return Math.sqrt((camera.getX()-p3d.getX())*(camera.getX()-p3d.getX())+(camera.getY()-p3d.getY())*(camera.getY()-p3d.getY())+(camera.getZ()-p3d.getZ())*(camera.getZ()-p3d.getZ()));
	}
	public boolean getAxis(){
		return axis;
	}
	public Point3D getCamera(){
		return camera;
	}
	public Point3D getCameraR(){
		return cameraR;
	}
	public double getR(){
		double rad = -Math.atan2(  camera.getX()-p3d.getX(), camera.getZ()-p3d.getZ())-cameraR.getY();
		if ( rad >  Math.PI) rad -= Math.PI*2;
		if ( rad < -Math.PI) rad += Math.PI*2;
		return rad;
	}
	public double getX(){
		return p3d.getX();
	}
	public double getY(){
		return p3d.getY();
	}
	public double getZ(){
		return p3d.getZ();
	}
}
