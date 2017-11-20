package me.devjack.chestnetwork.network;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class NetworkPlayer {

	private Player player;
	private List<Network> networks;
	
	public NetworkPlayer(Player player) {
		this(player, new ArrayList<Network>());
	}
	
	public NetworkPlayer(Player player, List<Network> networks) {
		this.player = player;
		this.networks = networks;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public List<Network> getNetworks() {
		return this.networks;
	}
	
	public Network getNetwork(NetworkColor color) {
		for(Network n : this.networks) {
			if(n.getColor() == color) {
				return n;
			}
		}
		
		Network newNetwork = new Network(this.player, color);
		this.networks.add(newNetwork);
		return newNetwork;
	}
	
	public void addNetwork(Network n) {
		this.networks.add(n);
	}
	
}
