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
package ovh.tgrhavoc.aibot.nbt;

import java.io.*;
import java.util.zip.*;

public class CompressedStreamTools {
	public CompressedStreamTools() {
	}

	/**
	 * Load the gzipped compound from the inputstream.
	 */
	public static NBTTagCompound readCompressed(InputStream par0InputStream)
			throws IOException {
		DataInputStream datainputstream = new DataInputStream(
				new BufferedInputStream(new GZIPInputStream(par0InputStream)));

		try {
			NBTTagCompound nbttagcompound = read(datainputstream);
			return nbttagcompound;
		} finally {
			datainputstream.close();
		}
	}

	/**
	 * Write the compound, gzipped, to the outputstream.
	 */
	public static void writeCompressed(NBTTagCompound par0NBTTagCompound,
			OutputStream par1OutputStream) throws IOException {
		DataOutputStream dataoutputstream = new DataOutputStream(
				new GZIPOutputStream(par1OutputStream));

		try {
			write(par0NBTTagCompound, dataoutputstream);
		} finally {
			dataoutputstream.close();
		}
	}

	public static NBTTagCompound decompress(byte par0ArrayOfByte[])
			throws IOException {
		DataInputStream datainputstream = new DataInputStream(
				new BufferedInputStream(new GZIPInputStream(
						new ByteArrayInputStream(par0ArrayOfByte))));

		try {
			NBTTagCompound nbttagcompound = read(datainputstream);
			return nbttagcompound;
		} finally {
			datainputstream.close();
		}
	}

	public static byte[] compress(NBTTagCompound par0NBTTagCompound)
			throws IOException {
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		DataOutputStream dataoutputstream = new DataOutputStream(
				new GZIPOutputStream(bytearrayoutputstream));

		try {
			write(par0NBTTagCompound, dataoutputstream);
		} finally {
			dataoutputstream.close();
		}

		return bytearrayoutputstream.toByteArray();
	}

	public static void safeWrite(NBTTagCompound par0NBTTagCompound,
			File par1File) throws IOException {
		File file = new File((new StringBuilder())
				.append(par1File.getAbsolutePath()).append("_tmp").toString());

		if(file.exists()) {
			file.delete();
		}

		write(par0NBTTagCompound, file);

		if(par1File.exists()) {
			par1File.delete();
		}

		if(par1File.exists()) {
			throw new IOException((new StringBuilder())
					.append("Failed to delete ").append(par1File).toString());
		} else {
			file.renameTo(par1File);
			return;
		}
	}

	public static void write(NBTTagCompound par0NBTTagCompound, File par1File)
			throws IOException {
		DataOutputStream dataoutputstream = new DataOutputStream(
				new FileOutputStream(par1File));

		try {
			write(par0NBTTagCompound, dataoutputstream);
		} finally {
			dataoutputstream.close();
		}
	}

	public static NBTTagCompound read(File par0File) throws IOException {
		if(!par0File.exists()) {
			return null;
		}

		DataInputStream datainputstream = new DataInputStream(
				new FileInputStream(par0File));

		try {
			NBTTagCompound nbttagcompound = read(datainputstream);
			return nbttagcompound;
		} finally {
			datainputstream.close();
		}
	}

	/**
	 * Reads from a CompressedStream.
	 */
	public static NBTTagCompound read(DataInput par0DataInput)
			throws IOException {
		NBTBase nbtbase = NBTBase.readNamedTag(par0DataInput);

		if(nbtbase instanceof NBTTagCompound) {
			return (NBTTagCompound) nbtbase;
		} else {
			throw new IOException("Root tag must be a named compound tag");
		}
	}

	public static void write(NBTTagCompound par0NBTTagCompound,
			DataOutput par1DataOutput) throws IOException {
		NBTBase.writeNamedTag(par0NBTTagCompound, par1DataOutput);
	}
}
