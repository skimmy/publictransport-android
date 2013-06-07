package com.skimmy.publictransit.interfaces;

/**
 * This interface represents a public <i>front end</i> for the location service.
 * 
 * @author Michele Schimd
 * 
 */
public interface LocationServiceManager {
	/**
	 * Starts the location service.
	 * 
	 * @return <code>true</code> if the service has been correctly started
	 */
	public boolean startLocationService();

	/**
	 * Stops the location service.
	 * 
	 * @return always <code>false</code>
	 */
	public boolean stopLocationService();

	/**
	 * Checks whether the service is running or is stopped
	 * 
	 * @return <code>true</code> if the service is running <code>false</code>
	 *         otherwise
	 */
	public boolean isServiceRunning();

}
