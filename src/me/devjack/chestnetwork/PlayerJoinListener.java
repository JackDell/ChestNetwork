package me.devjack.chestnetwork;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.devjack.chestnetwork.network.NetworkManager;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		NetworkManager.getNetworkPlayer(e.getPlayer());
	}
	
}
