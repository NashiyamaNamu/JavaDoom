import java . awt. *;
import javax. swing. *;
import javax. imageio. *;
import java . awt. image. *;
import java . io. *;

public class Sprite extends Object{
	private double x = 0.0;
	private double y = 0.0;
	
	private double width = 100.0;
	private double height = 100.0;
	
	private JFrame win;
	private BufferedImage image = null;
	
	private PointManager pm  = new PointManager();
	
	Sprite(){
	}
	Sprite( String fname, JFrame win){
		try{
			image = ImageIO.read( new File( fname));
		}catch( IOException e){
		}
		this.win = win;
	}
	
	private void transPosision(){
		x = pm.getX2D();
		y = pm.getY2D();
	}
	public void setAxis( boolean axis){
		pm.setAxis( axis);
	}
	public void position( Point3D p3d){
		pm.setPoint3D( p3d);
	}
	public void size( double width, double height){
		this.width = width;
		this.height = height;
	}
	public void draw( Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		transPosision();
		if ( image == null ){
			g.setColor( Color.BLACK);
			g.fillRect( (int)(x-width/2.0), (int)(y-height/2.0), (int)width, (int)height);
		}else{
			if ( pm.getAxis() ){
				PointManager pm2 = new PointManager();
				pm2.setCamera( pm.getCamera(), pm.getCameraR());
				pm2.set( pm.getX(), pm.getY()+1, pm.getZ());
				double w = this.width*((pm.getY2D()-pm2.getY2D())*0.25);
				double h = this.height*((pm.getY2D()-pm2.getY2D())*0.25);
				g.drawImage( image, (int)(x-w/2.0), (int)(y-h/2.0), (int)w, (int)h , win);
			}else{
				g.drawImage( image, (int)(x-width/2.0), (int)(y-height/2.0), (int)width, (int)height , win);
			}
		}
	}
	public double getR(){
		return getPM().getR();
	}
	public void setCamera( Point3D camera, Point3D cameraR){
		pm.setCamera( camera, cameraR);
	}
	public void draw( Graphics g, Point3D camera, Point3D cameraR){
		pm.setCamera( camera, cameraR);
		this.draw( g);
	}
	public PointManager getPM(){
		return pm;
	}
	public double getDistance(){
		return getPM().getDistance();
	}
}