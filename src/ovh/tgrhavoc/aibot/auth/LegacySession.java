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
package ovh.tgrhavoc.aibot.auth;

import org.apache.commons.lang3.StringUtils;

public class LegacySession extends Session {
	private final String sessionId;

	public LegacySession(String username, String password, String sessionId) {
		super(username, password);
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	@Override
	public boolean isValidForAuthentication() {
		return StringUtils.isNotBlank(sessionId);
	}

	@Override
	public String toString() {
		return "LegacySession{username=" + getUsername() + ",password=" + getPassword() + ",session=" + sessionId + "}";
	}
}
