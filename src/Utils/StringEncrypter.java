// By Iacon1
// Created 11/27/2021
// Encrypts strings 
// https://stackoverflow.com/questions/1205135/how-to-encrypt-string-in-java

package Utils;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public class StringEncrypter
{
	private static final transient String charset = "UTF-8"; // Transient and final for security
	private static final transient String encodeSet = "ISO-8859-1"; // https://stackoverflow.com/questions/9098022/problems-converting-byte-array-to-string-and-back-to-byte-array
	private static final transient String algorithm = "AES/CBC/PKCS5Padding";

	private static IvParameterSpec generateIv(byte[] seed, int blockSize) // Generates the iv by hashing ivSeed repeatedly
	{
		byte[] iv = new byte[blockSize];
		SecureRandom random = new SecureRandom(seed);
		random.nextBytes(iv);
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
		Logging.logNotice("Encrypting \"" + plainText + "\"");
		Cipher cipher = getCipher(key, true);
		
		byte[] plainBytes = plainText.getBytes(charset);
		byte[] cipherBytes = null;
		try {cipherBytes = cipher.doFinal(plainBytes);}
		catch (BadPaddingException e)
		{
			Logging.logException(e);
		}
		return new String(cipherBytes, encodeSet);
	}
	
	public static String decrypt(String cipherText, Key key) throws Exception
	{
		Cipher cipher = getCipher(key, false);

		byte[] cipherBytes = cipherText.getBytes(encodeSet);
		byte[] plainBytes = null;
		try {plainBytes = cipher.doFinal(cipherBytes);}
		catch (BadPaddingException e)
		{
			Logging.logException(e);
		}
		
		return new String(plainBytes, charset);
	}
}
