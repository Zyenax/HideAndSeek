package net.Blxd.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/*
 * This plugin was made by Blxd for use on players servers this 
 * code may not be decompiled altered taken for profit or 
 * used without and consent of permission by Blxd.
 * If any of this code is taken without any permission given
 * actions will be given towards the offender in any such manner
 * that may harm the reputation of the offender.
 */

public class CommandHandler implements Listener, CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;

	public CommandHandler(Main hub) {
		this.plugin = hub;
	}

	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		Player player = (Player) sender;
		if(sender.equals(player)){
			if (command.getName().equalsIgnoreCase("team")){
				if(args.length == 0){
					sender.sendMessage(Main.color("&8&l[&e&lHideNSeek&8&l] &a the command is &c/team [teamname]"));
				}
				
				if(args.length == 1){
					String target = args[0];
					if(hashMapStorage.Team.containsKey(player)){
						player.sendMessage(Main.color("&8&l[&e&lHideNSeek&8&l] &cYou may not join another team"));
					}else{
						if(target.equalsIgnoreCase("blue")){
							hashMapStorage.setTag(player, Main.color("&bH &e"));
							player.setPlayerListName(Main.color("&bH " + "&e" + player.getName()));
							sender.sendMessage(Main.color("&8&l[&e&lHideNSeek&8&l] &bYou have been added to the blue team!"));
							hashMapStorage.Team.put(player, "blue");
						}else if(target.equalsIgnoreCase("red")){
							hashMapStorage.setTag(player, Main.color("&cS &e"));
							player.setPlayerListName(Main.color("&cS " + "&e" + player.getName()));
							sender.sendMessage(Main.color("&8&l[&e&lHideNSeek&8&l] &cYou have been added to the red team!"));
							hashMapStorage.Team.put(player, "red");
						}else{
							sender.sendMessage(Main.color("&8&l[&e&lHideNSeek&8&l] &ePlease supply a valid team!"));
						}
					}
				}
			
			
			
			}else if (command.getName().equalsIgnoreCase("reset")){
				for(Player p : Bukkit.getOnlinePlayers()){
					hashMapStorage.setTag(p, Main.color("&e"));
					p.setPlayerListName(Main.color("&e" + p.getName()));
				}
				Bukkit.getServer().broadcastMessage(Main.color("&8&l[&e&lHideNSeek&8&l] &c&lHideNSeek has been reset!"));
				hashMapStorage.Team.clear();
			}
		}
		return true;
	}

}
