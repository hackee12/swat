package hackee12.swat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.zip.Deflater;

public class Base64DeflateDecoderTest
{
	private static final int MIN_TEST_BUFFER_LENGTH = 1;
	private static final int MAX_TEST_BUFFER_LENGTH = 100;
	private String clearMessage;
	private String rawMessage;

	@Before
	public void setup()
	{
		this.clearMessage = "some clear test message";

		final byte[] bytesIn = clearMessage.getBytes();
		final byte[] bytesOut = new byte[MAX_TEST_BUFFER_LENGTH];
		final Deflater compressor = new Deflater();
		compressor.setInput(bytesIn);
		compressor.finish();
		final int compressedLength = compressor.deflate(bytesOut);
		final byte[] bytesCompressed = Arrays.copyOf(bytesOut, compressedLength);

		this.rawMessage = DatatypeConverter.printBase64Binary(bytesCompressed);
	}

	@Test
	public void decode()
	{
		for (int i = MIN_TEST_BUFFER_LENGTH; i <= MAX_TEST_BUFFER_LENGTH; i++) {
			final Decoder decoder = new Base64DeflateDecoder(i);
			Assert.assertEquals(MessageFormat.format("Extract failure with bufferLength={0}", i),
					clearMessage, decoder.decode(rawMessage));
		}
	}
}
