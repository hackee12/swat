package hackee12.swat;

import org.junit.*;

import javax.xml.bind.DatatypeConverter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.zip.DataFormatException;
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

		byte[] bytesIn = clearMessage.getBytes();
		byte[] bytesOut = new byte[MAX_TEST_BUFFER_LENGTH];
		Deflater compressor = new Deflater();
		compressor.setInput(bytesIn);
		compressor.finish();
		int compressedLength = compressor.deflate(bytesOut);
		byte[] bytesCompressed = Arrays.copyOf(bytesOut, compressedLength);

		this.rawMessage = DatatypeConverter.printBase64Binary(bytesCompressed);
	}

	@Test
	public void decode() throws DataFormatException
	{
		for (int i = MIN_TEST_BUFFER_LENGTH; i <= MAX_TEST_BUFFER_LENGTH; i++) {
			Decoder decoder = new Base64DeflateDecoder(i);
			Assert.assertEquals(MessageFormat.format("Extract failure with bufferLength={0}", i),
					clearMessage, decoder.decode(rawMessage));
		}
	}
}
