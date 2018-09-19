package hackee12.swat;

import java.util.zip.DataFormatException;

public interface Decoder
{
	String decode(String rawMessage) throws DataFormatException;
}
