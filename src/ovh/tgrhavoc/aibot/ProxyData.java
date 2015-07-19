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
package ovh.tgrhavoc.aibot;

import org.apache.http.HttpHost;

public final class ProxyData {
	private final HttpHost host;
	private final ProxyType type;

	public ProxyData(String hostName, int port, ProxyType type) {
		host = new HttpHost(hostName, port);
		this.type = type;
	}

	public String getHostName() {
		return host.getHostName();
	}

	public int getPort() {
		return host.getPort();
	}

	public HttpHost getHost() {
		return host;
	}

	public ProxyType getType() {
		return type;
	}

	public enum ProxyType {
		HTTP,
		SOCKS,
		HTTP_CONNECT
	}
}
