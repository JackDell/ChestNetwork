package me.devjack.chestnetwork.network;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public class StorageChest extends NetworkChest {

	private List<Material> itemFilter;
	
	public StorageChest(Network network, Chest chest) {
		this(network, chest, new ArrayList<Material>());
	}
	
	public StorageChest(Network network, Chest chest, List<Material> itemFilter) {
		super(network, chest);
		super.setType(ChestType.STORAGE);
		this.itemFilter = itemFilter;
	}
	
	public List<Material> getItemFilter() {
		return this.itemFilter;
	}
	
	public void setItemFilter(List<Material> itemFilter) {
		this.itemFilter = itemFilter;
	}
	
	public void addFilter(Material m) {
		if(!(this.itemFilter.contains(m))) this.itemFilter.add(m);
	}
	
	public boolean isAcceptableItem(ItemStack is) {
		if(this.itemFilter.size() == 0) return true;
		return this.itemFilter.contains(is.getType());
	}
	
	@Override
	public boolean addItem(ItemStack is) {
		if(this.isAcceptableItem(is)) {
			
			for(ItemStack stack : this.getChest().getInventory().getContents()) {
				if(stack == null) {
					this.getChest().getInventory().addItem(is);
					return true;
				}
			}
		}
		return false;
	}
}
