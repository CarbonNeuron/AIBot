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

import java.math.BigInteger;
import java.util.UUID;

public class YggdrasilSession extends Session {
	private final BigInteger accessToken;
	private final UUID clientToken;
	private final Profile[] availableProfiles;
	private final Profile selectedProfile;

	public YggdrasilSession(String username, String password, BigInteger accessToken, UUID clientToken, Profile selectedProfile, Profile... availableProfiles) {
		super(username, password);

		this.accessToken = accessToken;
		this.clientToken = clientToken;
		this.availableProfiles = availableProfiles.clone();
		this.selectedProfile = selectedProfile;
	}

	public BigInteger getAccessToken() {
		return accessToken;
	}

	public UUID getClientToken() {
		return clientToken;
	}

	public Profile[] getAvailableProfiles() {
		return availableProfiles.clone();
	}

	public Profile getSelectedProfile() {
		return selectedProfile;
	}

	@Override
	public boolean isValidForAuthentication() {
		return accessToken != null && selectedProfile != null;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("YggdrasilSession{");
		buffer.append("username=").append(getUsername()).append(',');
		buffer.append("password=").append(getPassword()).append(',');
		buffer.append("accessToken=").append(accessToken.toString(16)).append(',');
		buffer.append("clientToken=").append(clientToken).append(',');
		buffer.append("selectedProfile=").append(selectedProfile.toString()).append(',');
		buffer.append("availableProfiles=[");
		for(int i = 0; i < availableProfiles.length; i++) {
			if(i != 0)
				buffer.append(',');
			buffer.append(availableProfiles[i].toString());
		}
		buffer.append("]}");
		return buffer.toString();
	}

	public static final class Profile {
		private final String id, name;

		public Profile(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return "YggdrasilSession.Profile{id=" + id + ",name=" + name + "}";
		}
	}
}
