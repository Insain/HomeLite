package com.bukkit.insain.HomeLite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/********************************/
/*GuestBook for Bukkit Build 82+*/
/*								*/
/*@author Dr Insain				*/
/********************************/
public class HomeLite extends JavaPlugin
{
  private final HomeLitePlayerListener playerListener = new HomeLitePlayerListener(this);
  private File m_Folder;
  public HashMap<String, Location> m_house = new HashMap();

  public HomeLite(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader)
  {
    super(pluginLoader, instance, desc, folder, plugin, cLoader);
    this.m_Folder = folder;
  }

  public void onEnable()
  {
	  PluginManager pm = getServer().getPluginManager();


      // EXAMPLE: Custom code, here we just output some info so we can check all is well
      PluginDescriptionFile pdfFile = this.getDescription();

      pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
      pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);

    if (!this.m_Folder.exists())
    {
      System.out.print("HomeLite: Config folder missing, creating...");
      this.m_Folder.mkdir();
      System.out.println("done.");
    }
    File homelist = new File(this.m_Folder.getAbsolutePath() + File.separator + "house.txt");
    if (!homelist.exists())
    {
      System.out.print("HomeLite: No House File Creating...");
      try
      {
        homelist.createNewFile();
        System.out.println("done.");
      }
      catch (IOException ex)
      {
        System.out.println("failed.");
      }
    }

    System.out.print("HomeLite: Loading homelist...");
    if (loadSettings())
      System.out.println("done.");
    else {
      System.out.println("failed.");
    }


    System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
  }

  public void onDisable()
  {
    System.out.println("Goodbye world!");
  }

 

  public boolean saveSettings(String PlayerName)
  {
    try
    {
      BufferedWriter writer = new BufferedWriter(new FileWriter(this.m_Folder.getAbsolutePath() + File.separator + "house.txt"));
      for (Map.Entry entry : this.m_house.entrySet())
      {
        Location loc = (Location)entry.getValue();
        if (loc != null)
        {
          writer.write((String)entry.getKey() + ";" + loc.getX() + ";" + loc.getBlockY() + ";" + loc.getBlockZ() + ";" + loc.getPitch() + ";" + loc.getYaw());
          writer.newLine();
        }
      }
      writer.close();
      return true;
    }
    catch (Exception ex) {
    }
    return false;
  }

  public boolean loadSettings()
  {
    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(this.m_Folder.getAbsolutePath() + File.separator + "house.txt"));
      String line = reader.readLine();
      while (line != null)
      {
        String[] values = line.split(";");
        if (values.length == 6)
        {
          double X = Double.parseDouble(values[1]);
          double Y = Double.parseDouble(values[2]);
          double Z = Double.parseDouble(values[3]);
          float pitch = Float.parseFloat(values[4]);
          float yaw = Float.parseFloat(values[5]);

          World world = getServer().getWorlds()[0];
          this.m_house.put(values[0], new Location(world, X, Y, Z, yaw, pitch));
        }
        line = reader.readLine();
      }
      return true;
    }
    catch (Exception ex) {
    }
    return false;
  }
}