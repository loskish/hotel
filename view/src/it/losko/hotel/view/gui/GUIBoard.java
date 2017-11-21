package it.losko.hotel.view.gui;

import it.losko.hotel.model.board.Board;
import it.losko.hotel.model.board.Entrance;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.board.Square;
import it.losko.hotel.model.game.Game;
import it.losko.hotel.model.player.Player;
import it.losko.hotel.model.player.Players;
import it.losko.hotel.view.gui.resources.Coordinates;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GUIBoard extends JLabel {
	private static final long serialVersionUID = -4891492133315304491L;

	private final GUI gui;
	private final Coordinates coordinates;
	
	// Constants
	private static final int IMG_PX_WIDTH = 800;
	private static final int IMG_PX_HEIGHT = 640;
	private static final double ASPECT_RATIO = ((double) IMG_PX_WIDTH) / ((double) IMG_PX_HEIGHT);

	// To remove once completed the board
	private static final boolean testMode = false;
	
	// Non-final instance fields
	private double scalingCoefficient = 1;
	
	public GUIBoard(final GUI gui) {
		this.gui = gui == null ? new GUI() : gui;
		this.coordinates = new Coordinates();
		
		// Sets the container size
		setSize();
		
		// Stretch the image to the container
		stretchImageToContainer();
		
		// Sets the resize listener
		addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
            	stretchImageToContainer();
            }
        });
	}
	
	public GUI getGui() {
		return gui;
	}

	public void setScalingCoefficient(double scalingCoefficient) {
		this.scalingCoefficient = scalingCoefficient;
	}

	public double getScalingCoefficient() {
		return scalingCoefficient;
	}
	
	private void setSize() {
		final Dimension d = new Dimension((int) (IMG_PX_WIDTH * getScalingCoefficient()), (int) (IMG_PX_HEIGHT * getScalingCoefficient()));
		setPreferredSize(d);
		setMaximumSize(d);
		setSize(d);
	}
	
    private void stretchImageToContainer() {
        int width = getWidth();
        int height = (int) (width / ASPECT_RATIO);
        
        if(height > getHeight()) {
        	height = getHeight();
        	width = (int) (height * ASPECT_RATIO);
        }
        
        // Otherwise IllegalArgumentException
        if(width != 0 && height != 0) {
        	setScalingCoefficient((double) width / (double) IMG_PX_WIDTH);
        	
        	Image scaledIm = gui.getImages().getBoard().getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        	setIcon(new ImageIcon(scaledIm));
        }
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);

    	drawCars(g);
    	drawBuildings(g);
    	drawEntrances(g);
    }
    
    private void drawCars(Graphics g) {
    	final Players players = Game.getSingleton().getPlayers();
    	if(players != null) {
    		for(Player player : players.getAll()) {
    			drawImage(g, gui.getImages().getCarByPlayer(player), coordinates.getCar(player));
    		}
    	}
    }
    
    private void drawBuildings(Graphics g) {
    	drawBankAndMunicipe(g);
    	drawHotels(g);
	}
    
    private void drawEntrances(Graphics g) {
    	final Board board = Game.getSingleton().getBoard();
    	for(final Square square : board.getSquares()) {
    		final Entrance entrance = square.getEntrance();
    		final int position = board.getSquarePosition(square);
    		if(position > 0) {
    			if(entrance != null) {
    				drawEntrance(g, position, entrance.getHotel().isInner());
    			}
    			
    			if(testMode) {
    				drawEntrance(g, position, true);
    				drawEntrance(g, position, false);
    			}
    		}
    	}
	}
    
    private void drawEntrance(final Graphics g, final int position, final boolean inner) {
    	drawAndRotateImage(g, gui.getImages().getEntrance(), coordinates.getEntrance(inner, position));
    }
    
    private void drawBankAndMunicipe(Graphics g) {
    	drawImage(g, gui.getImages().getBank(), coordinates.getBank());
    	drawImage(g, gui.getImages().getMunicipe(), coordinates.getMunicipe());
	}
    
    private void drawHotels(Graphics g) {
    	drawFujihama(g);
    	drawBoomerang(g);
    	drawLEtolie(g);
    	drawPresident(g);
    	drawRoyal(g);
    	drawWaikiki(g);
    	drawTajimahal(g);
    	drawSafari(g);
    }
    
	private void drawFujihama(Graphics g) {
		final Hotel hotel = Game.getSingleton().getBoard().getFujihama();
		drawBuildingSequence(g, hotel, 4, 2, 1, 3);
    }
    
	private void drawBoomerang(Graphics g) {
		final Hotel hotel = Game.getSingleton().getBoard().getBoomerang();
		drawBuildingSequence(g, hotel, 1, 2);
	}

	private void drawLEtolie(Graphics g) {
		final Hotel hotel = Game.getSingleton().getBoard().getLetoile();
		drawBuildingSequence(g, hotel, 1, 2, 3, 4, 5, 6);
	}

	private void drawPresident(Graphics g) {
		final Hotel hotel = Game.getSingleton().getBoard().getPresident();
		drawBuildingSequence(g, hotel, 5, 1, 2, 3, 4);
	}

	private void drawRoyal(Graphics g) {
		final Hotel hotel = Game.getSingleton().getBoard().getRoyal();
		drawBuildingSequence(g, hotel, 5, 2, 4, 1, 3);
	}

	private void drawWaikiki(Graphics g) {
		final Hotel hotel = Game.getSingleton().getBoard().getWaikiki();
		drawBuildingSequence(g, hotel, 6, 1, 2, 3, 4, 5);
	}

	private void drawTajimahal(Graphics g) {
		final Hotel hotel = Game.getSingleton().getBoard().getTajimahal();
		drawBuildingSequence(g, hotel, 4, 1, 2, 3);
	}

	private void drawSafari(Graphics g) {
		final Hotel hotel = Game.getSingleton().getBoard().getSafari();
		drawBuildingSequence(g, hotel, 4, 1, 2, 3);
	}
	
	private void drawBuildingSequence(final Graphics g, final Hotel hotel, final int ... sequence) {
		for(int i : sequence) {
			drawBuildingIfBuilt(g, hotel, i);
		}
	}

    private void drawBuildingIfBuilt(Graphics g, Hotel hotel, int level) {
    	if(testMode || hotel.isAtLeastOneBuildingBuilt()) {
    		if(testMode || level <= hotel.getAvailableBuildings().indexOf(hotel.getLastBuiltBuilding()) + 1) {
    			drawImage(g, gui.getImages().getBuildingByHotelAndLevel(hotel, level), coordinates.getBuilding(hotel, level));
    		}
    	}
    }
    
    private void drawImage(Graphics g, Image img, final Coordinates.XY xy) {
    	final Graphics2D gg = (Graphics2D) g;
    	final int deltaX = 0;
    	final int deltaY = (getHeight() - getIcon().getIconHeight()) / 2;
    	
    	int width = (int) (img.getWidth(this) * getScalingCoefficient());
    	int height = (int) (img.getHeight(this) * getScalingCoefficient());
    	gg.drawImage(img, (int) (xy.getX() * getScalingCoefficient()) + deltaX, (int) (xy.getY() * getScalingCoefficient()) + deltaY, width, height, this);
    }
    
    private void drawAndRotateImage(Graphics g, Image img, final Coordinates.XYT xyt) {
    	final Graphics2D gg = (Graphics2D) g;
    	final int deltaX = 0;
    	final int deltaY = (getHeight() - getIcon().getIconHeight()) / 2;
    	
    	AffineTransform transform = new AffineTransform();
        transform.setToTranslation((int) (xyt.getX() * getScalingCoefficient()) + deltaX, (int) (xyt.getY() * getScalingCoefficient()) + deltaY);
        transform.rotate(Math.toRadians(xyt.getTheta()));
        transform.scale(getScalingCoefficient(), getScalingCoefficient());
        gg.drawImage(img, transform, null);
    }
}
