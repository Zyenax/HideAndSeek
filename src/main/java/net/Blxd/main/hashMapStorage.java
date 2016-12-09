package net.Blxd.main;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftFirework;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

/*
 * This plugin was made by Blxd for use on players servers this 
 * code may not be decompiled altered taken for profit or 
 * used without and consent of permission by Blxd.
 * If any of this code is taken without any permission given
 * actions will be given towards the offender in any such manner
 * that may harm the reputation of the offender.
 */

public class hashMapStorage implements Listener{
	
	private static Main plugin;
	public hashMapStorage(Main listener) {
		hashMapStorage.plugin = listener;	
		SpawnItem(Bukkit.getWorld("world"), Material.GOLD_BLOCK, Material.TNT);
		StartClear();
	}
	
	public static HashMap<Player, String> Team = new HashMap<Player, String>();
	
	 @EventHandler
	  public void onJoin(PlayerJoinEvent e)
	  {
		 if(hashMapStorage.Team.containsKey(e.getPlayer())){
		 if(hashMapStorage.Team.get(e.getPlayer()).equals("red")){
			 e.getPlayer().setPlayerListName(Main.color("&cS " + "&e" + e.getPlayer().getName()));
		 }else if(hashMapStorage.Team.get(e.getPlayer()).equals("blue")){
			 e.getPlayer().setPlayerListName(Main.color("&bH " + "&e" + e.getPlayer().getName())); 
		 }
		 }else{
			 e.getPlayer().setPlayerListName(Main.color("&e" + e.getPlayer().getName()));
		 }
	    Player p = e.getPlayer();
	    p.setFoodLevel(20);
	    p.setHealth(20.0);
	    p.setPlayerListName(Main.color("&e" + p.getName()));
	  }
	  
	  @EventHandler
		public void noHunger(FoodLevelChangeEvent e){
			e.setCancelled(true);
		}
	  
