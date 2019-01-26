package hackee12.swat;

import hackee12.swat.decoder.exception.DecoderException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

public class Base64DeflateDecoder implements Decoder
{
	private static final int DEFAULT_BUFFER_SIZE_IN_BYTES = 1024;

	private final int BUFFER_SIZE_IN_BYTES;

	public Base64DeflateDecoder(int bufferSizeInBytes)
	{
		BUFFER_SIZE_IN_BYTES = bufferSizeInBytes;
	}

	public Base64DeflateDecoder()
	{
		this(DEFAULT_BUFFER_SIZE_IN_BYTES);
	}

	@Override
	public String decode(String rawMessage)
	{
		final String result;
		final byte[] bytesIn = parseBase64Binary(rawMessage);

		final Inflater decoder = new Inflater();
		decoder.setInput(bytesIn);

		final byte[] bytesOut = new byte[BUFFER_SIZE_IN_BYTES];
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(BUFFER_SIZE_IN_BYTES))
		{
			while (!decoder.finished())
			{
				int bytesToWrite = decoder.inflate(bytesOut);
				int zeroOffset = 0;
				byteArrayOutputStream.write(bytesOut, zeroOffset, bytesToWrite);
			}
			decoder.end();
			result = byteArrayOutputStream.toString();
		}
		catch (DataFormatException | IOException e)
		{
			throw new DecoderException(e);
		}
		return result;
	}
}
