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

import java.lang.reflect.Method;

public final class MultiEventException extends Exception {
	private static final long serialVersionUID = -1392819062389929428L;

	private final EventException[] exceptions;

	public MultiEventException(EventException... exceptions) {
		this.exceptions = exceptions.clone();
	}

	public MultiEventException(String message, EventException... exceptions) {
		super(message);

		this.exceptions = exceptions.clone();
	}

	public EventException[] getExceptions() {
		return exceptions.clone();
	}

	public static final class EventException extends RuntimeException {
		private static final long serialVersionUID = -910511146985345487L;

		private final Event event;
		private final EventListener listener;
		private final Method method;

		public EventException(Event event, Throwable cause, EventListener listener, Method method) {
			super(cause);

			this.event = event;
			this.listener = listener;
			this.method = method;
		}

		public EventException(Event event, String message, Throwable cause, EventListener listener, Method method) {
			super(message, cause);

			this.event = event;
			this.listener = listener;
			this.method = method;
		}

		public Event getEvent() {
			return event;
		}

		public EventListener getListener() {
			return listener;
		}

		public Method getMethod() {
			return method;
		}
	}
}
