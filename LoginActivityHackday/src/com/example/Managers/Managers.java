package com.example.Managers;

import com.example.net.LoginWebServiceManager;

public class Managers {
	
	private static final LoginWebServiceManager MainWebServiceManager = new LoginWebServiceManager();
	/**
	 * @return The main {@link BHWebServiceManager}
	 */
	public static LoginWebServiceManager getMainWebServiceManager() { return MainWebServiceManager; }
	

}
