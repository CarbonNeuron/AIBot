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

public abstract class Session {
	private final String username, password;

	public Session(String username, String password) {
		if(username == null)
			throw new NullPointerException("Null username");
		if(StringUtils.isBlank(username))
			throw new IllegalArgumentException("Empty username");
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isValidForLogin() {
		return StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password);
	}

	public abstract boolean isValidForAuthentication();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{username=" + username + ",password=" + password + "}";
	}
}
