import javax. swing. *;
import java . awt. *;
import java . awt. image. *;
import java . awt. event. *;
import java . util. Timer;
import java . util. TimerTask;
import java . util. ArrayList;
import java . io. *;

public class MapEditor{
	JFrame win = new JFrame( "Map Editor");
	JPanel jp = new JPanel();
	MyCanvas mc = new MyCanvas();
	
	boolean mouse = false;
	
	int camX = 0;
	int camY = 0;
	
	int mouseBX = 0;
	int mouseBY = 0;
	
	int editX = 0;
	int editY = 0;
	
	boolean editing = false;
	ArrayList< WallLine> walls = new ArrayList<>();
	
	public static void main( String args[]){
		MapEditor me = new MapEditor();
	}
	MapEditor(){
		win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		win.setResizable( false);
		
		jp.add(  mc);
		win.add( jp);
		
		win.setLayout( null);
		jp .setLayout( null);
		
		mc.setBounds( 0, 0, 800, 480);
		jp.setBounds( 0, 0, 800, 480);
		
		win.setVisible( true);
		
		Insets in = win.getInsets();
		win.setSize( 800+in.left+in.right, 480+in.bottom+in.top);
		win.setLocationRelativeTo( null);
		
		win.addMouseListener( new MyMouse());
		win.addKeyListener( new MyKey());
		
		Timer t = new Timer();
		t.schedule( new MyTimer(), 10, 15);
	}
	public class MyTimer extends TimerTask{
		public void run(){
			mc.repaint();
			
			if ( mouse ){
				camX += mouseBX-getMouseX();
				camY += mouseBY-getMouseY();
			}
			
			mouseBX = getMouseX();
			mouseBY = getMouseY();
		}
	}
	public class MyCanvas extends JPanel{
		public void paint( Graphics g){
			g.setColor( new Color( 150, 150, 150));
			g.fillRect( 0, 0, 800, 480);
			
			g.setColor( Color.RED);
			g.drawLine( 0, 240-camY, 800, 240-camY);
			g.drawLine( 0, 240-camY, 800, 240-camY);
			g.drawLine( 400-camX, 0, 400-camX, 480);
			
			
			g.setColor( Color.BLACK);
			
			if ( editing ){
				g.drawLine( editX-camX, editY-camY, getMouseX(), getMouseY() );
			}
			for( WallLine w: walls) w.draw( g);
		}
	}
	public class MyKey extends KeyAdapter{
		public void keyPressed( KeyEvent e){
			if ( e.getKeyCode() == KeyEvent.VK_S ){
				try{
					File f = new File( "editing.txt");
					BufferedWriter bw = new BufferedWriter( new FileWriter( f));
					for( WallLine w: walls){
						bw.write( w.outData());
						bw.newLine();
					}
					bw.close();
				}catch( IOException e2){ }
			}
		}
	}
	public class MyMouse implements MouseListener{
		public void mousePressed( MouseEvent e){
			if ( e.getButton() == MouseEvent.BUTTON1 ){
				if ( editing ){
					walls.add( new WallLine( getMouseX()+camX, getMouseY()+camY, editX, editY));
				}
				editX = getMouseX()+camX;
				editY = getMouseY()+camY;
				editing = true;
				
			}
			if ( e.getButton() == MouseEvent.BUTTON2 ){
				editing = false;
			}
			if ( e.getButton() == MouseEvent.BUTTON3 ) mouse = true;
		}
		public void mouseReleased( MouseEvent e){
			if ( e.getButton() == MouseEvent.BUTTON3 ) mouse = false;
		}
		public void mouseExited( MouseEvent e){
		}
		public void mouseEntered( MouseEvent e){
		}
		public void mouseClicked( MouseEvent e){
		}
	}
	public int getMouseX(){
		int out = 0;
		Insets in = win.getInsets();
		PointerInfo pinfo = MouseInfo.getPointerInfo();
		Point p = pinfo.getLocation();
		out = (int)p.getX()-in.left-win.getX();
		
		return out;
	}
	public int getMouseY(){
		int out = 0;
		Insets in = win.getInsets();
		PointerInfo pinfo = MouseInfo.getPointerInfo();
		Point p = pinfo.getLocation();
		out = (int)p.getY()-in.top-win.getY();
		
		return out;
	}
	public class WallLine{
		int x1, y1, x2, y2;
		WallLine( int x1, int y1, int x2, int y2){
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		public void draw( Graphics g){
			g.drawLine( x1-camX, y1-camY, x2-camX, y2-camY);
		}
		public String outData(){
			return ""+(x1-400)+","+(y1-240)+","+(x2-400)+","+(y2-240)+"";
		}
	}
}