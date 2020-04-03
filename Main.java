import javax. swing. *;
import java . awt. *;
import java . awt. image. *;
import java . awt. event. *;
import java . util. Timer;
import java . util. TimerTask;
import java . util. ArrayList;
import java . io. *;

public class Main{
	JFrame win = new JFrame( "");
	BufferStrategy bs;
	Sprite hand = new Sprite( "hand.png", win);
	
	Point3D camera = new Point3D( 0.0, 0.0, 0.0);
	Point3D cameraR = new Point3D( 0.0, 0.0, 0.0);
	ArrayList<Object> objects = new ArrayList<>();
	
	boolean key[] = new boolean[ 10];
	
	double r = 0.0;
	
	public static void main( String args[]){
		Main gm = new Main();
	}
	Main(){
		win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		win.setResizable( false);
		win.setVisible( true);
		
		try{
			win.createBufferStrategy( 2);
			bs = win.getBufferStrategy();
		}catch( Exception e){}
		
		Insets in = win.getInsets();
		win.setSize( 640+in.left+in.bottom, 480+in.top+in.bottom);
		win.setLocationRelativeTo( null);
		
		hand.setAxis( false);
		hand.position( new Point3D( 320, 360));
		hand.size( 320, 240);
		
		try{
			File f = new File( "./map//0.txt");
			BufferedReader br = new BufferedReader( new FileReader( f));
			String str = "#";
			while( (str = br.readLine()) != null ){
				String items[] = str.split( ",");
				int x1, z1, x2, z2;
				
				x1 = Integer.valueOf( items[ 0]);
				z1 = Integer.valueOf( items[ 1]);
				x2 = Integer.valueOf( items[ 2]);
				z2 = Integer.valueOf( items[ 3]);
				
				objects.add( new Wall( x1, z1, x2, z2, win));
			}
		}catch( IOException e){}
		
		
		for( int i = 0; i < 100; i ++){
			Sprite sizue;
			sizue = new Sprite( "sizue.png", win);
			sizue.position( new Point3D( -300.0+Math.random()*600, 0.0, -300.0+Math.random()*600.0));
			sizue.size( 80, 80);
			objects.add( sizue);
		}
		
		for( int i = 0; i < objects.size(); i ++){
			for( int ii = 0; ii < objects.size()-1; ii ++){
				if ( objects.get( ii).getDistance() < objects.get( ii+1).getDistance() ){
					Object temp = objects.get( ii);
					objects.set( ii  , objects.get( ii+1));
					objects.set( ii+1, temp);
				}
			}
		}
		
		win.addKeyListener( new MyKey());
		
		Timer t = new Timer();
		t.schedule( new MyTimer(), 10, 15);
	}
	public class MyTimer extends TimerTask{
		public void run(){
			Graphics g = bs.getDrawGraphics();
			if ( !bs.contentsLost()){
				Insets in = win.getInsets();
				g.translate( in.left, in.top);
				Graphics2D g2 = (Graphics2D)g;
				
				g.setColor( new Color( 0, 0, 100));
				g.fillRect( 0, 0, 640, 480);
				
				
				for( int i = 0; i < 100; i ++){
					objects.get( i).setCamera( camera, cameraR);
				}
				for( int i = 0; i < objects.size()-1; i ++){
					if ( objects.get( i).getDistance() < objects.get( i+1).getDistance() ){
						for( int ii = 0; ii < objects.size()-1; ii ++){
							if ( objects.get( ii).getDistance() < objects.get( ii+1).getDistance() ){
								Object temp = objects.get( ii);
								objects.set( ii  , objects.get( ii+1));
								objects.set( ii+1, temp);
							}
						}
					}
				}
				
				g2.rotate( -cameraR.getZ(), 320, 240);
				for( Object obj: objects){
					if ( obj.getR() < Math.PI/2.0 && obj.getR() > -Math.PI/2.0 ) obj.draw( g, camera, cameraR);
				}
				g2.rotate( cameraR.getZ(), 320, 240);
				hand.draw( g);
				
				bs.show();
				g.dispose();
				
				if ( key[ 0] ){
					camera.set( camera.getX()+Math.sin( cameraR.getY())*1.75, camera.getY(), camera.getZ()-Math.cos( cameraR.getY())*1.75);
					cameraR.set( cameraR.getX()+0.01, cameraR.getY(), cameraR.getZ());
					hand.position( new Point3D( hand.getPM().getX2D(), hand.getPM().getY2D()+2.0));
				}
				if ( key[ 1] ){
					camera.set( camera.getX()+Math.sin( cameraR.getY()-Math.PI*0.5)*1.75, camera.getY(), camera.getZ()-Math.cos( cameraR.getY()-Math.PI*0.5)*1.75);
					cameraR.set( cameraR.getX(), cameraR.getY(), cameraR.getZ()-0.01);
					hand.position( new Point3D( hand.getPM().getX2D()+2.0, hand.getPM().getY2D()));
				}
				if ( key[ 2] ){
					camera.set( camera.getX()-Math.sin( cameraR.getY())*1.75, camera.getY(), camera.getZ()+Math.cos( cameraR.getY())*1.75);
					cameraR.set( cameraR.getX()-0.01, cameraR.getY(), cameraR.getZ());
					hand.position( new Point3D( hand.getPM().getX2D(), hand.getPM().getY2D()-2.0));
				}
				if ( key[ 3] ){
					camera.set( camera.getX()+Math.sin( cameraR.getY()+Math.PI*0.5)*1.75, camera.getY(), camera.getZ()-Math.cos( cameraR.getY()+Math.PI*0.5)*1.75);
					cameraR.set( cameraR.getX(), cameraR.getY(), cameraR.getZ()+0.01);
					hand.position( new Point3D( hand.getPM().getX2D()-2.0, hand.getPM().getY2D()));
				}
				if ( key[ 4] ){
					cameraR.set( cameraR.getX(), cameraR.getY()-0.075, cameraR.getZ());
					cameraR.set( cameraR.getX(), cameraR.getY(), cameraR.getZ()-0.01);
					hand.position( new Point3D( hand.getPM().getX2D()+3.0, hand.getPM().getY2D()));
				}
				if ( key[ 5] ){
					cameraR.set( cameraR.getX(), cameraR.getY()+0.075, cameraR.getZ());
					cameraR.set( cameraR.getX(), cameraR.getY(), cameraR.getZ()+0.01);
					hand.position( new Point3D( hand.getPM().getX2D()-3.0, hand.getPM().getY2D()));
				}
				
				if ( cameraR.getY() >  Math.PI ) cameraR.set( cameraR.getX(), cameraR.getY()-Math.PI*2.0, cameraR.getZ());
				if ( cameraR.getY() < -Math.PI ) cameraR.set( cameraR.getX(), cameraR.getY()+Math.PI*2.0, cameraR.getZ());
				cameraR.set( cameraR.getX()*0.94, cameraR.getY(), cameraR.getZ()*0.94);
				
				hand.position( new Point3D( 320.0+(hand.getPM().getX2D()-320.0)*0.95, 360.0+(hand.getPM().getY2D()-360.0)*0.95));
				
				//r += 1;
			}
		}
	}
	public class MyKey extends KeyAdapter{
		public void keyPressed( KeyEvent e){
			if ( e.getKeyCode() == KeyEvent.VK_W ) key[ 0] = true;
			if ( e.getKeyCode() == KeyEvent.VK_A ) key[ 1] = true;
			if ( e.getKeyCode() == KeyEvent.VK_S ) key[ 2] = true;
			if ( e.getKeyCode() == KeyEvent.VK_D ) key[ 3] = true;
			if ( e.getKeyCode() == KeyEvent.VK_LEFT ) key[ 4] = true;
			if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) key[ 5] = true;
		}
		public void keyReleased( KeyEvent e){
			if ( e.getKeyCode() == KeyEvent.VK_W ) key[ 0] = false;
			if ( e.getKeyCode() == KeyEvent.VK_A ) key[ 1] = false;
			if ( e.getKeyCode() == KeyEvent.VK_S ) key[ 2] = false;
			if ( e.getKeyCode() == KeyEvent.VK_D ) key[ 3] = false;
			if ( e.getKeyCode() == KeyEvent.VK_LEFT ) key[ 4] = false;
			if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) key[ 5] = false;
		}
	}
}
