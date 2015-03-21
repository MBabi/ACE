/*
 * $Id: PropertyChangeHashMapImpl.java 2720 2006-02-23 14:21:47Z pyron $
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.SwingUtilities;



public class PropertyChangeHashMapImpl extends HashMap implements PropertyChangeHashMap, PropertyChangeListener {

	private PropertyChangeSupport propertyChangeSupport;
	
	public PropertyChangeHashMapImpl() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
			}
		});
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
	}
	
	public Object put(Object key, Object value) {
		if(value instanceof PropertyChangeCaretHandler) {
			((PropertyChangeCaretHandler)value).addPropertyChangeListener(this);
		}
		return super.put(key, value);
	}
	
	public Object remove(Object key) {
		if(super.get(key) instanceof PropertyChangeCaretHandler) {
			((PropertyChangeCaretHandler)super.get(key)).removePropertyChangeListener(this);
		}
		return super.remove(key);
	}
	
}

