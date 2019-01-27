package hackee12.swat;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

public class Base64GzipDecoderTest {

    @Test
    public void decodeSystemEncodedMessage() {
        final String rawMessage = "H4sICGw8TVwAA3Rlc3QudHh0ACvOz01VSM5JTSxSKEmtKOECAAAepLkQAAAA";
        final Base64GzipDecoder decoder = new Base64GzipDecoder();
        Assert.assertEquals("Decoder failure", "some clear text", decoder.decode(rawMessage));
    }

    @Test
    public void decodeJavaEncodedMessage() throws IOException {

        final String testMessage = "some clear text";

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gzipOutputStream.write(testMessage.getBytes());
        gzipOutputStream.close();

        final byte[] gzipBytes = byteArrayOutputStream.toByteArray();
        final byte[] base64GzipBytes = Base64.getEncoder().encode(gzipBytes);

        final String rawMessage = new String(base64GzipBytes);
        final Base64GzipDecoder decoder = new Base64GzipDecoder();
        Assert.assertEquals("Decoder failure", testMessage, decoder.decode(rawMessage));
    }
}