import java . awt. *;
import javax. swing. *;
import javax. imageio. *;
import java . awt. image. *;
import java . io. *;

public class Wall extends Object{
	private PointManager p[] = new PointManager[4];
	private PointManager center;
	private Color c = new Color( 0, 0, 0);
	private double x1;
	private double z1;
	private double x2;
	private double z2;
	
	private JFrame win;
	
	private BufferedImage image;
	
	Wall(){
	}
	Wall( double x1, double z1, double x2, double z2, JFrame win){
		p[ 0] = new PointManager( x1, -60.0, z1);
		p[ 1] = new PointManager( x1,  100.0, z1);
		p[ 2] = new PointManager( x2, -60.0, z2);
		p[ 3] = new PointManager( x2,  100.0, z2);
		
		center = new PointManager( (x1+x2)/2.0, 0.0, (z1+z2)/2.0);
		
		this.x1 = x1;
		this.z1 = z1;
		this.x2 = x2;
		this.z2 = z2;
		
		try{
			image = ImageIO.read( new File( "wall.png"));
		}catch( IOException e){}
		
		this.win = win;
	}
	public void draw( Graphics g, Point3D camera, Point3D cameraR){
		for( int i = 0; i < 4; i ++) p[ i].setCamera( camera, cameraR);
		
		// draw texture
		int start = 0;
		int width = (int)Math.abs( p[ 0].getX2D()-p[ 2].getX2D());
		if ( (int)p[ 0].getX2D() > (int)p[ 2].getX2D() ) start = 2;
		
		int height1 = (int)(Math.abs(p[start].getY2D()-p[(start+1)%4].getY2D()));
		int height2 = (int)(Math.abs(p[(start+2)%4].getY2D()-p[(start+3)%4].getY2D()));
		int height3 = (int)(height2-height1);
		int y1 = (int)(p[ (start+2)%4].getY2D()-p[ start].getY2D());
		
		for( int i = 0; i < width; i ++){
			double step = (double)i/(double)width;
			double yp = y1*step;
			g.drawImage( image, (int)(p[start].getX2D()+i), (int)(p[ start].getY2D()-(height1+(double)(height3*step))+yp), (int)(p[start].getX2D()+i+1), (int)(p[ start].getY2D()+yp), (int)((double)image.getWidth()*step), 0, (int)((double)image.getWidth()*step)+1, image.getHeight(), win);
		}
		
		//draw shadow
		
		double ang = (1.0+Math.cos( Math.atan2( x1-x1, z1-z2)))/2.0;
		if ( (int)p[ 0].getX2D() < (int)p[ 3].getX2D() ) ang = (1.0+Math.cos( Math.atan2( x1-x2, z1-z2)+Math.PI))/2.0;
		g.setColor( new Color( 0, 0, 0, (int)( 255*ang)));
		int xs[] = { (int)p[ 0].getX2D(), (int)p[ 1].getX2D(), (int)p[ 3].getX2D(), (int)p[ 2].getX2D()};
		int ys[] = { (int)p[ 0].getY2D(), (int)p[ 1].getY2D(), (int)p[ 3].getY2D(), (int)p[ 2].getY2D()};
		g.fillPolygon( xs, ys, 4);
		
		
	}
	public void setCamera( Point3D camera, Point3D cameraR){
		for( PointManager point: p) point.setCamera( camera, cameraR);
		center.setCamera( camera, cameraR);
	}
	public double getDistance(){
		return center.getDistance();
	}
	public double getR(){
		PointManager out = p[0];
		for( PointManager point: p) if ( out.getR()*out.getR() > point.getR()*point.getR()) out = point;
		return out.getR();
	}
}
