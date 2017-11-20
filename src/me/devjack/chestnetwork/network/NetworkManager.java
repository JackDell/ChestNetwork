package me.devjack.chestnetwork.network;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class NetworkManager {
	
	public NetworkManager() {
		
	}
	
	private static List<NetworkPlayer> players = new ArrayList<NetworkPlayer>();

	public static List<NetworkPlayer> getNetworkPlayers() {
		return players;
	}
	
	public static void setNetworkPlayers(List<NetworkPlayer> np) {
		players = np;
	}
	
	public static NetworkPlayer getNetworkPlayer(Player p) {
		for(NetworkPlayer np : players) {
			if(np.getPlayer() == p) {
				return np;
			}
		}
		
		NetworkPlayer newPlayer = new NetworkPlayer(p);
		players.add(newPlayer);
		return newPlayer;
	}
	
	public static void addNetworkPlayer(NetworkPlayer np) {
		players.add(np);
	}
	
}
