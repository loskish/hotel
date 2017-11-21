package it.losko.hotel.model.info;

public class Info {
	private final String name;
	private final Version version;
	private final String author;
	
	private Info() {
		name = "Hotel";
		version = new Version("0.0.1");
		author = "LoSko";
	}
	
	/**
	 * This inner-class holds the singleton instance
	 * This allows to implement the Singleton pattern
	 * without use of special constructs like synchronized
	 */
	private static class SingletonHolder { 
		private static final Info singleton = new Info();
	}
	
	/**
	 * Returns a unique instance of the class
	 * 
	 * @return a unique instance of the class
	 */
	public static Info getSingleton() {
	     return SingletonHolder.singleton;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * Returns the application's name
	 * 
	 * @return the application's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the application's version
	 * 
	 * @return the application's version
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * Returns the application's author
	 * 
	 * @return the application's author
	 */
	public String getAuthor() {
		return author;
	}
}
