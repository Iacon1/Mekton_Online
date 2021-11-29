// By Iacon1
// Created 11/27/2021
// Encrypts strings 
// https://stackoverflow.com/questions/1205135/how-to-encrypt-string-in-java

package Utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

public final class StringCipher
{
	private static final transient String charset = "UTF-8"; // Transient and final for security
	private static final transient String algorithm = "AES/CBC/PKCS5Padding";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

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

	public void setKey(Key key) throws Exception
	{
		encryptCipher = Cipher.getInstance(algorithm);
		decryptCipher = Cipher.getInstance(algorithm);
		
		IvParameterSpec ivParamsE = generateIv(key.getEncoded(), encryptCipher.getBlockSize());
		IvParameterSpec ivParamsD = generateIv(key.getEncoded(), decryptCipher.getBlockSize());
		encryptCipher.init(Cipher.ENCRYPT_MODE, key, ivParamsE);
		decryptCipher.init(Cipher.DECRYPT_MODE, key, ivParamsD);
	}
	
	public byte[] encrypt(String plainText) throws Exception
	{
		byte[] plainBytes = plainText.getBytes(charset);
		byte[] cipherBytes = null;
		
		cipherBytes = encryptCipher.doFinal(plainBytes);
		
		return cipherBytes;
	}
	
	public byte[] encrypt(String plainText, Key key) throws Exception
	{
		setKey(key);
		return encrypt(plainText);
	}
	
	
	public String decrypt(byte[] cipherBytes) throws Exception
	{
		byte[] plainBytes = null;
		
		plainBytes = decryptCipher.doFinal(cipherBytes);
		
		return new String(plainBytes, charset);
	}
	public String decrypt(byte[] cipherBytes, Key key) throws Exception
	{
		setKey(key);
		return decrypt(cipherBytes);
	}
}
