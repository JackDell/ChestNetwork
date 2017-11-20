package me.devjack.chestnetwork.network;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public abstract class NetworkChest {
	
	private Network network;
	private Chest chest;
	private ChestType type;
	
	public NetworkChest(Network network, Chest chest) {
		this.network = network;
		this.chest = chest;
	}
	
	public Network getNetwork() {
		return this.network;
	}
	
	public void setNetwork(Network network) {
		this.network = network;
	}
	
	public Chest getChest() {
		return this.chest;
	}
	
	public List<ItemStack> getContents() {
		List<ItemStack> returnList = new ArrayList<ItemStack>();
		for(ItemStack is : this.chest.getBlockInventory().getContents()) {
			returnList.add(is);
		}
		return returnList;
	}
	
	public void setContents(List<ItemStack> contents) {
		this.chest.getInventory().clear();
		for(ItemStack is : contents) {
			if(is == null) continue;
			this.chest.getInventory().addItem(is);
		}
		this.chest.update(true);
	}
	
	public boolean addItem(ItemStack is) {
		
		if(is == null) return false;
		
		for(ItemStack stack : this.chest.getInventory().getContents()) {
			if(stack != null) continue;
			this.chest.getInventory().addItem(is);
			this.chest.update();
			return true;
		}
		return false;
	}
	
	public Location getChestLocation() {
		return this.chest.getLocation();
	}
	
	public NetworkColor getColor() {
		return this.network.getColor();
	}
	
	public ChestType getType() {
		return this.type;
	}
	
	public void setType(ChestType type) {
		this.type = type;
	}
}
