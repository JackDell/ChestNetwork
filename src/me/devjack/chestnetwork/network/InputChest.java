package me.devjack.chestnetwork.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.inventory.ItemStack;

public class InputChest extends NetworkChest {
	
	public InputChest(Network network, Chest chest) {
		super(network, chest);
		super.setType(ChestType.INPUT);
	}
	
	public void emptyInputChest() {
		
		Iterator<ItemStack> iter;
		
		if(this.getChest() instanceof DoubleChest) {
			DoubleChest dc = (DoubleChest) this.getChest();
			List<ItemStack> chestContents = new ArrayList<ItemStack>();
			
			for(ItemStack is : dc.getLeftSide().getInventory().getContents()) {
				chestContents.add(is);
			}
			
			for(ItemStack is : dc.getRightSide().getInventory().getContents()) {
				chestContents.add(is);
			}
			
			iter = chestContents.iterator();
		}
		else {
			iter = this.getContents().iterator();
		}
		
		List<ItemStack> contents = new ArrayList<ItemStack>();
		
		while(iter.hasNext()) {
			ItemStack next = iter.next();
			if(next != null) {
				contents.add(next);
			}
		}
		
		for(NetworkChest nc : this.getNetwork().getNetworkChests()) {
			if(nc instanceof InputChest) continue;
			StorageChest sc = (StorageChest) nc;
			
			if(contents.size() == 0) return;
			
			List<ItemStack> remove = new ArrayList<ItemStack>();
			for(ItemStack is : contents) {
				if(sc.addItem(is)) remove.add(is);
			}
			contents.removeAll(remove);
			for(ItemStack is : remove) {
				this.getChest().getInventory().remove(is);
			}
		}
		
		
	}
}
