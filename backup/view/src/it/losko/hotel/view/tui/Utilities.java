package it.losko.hotel.view.tui;


/**
 * This class provides access to the defined application loggers.
 * 
 * @author losko
 */
public class Utilities {
	
	private Utilities() {
	}
	
	private static class SingletonHolder { 
		private static final Utilities singleton = new Utilities();
	}
	
	public static Utilities getSingleton() {
	     return SingletonHolder.singleton;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public String fillLine(final String toFormat, final String initFiller, final String filler) {
		final StringBuffer out = new StringBuffer();
		final int nRows = toFormat.length() / (TUI.LINE_SIZE - 4);
		for(int i = 0; i <= nRows; i++) {
			final StringBuffer sb = new StringBuffer();
			sb.append("#");
			sb.append(initFiller);
			sb.append(toFormat.substring(i * (TUI.LINE_SIZE - 5), Math.min(toFormat.length(), (i + 1) * (TUI.LINE_SIZE - 5))));
			
			for(int y = sb.length(); y < TUI.LINE_SIZE - 2; y++) {
				sb.append(filler);
			}
			sb.append("#");
			if(i != nRows)
				sb.append("\n");
			out.append(sb);
		}
		
		return out.toString();
	}
	
	public String fillLine(final String toFormat, final String filler) {
		return fillLine(toFormat, filler, filler);
	}
}
