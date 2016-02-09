package challenge;

/**
 * Represents a routable IP network, with a gateway IP address, netmask, and
 * identifying name.
 * 
 * A network can be described by an IP address and a netmask. A netmask is a
 * filter that allows you to determine if an IP is part of a network.
 * 
 * @author zozo
 * 
 */
public class Network {

	private IPAddress network, netmask;
	private String name;

	public Network(IPAddress network, IPAddress netmask, String name) {
		this.network = network;
		this.netmask = netmask;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Determines if an IP address is part of this network.
	 * 
	 * @param address
	 *            a candidate IP address
	 * @return true if and only if address & netmask == network
	 */
	public boolean contains(IPAddress address) {
		return address.and(netmask).equals(network);
	}

}
