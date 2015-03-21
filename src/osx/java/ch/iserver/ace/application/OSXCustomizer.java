/*
 * $Id: OSXCustomizer.java 1520 2005-11-18 17:46:41Z sim $
 *
 * ace - a collaborative editor
 * Copyright (C) 2005 Mark Bigler, Simon Raess, Lukas Zbinden
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package ch.iserver.ace.application;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;

/**
 *
 */
public class OSXCustomizer extends ApplicationAdapter implements Customizer {
	
	private ApplicationController controller;
	
	public void init(ApplicationController controller) {
		this.controller = controller;
		Application app = Application.getApplication();
		app.setEnabledPreferencesMenu(true);
		app.addApplicationListener(this);
	}
	
	protected ApplicationController getController() {
		return controller;
	}
	
	public void handleAbout(ApplicationEvent e) {
		e.setHandled(true);
		getController().showAbout();
	}
		
	public void handleOpenFile(ApplicationEvent e) {
		e.setHandled(true);
		getController().openFile(e.getFilename());
	}

	public void handlePreferences(ApplicationEvent e) {
		e.setHandled(true);
		getController().showPreferences();
	}
	
	public void handleQuit(ApplicationEvent e) {
		e.setHandled(false);
		getController().quit();
	}
	
}