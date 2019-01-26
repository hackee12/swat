package hackee12.swat;

import hackee12.swat.decoder.exception.DecoderException;

public interface Decoder
{
	String decode(String rawMessage) throws DecoderException;
}
