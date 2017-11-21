package it.losko.hotel.view.gui.resources;

import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.player.Player;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;


public class Images {
	
	private final Image board;
	private final Image bank;
	private final Image municipe;
	private final Image entrance;
	private final Map<String, Image> cars;
	private final Map<String, Image> buildings;
	
	public Images() {
		board = new ImageIcon("view/img/board.jpg").getImage();
		bank = new ImageIcon("view/img/buildings/bank/1.png").getImage();
		municipe = new ImageIcon("view/img/buildings/municipe/1.png").getImage();
		entrance = new ImageIcon("view/img/entrance.png").getImage();
		cars = new HashMap<String, Image>();
		buildings = new HashMap<String, Image>();
	}
	
	public Image getBoard() {
		return board;
	}
	
	public Image getCarByPlayer(final Player player) {
		final String key = "view/img/cars/" + player.getColor().toLowerCase() + ".png";
		
		if(cars.get(key) == null) {
			cars.put(key, new ImageIcon(key).getImage());
		}

		return cars.get(key);
	}
	
	public Image getBuildingByHotelAndLevel(final Hotel hotel, final int level) {
		final String key = ("view/img/buildings/" + hotel.getClass().getSimpleName().toLowerCase() + "/" + level + ".png").toLowerCase();
		
		if(buildings.get(key) == null) {
			buildings.put(key, new ImageIcon(key).getImage());
		}

		return buildings.get(key);
	}

	public Image getBank() {
		return bank;
	}

	public Image getMunicipe() {
		return municipe;
	}

	public Image getEntrance() {
		return entrance;
	}
}
