// By Iacon1
// Created 11/27/2021
// An implementation of Diffie-Hellman Key Exchange
// Steps:
// 1. Init
// 2. Send them your initialMix
// 3. Run finalMix with their initialMix
// 4. Use giveKey on your thread

package Modules.BaseModule;

import java.math.BigInteger;
import java.security.Key;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.spec.SecretKeySpec;

import GameEngine.Net.ConnectionPairThread;
import Utils.Logging;
import Utils.MiscUtils;
import Utils.StringEncrypter;

public class DiffieHellman
{
	
	private static final int encodeRadix = 16; // Radix for encoding mixes to strings
	private static final int keyLength = 16; // Key length in bytes
	
	private static BigInteger modulus = null;
	private static BigInteger base = null; // Has to be a primitive root modulo [modulus].
	private static int maxSecret = 0; // b^(s'*s) < 2^(2^32) due to limitations of Java, so s^2 < logb(2) * 2^32 so s < sqrt(logb(2)) * 2^16
	/** Inits modulus & base */
	private static void initPair() 
	{
		// https://www.rfc-editor.org/rfc/rfc3526
/*		modulus = new BigInteger(
				"FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1" +
				"29024E088A67CC74020BBEA63B139B22514A08798E3404DD" +
				"EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245" +
				"E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7ED" +
				"EE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3D" +
				"C2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F" +
				"83655D23DCA3AD961C62F356208552BB9ED529077096966D" +
				"670C354E4ABC9804F1746C08CA18217C32905E462E36CE3B" +
				"E39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9" +
				"DE2BCBF6955817183995497CEA956AE515D2261898FA0510" +
				"15728E5A8AACAA68FFFFFFFFFFFFFFFF", 16);
		base = new BigInteger("2", 16);
*/   
		// https://www.rfc-editor.org/rfc/rfc2409#section-6
/*		modulus = new BigInteger(
				"FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1" +
				"29024E088A67CC74020BBEA63B139B22514A08798E3404DD" +
				"EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245" +
				"E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7ED" +
				"EE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381" +
				"FFFFFFFFFFFFFFFF", 16);
		base = new BigInteger("2", 16); 
*/
		modulus = new BigInteger("2").pow(130).subtract(new BigInteger("5")); // https://primes.utm.edu/lists/2small/100bit.html; We need at least 128 bits of encrypting
		base = new BigInteger("6"); // https://www.wolframalpha.com/widgets/view.jsp?id=ef51422db7db201ebc03c8800f41ba99
		maxSecret = (int) (Math.sqrt(Math.log(2) / Math.log(base.doubleValue())) * Math.pow(2, 16));
	}
	
	private transient int secret = 0; // Transient for security
	private transient Key key = null; // Ditto
	
	/** Generates the secret.
	 * 
	 */
	public void generateSecret()
	{
		Random random = new Random();
		random.setSeed(random.nextInt());
//		secret = new BigInteger(bits, random);
		if (modulus == null) initPair();
		secret = Math.abs(random.nextInt(maxSecret));
	}
	
	/** Generates a mix based off the secret.
	 * 
	 */
	public String initialMix() // b ^ s % m
	{
		if (modulus == null) initPair();
		return base.pow(secret).mod(modulus).toString(encodeRadix);
	}
	
	/** Generates the key based off the other side's mix.
	 * 
	 */
	public void finalMix(String mixString)
	{
		if (mixString == null) return; // TODO why does this happen?
		if (modulus == null) initPair();
		
		BigInteger mix = new BigInteger(mixString, encodeRadix);
		
		BigInteger finalMix = mix.pow(secret).mod(modulus); // b ^ (s' * s) % m
		key = new SecretKeySpec(Arrays.copyOf(finalMix.toByteArray(), keyLength), "AES");
	}
	
	/** Gives the key to the server.
	 * 
	 */
	public void giveKey(ConnectionPairThread thread)
	{
		thread.setKey(key);
	}
}
