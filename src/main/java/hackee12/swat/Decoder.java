package hackee12.swat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;

public interface Decoder
{
	String decode(String rawMessage) throws IOException, DataFormatException;
}
