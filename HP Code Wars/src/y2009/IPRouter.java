package challenge;

import java.io.*;
import java.util.ArrayList;

/**
 * An IP address acts as a mailing address for your computer. Your computer
 * usually exists on a local network with other computers. In order to talk to
 * computers outside of your local network, a routing device is needed. Many of
 * you have one of these in your home that allows you access to the web and
 * communicate with as many computers as you want.
 * 
 * Communications between computers (such as accessing a web page) are made up
 * of a series of packets (small chunks of data). Each packet contains a
 * destination IP address. A router keeps a list of networks. The router
 * compares this list to the IP address in each packet. When it finds a match,
 * the packet is 'routed' to that network. There is also a default route where a
 * packet is sent when it doesn't match any of the known networks. The default
 * route usually sends the packet to another router that will know what to do
 * with the packet.
 * 
 * 
 * @author zozo
 * 
 */
public class IPRouter {
	/**
	 * Location of the routing table file.
	 */
	private static final String ROUTING_TABLE_FILE = "routingtable.txt";

	private ArrayList<Network> networks = new ArrayList<Network>();
	private String defaultNet;

	/**
	 * Loads the routing table from a text file. The file must be tab-delimited,
	 * with three columns: Network, Netmask, Network Name.
	 * 
	 * @param routingTableFile
	 *            file name
	 * @throws IOException
	 *             if the file could not be found or read
	 */
	public IPRouter(String routingTableFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(routingTableFile));
		while (br.ready()) {
			String[] fields = br.readLine().split("\t");
			String network = fields[0];
			if (network.equals("default")) {
				defaultNet = fields[2];
			} else {
				IPAddress netIP = new IPAddress(network);
				IPAddress netmask = new IPAddress(fields[1]);
				String name = fields[2];
				networks.add(new Network(netIP, netmask, name));
			}
		}
	}

	public static void main(String[] args) {
		try {
			IPRouter ipr = new IPRouter(ROUTING_TABLE_FILE);
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader stdin = new BufferedReader(isr);
			while (stdin.ready()) {
				String ip = stdin.readLine();
				System.out.println(ipr.route(ip));
			}

		} catch (FileNotFoundException e) {
			System.err.println("Could not find routing table file: "
					+ ROUTING_TABLE_FILE);
		} catch (IOException e) {
			System.err.println("Could not read routing table file: "
					+ e.getMessage());
		}
	}

	/**
	 * Routes an IP address to a network in the routing table, or the default
	 * network if no acceptable networks exist.
	 * 
	 * Tries each network in the order they were defined in the routing table
	 * file.
	 * 
	 * @param ip
	 *            the IP address as a string (e.g. "1.2.3.4")
	 * @return the name of a network, or the default network if no networks
	 *         match.
	 */
	private String route(String ip) {
		IPAddress address = new IPAddress(ip);
		for (Network net : networks)
			if (net.contains(address))
				return net.getName();
		return defaultNet;
	}

}
