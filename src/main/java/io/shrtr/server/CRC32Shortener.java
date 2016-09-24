package io.shrtr.server;
import java.util.zip.CRC32;

import com.google.common.base.Charsets;
public class CRC32Shortener implements Shortener {
	
	@Override
	public String shorten(String url) {
		CRC32 crc = new CRC32();
		crc.update(url.getBytes(Charsets.UTF_8));
		long value = crc.getValue();
		return Long.toHexString(value);
	}

}
