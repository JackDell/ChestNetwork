package me.devjack.chestnetwork.network;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Network {

	private Player owner;
	private NetworkColor color;
	private List<Player> canAccess;
	private List<NetworkChest> networkChests;
	private String path;
	
	public Network(Player owner, NetworkColor color) {
		this(owner, color, new ArrayList<Player>(), new ArrayList<NetworkChest>());
	}
	
	public Network(Player owner, NetworkColor color, List<Player> canAccess, List<NetworkChest> networkChests) {
		this.owner = owner;
		this.color = color;
		this.canAccess = canAccess;
		this.networkChests = networkChests;
		this.path = (owner.getUniqueId().toString() + "." + color.toString() + ".");
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public NetworkColor getColor() {
		return this.color;
	}
	
	public List<Player> getWhoCanAccess() {
		return this.canAccess;
	}
	
	public boolean hasAccess(Player p) {
		if(this.owner == p || this.canAccess.contains(p)) return true;
		return false;
	}
	
	public void allowAccess(Player p) {
		if(!(this.canAccess.contains(p))) this.canAccess.add(p);
	}
	
	public void disbandAccess(Player p) {
		this.canAccess.remove(p);
	}
	
	public List<NetworkChest> getNetworkChests() {
		return this.networkChests;
	}
	
	public void addNetworkChest(NetworkChest nc) {
		if(!(this.networkChests.contains(nc))) this.networkChests.add(nc);
	}
	
	public void removeNetworkChest(NetworkChest nc) {
		this.networkChests.remove(nc);
	}
	
	public String getPath() {
		return this.path;
	}
	
	// Functional Methods
	
	public void moveItems() {
		
		int inputs = 0;
		for(NetworkChest nc : this.networkChests) {
			if(nc instanceof InputChest) inputs++;
		}
		
		if(inputs == this.networkChests.size()) return;
		
		for(NetworkChest nc : this.networkChests) {
			if(nc instanceof InputChest) ((InputChest) nc).emptyInputChest();
		}
	}
	
}
