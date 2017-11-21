package it.losko.hotel.view.gui.resources;

import it.losko.hotel.model.board.Boomerang;
import it.losko.hotel.model.board.Fujihama;
import it.losko.hotel.model.board.Hotel;
import it.losko.hotel.model.board.LEtoile;
import it.losko.hotel.model.board.President;
import it.losko.hotel.model.board.Royal;
import it.losko.hotel.model.board.Safari;
import it.losko.hotel.model.board.TajiMahal;
import it.losko.hotel.model.board.Waikiki;
import it.losko.hotel.model.player.Player;

import java.util.HashMap;
import java.util.Map;


public class Coordinates {
	
	private final Map<String, int[]> parkCoordinates;
	private final Map<String, int[][]> buildings;
	private final int[][] carCoordinates;
	private final int[][] innerEntrances;
	private final int[][] outerEntrances;
	private final int NC = Integer.MIN_VALUE;
	
	public Coordinates() {
		carCoordinates = new int[][] {
				 {725, 285}
		    	,{746, 370}
		    	,{733, 436}
		    	,{695, 485}
		    	,{637, 515}
		    	,{576, 515}
		    	,{522, 490}
		    	,{457, 483}
		    	,{401, 506}
		    	,{341, 494}
		    	,{293, 447}
		    	,{240, 413}
		    	,{176, 394}
		    	,{112, 386}
		    	,{58, 354}
		    	,{30, 297}
		    	,{37, 229}
		    	,{79, 176}
		    	,{142, 158}
		    	,{206, 174}
		    	,{266, 194}
		    	,{326, 214}
		    	,{392, 228}
		    	,{443, 188}
		    	,{452, 121}
		    	,{474, 54}
		    	,{549, 34}
		    	,{601, 82}
		    	,{605, 156}
		    	,{606, 225}
		    	,{641, 271}
		};
		
		parkCoordinates = new HashMap<String, int[]>();
		parkCoordinates.put("yellow", new int[] {694, 224});
		parkCoordinates.put("green", new int[] {719, 224});
		parkCoordinates.put("blue", new int[] {744, 224});
		parkCoordinates.put("red", new int[] {769, 224});
		
		buildings = new HashMap<String, int[][]>();
		buildings.put(Fujihama.class.getSimpleName().toLowerCase(), new int[][] {
				 {583, 385}
		    	,{626, 357}
		    	,{538, 363}
		    	,{530, 355}
			}
		);
		buildings.put(Boomerang.class.getSimpleName().toLowerCase(), new int[][] {
		    	 {665, 537}
		    	,{544, 492}
			}
		);
		buildings.put(LEtoile.class.getSimpleName().toLowerCase(), new int[][] {
				 {343, 396}
			    ,{430, 370}
			    ,{434, 305}
			    ,{334, 282}
			    ,{310, 340}
			    ,{351, 202}
			}
		);
		buildings.put(President.class.getSimpleName().toLowerCase(), new int[][] {
				 {-40, 560}
			    ,{ 28, 562}
			    ,{ 96, 562}
			    ,{-25, 501}
			    ,{ -4, 422}
			}
		);
		buildings.put(Royal.class.getSimpleName().toLowerCase(), new int[][] {
				 {178, 262}
			    ,{176, 223}
			    ,{226, 278}
			    ,{110, 238}
			    ,{ 74, 230}
			}
		);
		buildings.put(Waikiki.class.getSimpleName().toLowerCase(), new int[][] {
				 {196,  -2}
			    ,{112,   1}
			    ,{ 64,  26}
			    ,{ 37,  64}
			    ,{ 11,  99}
			    ,{ 74,  43}
			}
		);
		buildings.put(TajiMahal.class.getSimpleName().toLowerCase(), new int[][] {
				 {340,   4}
			    ,{387,  23}
			    ,{307,  24}
			    ,{249,  38}
			}
		);
		buildings.put(Safari.class.getSimpleName().toLowerCase(), new int[][] {
				 {693,   2}
			    ,{747,  57}
			    ,{747, 128}
			    ,{605,   8}
			}
		);
		
		// Entrances
		innerEntrances = new int[][] {
			//  x,	 y, theta
			 { NC,  NC,  NC} // There will never be an entrance here
			,{728, 365,   0}
		    ,{724, 422,  24}
		    ,{700, 465,  50}
		    ,{657, 494,  77}
		    ,{609, 501, 104}
		    ,{557, 480, 115}
		    ,{ NC,  NC,  NC} // There will never be an entrance here
		    ,{422, 485,  77}
		    ,{380, 487, 125}
		    ,{336, 445, 130}
		    ,{276, 403, 116}
		    ,{207, 378, 100}
		    ,{145, 373, 106}
		    ,{102, 354, 137}
		    ,{ 75, 315, 170}
		    ,{ 72, 266, 204}
		    ,{ 99, 223, 235}
		    ,{142, 202, 270}
		    ,{197, 212, 289}
		    ,{257, 232, 289}
		    ,{317, 252, 289}
		    ,{397, 273, 260}
		    ,{478, 225, 207}
		    ,{496, 148, 181}
		    ,{ NC,  NC,  NC} // There will never be an entrance here
		    ,{ NC,  NC,  NC} // There will never be an entrance here
		    ,{ NC,  NC,  NC} // There will never be an entrance here
		    ,{586, 155,   0}
		    ,{586, 233, 345}
		    ,{ NC,  NC,  NC} // There will never be an entrance here
		};
		outerEntrances = new int[][] {
			//  x,	 y, theta
			 { NC,  NC,  NC} // There will never be an entrance here
			,{ NC,  NC,  NC} // There will never be an entrance here
		    ,{769, 471, 204}
		    ,{719, 529, 230}
		    ,{645, 560, 257}
		    ,{570, 555, 284}
		    ,{ NC,  NC,  NC} // There will never be an entrance here
		    ,{ NC,  NC,  NC} // There will never be an entrance here
		    ,{ NC,  NC,  NC} // There will never be an entrance here
		    ,{326, 524, 305}
		    ,{277, 475, 310}
		    ,{228, 448, 296}
		    ,{171, 435, 280}
		    ,{103, 425, 286}
		    ,{ 40, 377, 317}
		    ,{ 10, 300, 350}
		    ,{ 27, 217,  24}
		    ,{ 87, 156,  55}
		    ,{167, 140,  90}
		    ,{241, 161, 109}
		    ,{301, 182, 109}
		    ,{360, 202, 109}
		    ,{413, 207,  80}
		    ,{435, 175,  27}
		    ,{434, 121,   1}
		    ,{476,  34,  45}
		    ,{585,  21, 108}
		    ,{646,  93, 158}
		    ,{648, 181, 180}
		    ,{653, 241, 165}
		    ,{681, 262, 118}
		};
	}
	
