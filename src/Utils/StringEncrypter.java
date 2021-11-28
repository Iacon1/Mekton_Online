// By Iacon1
// Created 11/27/2021
// Encrypts strings 
// https://stackoverflow.com/questions/1205135/how-to-encrypt-string-in-java

package Utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public final class StringEncrypter
{
	private static final transient String charset = "UTF-8"; // Transient and final for security
	private static final transient String encodeSet = "ISO-8859-1"; // https://stackoverflow.com/questions/9098022/problems-converting-byte-array-to-string-and-back-to-byte-array
	private static final transient String algorithm = "AES/CBC/PKCS5Padding";

	private static String fromBytes(byte[] bytes)
	{
		return MiscUtils.arrayToString(bytes, " ");
	}
	private static byte[] fromString(String string)
	{
		String[] words = string.split(" ");
		byte[] bytes = new byte[words.length];
		
		for (int i = 0; i < words.length; ++i)
		{
			bytes[i] = (byte) (int) Integer.valueOf(words[i].substring(2), 16);
		}
		
		return bytes;
	}
	private static IvParameterSpec generateIv(byte[] seed, int blockSize) // Generates the iv by hashing seed repeatedly
	{
		byte[] iv = new byte[blockSize];
		
		String hash = Integer.toString(new String(seed).hashCode());
		
		for (int i = 0; i < iv.length; ++i)
		{
			iv[i] = (byte) (hash.hashCode() % 0xFF);
			hash = Integer.toString(new String(hash).hashCode());
		}
		
		return new IvParameterSpec(iv);
	}
	
	private static Cipher getCipher(Key key, boolean encrypt) throws Exception
	{
		Cipher cipher = Cipher.getInstance(algorithm);
		IvParameterSpec ivParams = generateIv(key.getEncoded(), cipher.getBlockSize());
		if (encrypt) cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
		else cipher.init(Cipher.DECRYPT_MODE, key, ivParams);

		return cipher;
	}

	public static String encrypt(String plainText, Key key) throws Exception
	{
		Cipher cipher = getCipher(key, true);
		
		byte[] plainBytes = plainText.getBytes(charset);
		byte[] cipherBytes = null;
		
		cipherBytes = cipher.doFinal(plainBytes);
		cipher = null;
		
		return fromBytes(cipherBytes);
	}
	
	public static String decrypt(String cipherText, Key key) throws Exception
	{
		Cipher cipher = getCipher(key, false);

		byte[] cipherBytes = fromString(cipherText);
		byte[] plainBytes = null;
		
		plainBytes = cipher.doFinal(cipherBytes);
		cipher = null;
		
		return new String(plainBytes, charset);
	}
}
