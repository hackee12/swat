package hackee12.swat;

import hackee12.swat.decoder.exception.DecoderException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

public class Base64GzipDecoder implements Decoder
{
	@Override
	public String decode(String rawMessage)
	{
		final byte[] rawBytes = Base64.getDecoder().decode(rawMessage);
		final StringBuilder stringBuilder = new StringBuilder();
		try (final ByteArrayInputStream rawByteArrayInputStream = new ByteArrayInputStream(rawBytes);
			final GZIPInputStream gzipInputStream = new GZIPInputStream(rawByteArrayInputStream);
			final Scanner scanner = new Scanner(gzipInputStream))
		{
			while (scanner.hasNextLine())
			{
				stringBuilder.append(scanner.nextLine());
			}
			return stringBuilder.toString();
		}
		catch (IOException e)
		{
			throw new DecoderException(e);
		}
	}
}
