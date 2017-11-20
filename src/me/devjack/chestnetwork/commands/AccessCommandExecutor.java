package me.devjack.chestnetwork.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.devjack.chestnetwork.network.NetworkColor;
import me.devjack.chestnetwork.network.NetworkManager;
import me.devjack.chestnetwork.network.NetworkPlayer;

public class AccessCommandExecutor implements CommandExecutor {
	
	public AccessCommandExecutor() {
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
	        sender.sendMessage("You must be a player!");
	        return false;
	    }
		
		Player p = (Player) sender;
		
		if(args.length == 3) {
			NetworkColor color = NetworkColor.fromString(args[1]);
			if(color == null) return false;
			
			Player target = Bukkit.getPlayer(args[2]);
			if(target == null) return false;
			
			NetworkPlayer np = NetworkManager.getNetworkPlayer(p);
			
			if(args[0].equalsIgnoreCase("grant")) {
				np.getNetwork(color).allowAccess(target);
				p.sendMessage(args[1] + " was granted access to your " + color.toString() + " chest network.");
				target.sendMessage(p.getDisplayName() + " has granted you access to his " + color.toString() + " chest network.");
				return true;
			}
			else if(args[0].equalsIgnoreCase("remove")) {
				np.getNetwork(color).disbandAccess(target);;
				p.sendMessage(args[1] + " access permission was removed from your " + color.toString() + " chest network.");
				target.sendMessage(p.getDisplayName() + " has removed your access to his " + color.toString() + " chest network.");
				return false;
			}
			else {
				return false;
			}
		}
		return true;
	}
}