	public static class XY {
		private final int x;
		private final int y;
		
		public XY(final int x, final int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
	
	public static class XYT extends XY {
		private final int theta;
		
		public XYT(final int x, final int y, final int theta) {
			super(x, y);
			this.theta = theta;
		}
		
		public int getTheta() {
			return theta;
		}
	}
	
	// Public methods
	public XY getCar(final Player player) {
		return new XY(getCarX(player), getCarY(player));
	}
	
	public XY getBuilding(final Hotel hotel, final int level) {
		return new XY(getBuildingX(hotel, level), getBuildingY(hotel, level));
	}
	
	public XY getMunicipe() {
		return new XY(521, 110);
	}
	
	public XY getBank() {
		return new XY(447, 565);
	}
	
	public XYT getEntrance(final boolean inner, final int position) {
		return new XYT(getEntranceX(inner, position), getEntranceY(inner, position), getEntranceTheta(inner, position));
	}
	
	// Private methods
	private int getCarX(final Player player) {
		int square = player.getPositionOnBoard();
		if(square > 0) {
			return carCoordinates[square - 1][0];
		} else {
			return parkCoordinates.get(player.getColor())[0];
		}
	}
	
	private int getCarY(final Player player) {
		int square = player.getPositionOnBoard();
		if(square > 0) {
			return carCoordinates[square - 1][1];
		} else {
			return parkCoordinates.get(player.getColor())[1];
		}
	}
	
	private int getBuildingX(final Hotel hotel, final int level) {
		return buildings.get(hotel.getClass().getSimpleName().toLowerCase())[level - 1][0];
	}
	
	private int getBuildingY(final Hotel hotel, final int level) {
		return buildings.get(hotel.getClass().getSimpleName().toLowerCase())[level - 1][1];
	}
	
	private int getEntranceX(final boolean inner, final int position) {
		return inner ? innerEntrances[position - 1][0] : outerEntrances[position - 1][0];
	}
	
	private int getEntranceY(final boolean inner, final int position) {
		return inner ? innerEntrances[position - 1][1] : outerEntrances[position - 1][1];
	}
	
	private int getEntranceTheta(final boolean inner, final int position) {
		return inner ? innerEntrances[position - 1][2] : outerEntrances[position - 1][2];
	}
}