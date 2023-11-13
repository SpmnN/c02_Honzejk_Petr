import fill.ScanLineFiller;
import fill.SeedFiller;
import model.Line;
import model.Point;
import model.Polygon;
import rasterize.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Canvas {

	private JPanel panel;
	private RasterBufferedImage raster;
	private LineRasterizer lineRasterizer;
	private PolygonRasterizer polygonRasterizer;
	private Polygon polygon;
	private int mouseX, mouseY;
	private int x,y;

	public Canvas(int width, int height) {
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());

		frame.setTitle("PGRF - rasterizace");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		raster = new RasterBufferedImage(width, height);
		polygonRasterizer = new PolygonRasterizer(lineRasterizer);
		polygon = new Polygon();
		lineRasterizer = new FilledLineRasterizer(raster);

		Graphics g = raster.getGraphics();
		g.setColor(Color.YELLOW);
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present(g);
			}
		};
		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//ScanLineFiller scanLineFiller = new ScanLineFiller(lineRasterizer,polygonRasterizer,polygon);
				//scanLineFiller.fill();

				if(e.getButton() == MouseEvent.BUTTON3) {
					SeedFiller seedFiller = new SeedFiller(
							raster,
							e.getX(),
							e.getY(),
							0xff0000,
							raster.getPixel(e.getX(),e.getY()));
					seedFiller.fill();
				}
				panel.repaint();
			}
		});
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){//při stisknutí klávesy C, vyčisti celé plátno
				if(e.getKeyCode() == KeyEvent.VK_C){
					clear(0x2f2f2f);
					panel.repaint();
				}
			}
		});
		panel.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e){
			lineRasterizer.setColor(16711935);//růžová
			if(SwingUtilities.isRightMouseButton(e)){//dle stisknuté klávesy vybere, co se má vykreslit
				clear(0x2f2f2f);
				panel.repaint();
			}
			else if (SwingUtilities.isLeftMouseButton(e)) {//přesné vykreslování
				if(e.isControlDown()){
					Point p = new Point(e.getX(), e.getY());
					polygon.addPoint(p);
					polygonRasterizer.rasterize(polygon);
				}
				else if(e.isShiftDown()){
					clear(0x2f2f2f);
					x = e.getX();
					y = e.getY();
					drawPrecision(mouseX,x,mouseY,y);
					panel.repaint();
				}
				else {//standartní vykreslení
					clear(0x2f2f2f);
					x = e.getX();
					y = e.getY();
					draw(mouseX, x, mouseY, y);
					panel.repaint();
				}
			}
		}
			@Override
			public void mousePressed(MouseEvent e){
				if(SwingUtilities.isLeftMouseButton(e)) {//dle stisknuté klávesy vybere, co se má vykreslit
					if (e.isControlDown()) {
						mouseX = e.getX();
						mouseY = e.getY();
					} else if (e.isShiftDown()) {
						mouseX = e.getX();
						mouseY = e.getY();
					} else {
						mouseX = e.getX();
						mouseY = e.getY();
						panel.repaint();
					}
				}
			}
	});

		panel.addMouseMotionListener(new MouseAdapter() {@Override
			public void mouseDragged(MouseEvent e) {
				lineRasterizer.setColor(16711680);//červená
				if(SwingUtilities.isLeftMouseButton(e)){//dle stisknuté klávesy vybere, co se má vykreslit
					if(e.isShiftDown())//přesné vykreslování
					{
						clear(0x2f2f2f);
						drawDottedPrecision(mouseX,e.getX(),mouseY,e.getY());
						panel.repaint();
					}
					else {//vykreslení tečkované čáry
						clear(0x2f2f2f);
						drawDotted(mouseX, e.getX(), mouseY, e.getY());
						panel.repaint();
					}
				}
			}
		});
	}
	public void clear(int color) {
		Graphics gr = raster.getGraphics();
		gr.setColor(new Color(0x2f2f2f));//šedá
		gr.fillRect(0, 0, raster.getWidth(), raster.getHeight());
	}

	public void present(Graphics graphics) {
		raster.repaint(graphics);
	}

	public void start() {
		clear(0x2f2f2f);
		panel.repaint();
	}

	public void drawPrecision(int mouseX, int x, int mouseY,int y){//0,45,90,135,180,225,270,315,360, plnou čarou
		lineRasterizer.rasterizePrecisionMode(mouseX,mouseY,x,y);
	}
	public void drawDotted(int x1, int x2, int y1, int y2){
		lineRasterizer.rasterizeDottedLine(x1,y1,x2,y2);
	}
	public void drawDottedPrecision(int mouseX, int x, int mouseY, int y){//-,,- tečkovanou čarou
		lineRasterizer.rasterizeDottedPrecisionLine(mouseX, mouseY, x,y);
	}
	public void draw(int x1, int x2, int y1, int y2) {
		lineRasterizer.rasterize(x1, y1, x2,y2);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}

}