	  @EventHandler
	  public void weatherChange(WeatherChangeEvent event)
	  {
	    if (event.toWeatherState()) {
	        World world = event.getWorld();
	          event.setCancelled(true);
	          world.setStorm(false);
	          world.setThundering(false);
	          world.setWeatherDuration(0);
	    }
	  }
	  
	  
	  @EventHandler
	  public void onBreak(BlockBreakEvent e){
		  if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
			  e.setCancelled(true);
		  }
	  }
	  
	  @EventHandler
	  public void onPlace(BlockPlaceEvent e){
		  if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
			  e.setCancelled(true);
		  }
	  }
	  
	  @EventHandler
		public void onHit(EntityDamageByEntityEvent e){
			e.setCancelled(true);
			if(e.getEntity() instanceof Player){
				if(e.getDamager() instanceof Player){
					Player player = (Player) e.getEntity();
					Player damager = (Player) e.getDamager();
					if(hashMapStorage.Team.get(damager).equals("red") && hashMapStorage.Team.get(player).equals("blue")){
						hashMapStorage.Team.put(player, "red");
						hashMapStorage.setTag(player, Main.color("&cS &e"));
						player.setPlayerListName(Main.color("&cS " + "&e" + player.getName()));
						Bukkit.broadcastMessage(Main.color("&8&l[&e&lHideNSeek&8&l] &b" + player.getName() + " &ehas been tagged by &c" + damager.getName()));
					}
				}
			}
		}
		
		@EventHandler
		public void onHit(EntityDamageEvent e){
			e.setCancelled(true);
		}
		
		@SuppressWarnings({ "deprecation" })
		public static void setTag(Player player, String tag)
		  {
		    Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		    Team team = board.getTeam(player.getName());
		    if (team == null) {
		      team = board.registerNewTeam(player.getName());
		    }
		    team.setPrefix(tag);
		    team.addPlayer(player);
		    for (Player players : Bukkit.getOnlinePlayers()) {
		      players.setScoreboard(board);
		    }
		  }
		
		@SuppressWarnings({ "deprecation" })
		public static void setNonCollidable(Player player)
		  {
		    Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		    Team team = board.getTeam(player.getName());
		    if (team == null) {
		      team = board.registerNewTeam(player.getName());
		    }
		    team.setOption(Option.COLLISION_RULE, OptionStatus.ALWAYS);
		    team.addPlayer(player);
		    for (Player players : Bukkit.getOnlinePlayers()) {
		      players.setScoreboard(board);
		    }
		  }
		
		public static Material blocktoSpawnOnTop;
		
		public void SpawnItem(final World world, final Material blockToSpawnOn, final Material itemToSpawn){
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run() {
				blocktoSpawnOnTop = blockToSpawnOn;
				for(Chunk chunk : world.getLoadedChunks()){
					for (int x = 0; x < 16; x++) {
			            for (int z = 0; z < 16; z++) {
			                for (int y = 0; y < 256; y++) {
			                    Block b = chunk.getBlock(x, y, z);
			                    Location loc = new Location(Bukkit.getWorld("world"), b.getLocation().getX(), b.getLocation().getY(), b.getLocation().getZ());
			                    if(b.getType().equals(blockToSpawnOn)){
			                    	for(Player player : Bukkit.getOnlinePlayers()){
			                    		sendActionBar(player, Main.color("&cThrowing TnT &ahas been spawned on the gold blocks!"));
			                    	}
			                    	FireworkEffect tntEffect = FireworkEffect
											.builder()
											.trail(false)
											.flicker(false)
											.withColor(
													new Color[] { Color.YELLOW,
															Color.RED,
															Color.WHITE })
											.with(FireworkEffect.Type.BALL_LARGE)
											.build();
									playFirework(loc,
											tntEffect, -1);
			                    	world.dropItem(new Location(Bukkit.getWorld("world"), b.getLocation().getX() + 0.5, b.getLocation().getY() + 1.5, b.getLocation().getZ() + 0.5), createItem(itemToSpawn, 1, 0, Main.color("&e&l[&c&lThrowing TNT&e&l]"), null));
			                    }
			                }
			            }
			        }
				}
			}
			}, 0, 15 * 20);
		}
		
		public void StartClear(){
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run() {
				for(Entity entity : Bukkit.getWorld("world").getEntities()){
					if(!(entity instanceof Player)){
						entity.remove();
					}
				}
				for(Player player : Bukkit.getOnlinePlayers()){
            		sendActionBar(player, Main.color("&8&l[&e&lHideNSeek&8&l] &cThrowing TnT &ahas been removed off the ground!"));
            		player.sendMessage(Main.color("&8&l[&e&lHideNSeek&8&l] &cThrowing TnT &ahas been removed off the ground!"));
            	}
				
			}
			}, 0, 220 * 20);
		}
		
		public static ItemStack createItem(Material material, int amount ,int dataValue, String name,
				List<String> list) {
			ItemStack selector = new ItemStack(material, amount, (short) dataValue);
			ItemMeta selectorMeta = selector.getItemMeta();
			selectorMeta.setDisplayName(name);
			if (list != null)
				selectorMeta.setLore(list);
			selector.setItemMeta(selectorMeta);
			return selector;
		}
		
		@SuppressWarnings("deprecation")
		@EventHandler
		public void Tnt(final PlayerInteractEvent e) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK
					|| e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (e.getPlayer().getItemInHand() != null) {
					if (e.getPlayer().getItemInHand().getType() == Material.TNT) {
						final Entity tnt = e.getPlayer().getWorld().spawnEntity(e.getPlayer().getEyeLocation(), EntityType.PRIMED_TNT);
						tnt.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5));
					}
					}
				}
			}
		
		@SuppressWarnings("deprecation")
		@EventHandler
		public void onBlockEvent(EntityExplodeEvent e){
			if(e.blockList().contains(blocktoSpawnOnTop)){
				e.setCancelled(true);
			}else{
			for(Block block : e.blockList()){
				FallingBlock falling = block.getLocation().getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
				float x = (float) (Math.random() * randomNum(-2,2));
	            float y = (float) (Math.random() * randomNum(1,2));
	            float z = (float) (Math.random() * randomNum(-2,2));
				falling.setVelocity(block.getLocation().getDirection().setX(x).setY(y).setZ(z));
			}
			}
		} 
		
		public static int randomNum(int Low, int High){
			Random r = new Random();
			int R = r.nextInt(High-Low) + Low;
			return R;
		}
		
		public static void sendActionBar(Player player, String message) {
			CraftPlayer p = (CraftPlayer) player;
			IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message
					+ "\"}");
			PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
		}
		
		public static void playFirework(Location paramLocation,
				FireworkEffect paramFireworkEffect, Integer lifespan) {
			Entity localEntity = paramLocation.getWorld().spawnEntity(
					paramLocation, EntityType.FIREWORK);
			Firework localFirework = (Firework) localEntity;
			FireworkMeta localFireworkMeta = localFirework.getFireworkMeta();
			localFireworkMeta.addEffect(paramFireworkEffect);
			localFireworkMeta.setPower(1);
			localFirework.setFireworkMeta(localFireworkMeta);

			((CraftFirework) localFirework).getHandle().expectedLifespan = lifespan;
		}
		
		
		
		
		
		
	
}
