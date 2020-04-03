import java . awt. *;
abstract class Object{
	abstract public double getDistance();
	abstract public double getR();
	abstract public void setCamera( Point3D camera, Point3D cameraR);
	abstract public void draw( Graphics g, Point3D camera, Point3D cameraR);
}