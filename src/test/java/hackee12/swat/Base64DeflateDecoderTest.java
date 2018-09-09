package hackee12.swat;

import org.junit.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.zip.DataFormatException;

public class Base64DeflateDecoderTest {

	@Test
	public void decode() throws IOException, DataFormatException {
		String clearMessage = "some clear message";
		String raw = "eJwrzs9NVUjOSU0sUshNLS5OTE8FAED2BuE=";

		for (int bufferSize = 1; bufferSize < 128; bufferSize++) {
			Decoder decoder = new Base64DeflateDecoder(bufferSize);
			Assert.assertEquals(MessageFormat.format("Extract failure with bufferSize={0}",
					bufferSize), clearMessage, decoder.decode(raw));
		}
	}
}
