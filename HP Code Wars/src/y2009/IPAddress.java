package challenge;

import java.util.Arrays;

/**
 * Represents a single IPv4 address.
 * 
 * @author zozo
 * 
 */
public class IPAddress {

	private static final int NUM_OCTETS = 4;
	private int[] octets = new int[NUM_OCTETS];

	/**
	 * Parses an IP address from a string. Note that since Java has no unsigned
	 * byte type, the data type is int.
	 * 
	 * @param ip
	 *            an IP address as a String, e.g. "1.2.3.4"
	 * @throws NumberFormatException
	 *             if the string is not well-formatted.
	 */
	public IPAddress(String ip) {
		String[] address = ip.split("\\.");
		for (int i = 0; i < NUM_OCTETS; ++i)
			octets[i] = Integer.parseInt(address[i]);
	}

	public IPAddress(int[] octets) {
		this.octets = octets;
	}

	/**
	 * Performs a bitwise and of each octet of this IP with another. i.e.
	 * this.and(other) == this & other
	 * 
	 * @param other
	 *            another IP address
	 * @return this & other
	 */
	public IPAddress and(IPAddress other) {
		int[] result = new int[NUM_OCTETS];
		for (int i = 0; i < NUM_OCTETS; ++i)
			result[i] = octets[i] & other.octets[i];
		return new IPAddress(result);
	}

	/**
	 * Compares this IP address to another based on its octets.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IPAddress)
			return Arrays.equals(octets, ((IPAddress) obj).octets);
		return super.equals(obj);
	}
}
