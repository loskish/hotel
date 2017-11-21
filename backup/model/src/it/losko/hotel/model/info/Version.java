package it.losko.hotel.model.info;

public class Version {
	private final String SEPARATOR;
	
	private final int major;
	private final int minor;
	private final int revision;
	
	protected Version(final String version) {
		SEPARATOR = ".";
		final String[] parts = version.split("\\" + SEPARATOR);
		
		this.major = Integer.valueOf(parts[0]);
		this.minor = Integer.valueOf(parts[1]);
		this.revision = Integer.valueOf(parts[2]);
	}
	
	/**
	 * Returns the release's major number
	 * 
	 * @return the release's major number
	 */
	public int getMajor() {
		return major;
	}

	/**
	 * Returns the release's minor number
	 * 
	 * @return the release's minor number
	 */
	public int getMinor() {
		return minor;
	}

	/**
	 * Returns the release's revision number
	 * 
	 * @return the release's revision number
	 */
	public int getRevision() {
		return revision;
	}
	
	/**
	 * Returns the version number
	 * 
	 * @return the version number
	 */
	public String getVersionNumber() {
		return major + SEPARATOR + minor + SEPARATOR + revision;
	}
	
	@Override
	public String toString() {
		return getVersionNumber();
	}
}
