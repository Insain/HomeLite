package com.bukkit.insain.HomeLite;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.entity.Player;


public class HomeLitePlayerListener extends PlayerListener {
    private final HomeLite plugin;

    public HomeLitePlayerListener(HomeLite instance) {
        plugin = instance;
    }
    public void onPlayerJoin(PlayerEvent event)
    { 
    	  Player player = event.getPlayer();
      player.sendMessage("[HomeLite Active]" );
      player.sendMessage("Please See /hlhelp for commands" );
    }

	public void onPlayerCommand(PlayerChatEvent event)
{
	  Player player = event.getPlayer();
	  String[] command = event.getMessage().split(" ");

      if (command[0].compareToIgnoreCase("/hlhelp") == 0)
      {
        event.getPlayer().sendMessage("HomeLite: Commands:");
        event.getPlayer().sendMessage("HomeLite: sethome   (Get a Home)");
        event.getPlayer().sendMessage("HomeLite: home (Go Home you lazy Bum)");

      }
      if (command[0].compareToIgnoreCase("/sethome") == 0)
      {
      	 this.plugin.m_house.put(player.getName(), player.getLocation());
      	    player.sendMessage(ChatColor.RED + "Welcome to your New Home!");
      	   String Yip = event.getPlayer().getName();
      	 player.sendMessage(ChatColor.RED + Yip);
      	    this.plugin.saveSettings(Yip);
      }
      else if (command[0].compareToIgnoreCase("/home") == 0)
      {
      	 Location loc = (Location)this.plugin.m_house.get(player.getName());
      	    if (loc != null)
      	    {
      	    	player.sendMessage(ChatColor.RED + "WOOOOOOOSSSSSSHHHHHHHH!");
      	      player.teleportTo(loc);
      	    }
      	    else
      	    {
      	      player.sendMessage(ChatColor.RED + "What do you think this is a free ride? Get a home you lazy bum //sethome");
      	    }
      	  }}}
