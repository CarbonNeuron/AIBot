/*******************************************************************************
 *     Copyright (C) 2015 Jordan Dalton (jordan.8474@gmail.com)
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *******************************************************************************/
package ovh.tgrhavoc.aibot.event;

import java.lang.reflect.*;
import java.util.*;

final class EventSender {
	private final Map<EventListener, List<Method>> handlers;
	private final Class<? extends Event> listenerEventClass;

	public EventSender(Class<? extends Event> listenerEventClass) {
		handlers = new HashMap<EventListener, List<Method>>();
		this.listenerEventClass = listenerEventClass;
	}

	public synchronized void addHandler(EventListener listener, Method method) {
		List<Method> methods = handlers.get(listener);
		if(methods == null) {
			methods = new ArrayList<>();
			handlers.put(listener, methods);
		}
		methods.add(method);
	}

	public synchronized void unregisterListener(EventListener listener) {
		handlers.remove(listener);
	}

	public synchronized List<EventListener> getListeners() {
		return Collections.unmodifiableList(new ArrayList<>(handlers.keySet()));
	}

	public synchronized void sendEvent(Event event) {
		Class<?> eventClass = event.getClass();
		if(!listenerEventClass.isAssignableFrom(eventClass))
			return;
		List<EventListener> listeners = new ArrayList<>(handlers.keySet());
		for(EventListener listener : listeners) {
			List<Method> methods = handlers.get(listener);
			if(methods == null)
				continue;
			for(Method method : methods) {
				try {
					boolean accessible = method.isAccessible();
					if(!accessible)
						method.setAccessible(true);
					method.invoke(listener, event);
					if(!accessible)
						method.setAccessible(false);
				} catch(InvocationTargetException exception) {
					exception.getCause().printStackTrace();
				} catch(Throwable exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	public Class<? extends Event> getListenerEventClass() {
		return listenerEventClass;
	}
}
