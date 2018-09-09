package hackee12.swat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

public class Base64DeflateDecoder implements Decoder
{
	private final int BUFFER_SIZE_IN_BYTES;

	public Base64DeflateDecoder(int bufferSizeInBytes)
	{
		BUFFER_SIZE_IN_BYTES = bufferSizeInBytes;
	}

	@Override
	public String decode(String rawMessage) throws DataFormatException, IOException
	{
		final byte[] bytesIn = parseBase64Binary(rawMessage);

		final Inflater decoder = new Inflater();
		decoder.setInput(bytesIn);

		byte[] bytesOut = new byte[BUFFER_SIZE_IN_BYTES];
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFFER_SIZE_IN_BYTES);
		while (!decoder.finished())
		{
			int bytesToWrite = decoder.inflate(bytesOut);
			int zeroOffset = 0;
			baos.write(bytesOut, zeroOffset, bytesToWrite);
		}
		decoder.end();

		final String clearMessage = baos.toString();
		return clearMessage;
	}
}
