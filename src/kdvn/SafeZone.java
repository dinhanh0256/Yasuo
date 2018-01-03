/*    */ package kdvn;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.md_5.bungee.api.ChatColor;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class SafeZone
/*    */ {
/* 15 */   private static FileConfiguration dataConfig = ;
/* 16 */   private static HashMap<String, Location> locationList = new HashMap();
/*    */   
/*    */   public static void setLocation(Player player, double radius, String name, String world) {
/* 19 */     Location location = player.getLocation();
/* 20 */     double x = location.getX();
/* 21 */     double y = location.getY();
/* 22 */     double z = location.getZ();
/* 23 */     dataConfig.set("Location." + name + ".x", Double.valueOf(x));
/* 24 */     dataConfig.set("Location." + name + ".y", Double.valueOf(y));
/* 25 */     dataConfig.set("Location." + name + ".z", Double.valueOf(z));
/* 26 */     dataConfig.set("Location." + name + ".radius", Double.valueOf(radius));
/* 27 */     dataConfig.set("Location." + name + ".world", world);
/*    */     
/* 29 */     List<String> list = dataConfig.getStringList("List");
/* 30 */     list.add(name);
/* 31 */     dataConfig.set("List", list);
/*    */     
/* 33 */     SecondConfig.saveConfig();
/* 34 */     player.sendMessage(ChatColor.GREEN + "DONE!");
/*    */   }
/*    */   
/*    */ 
/*    */   public static HashMap<String, Location> getLocationList(Player player)
/*    */   {
/* 40 */     List<String> list = dataConfig.getStringList("List");
/* 41 */     locationList = new HashMap();
/* 42 */     for (String s : list) {
/* 43 */       Location location = new Location(null, 0.0D, 0.0D, 0.0D);
/* 44 */       location.setX(dataConfig.getDouble("Location." + s + ".x"));
/* 45 */       location.setY(dataConfig.getDouble("Location." + s + ".y"));
/* 46 */       location.setZ(dataConfig.getDouble("Location." + s + ".z"));
/* 47 */       location.setX(dataConfig.getDouble("Location." + s + ".x"));
/* 48 */       locationList.put(s, location);
/*    */     }
/*    */     
/* 51 */     return locationList;
/*    */   }
/*    */   
/*    */   public static boolean inLocation(Player player) {
/* 55 */     List<String> listLocation = dataConfig.getStringList("List");
/* 56 */     Iterator localIterator = listLocation.iterator(); if (localIterator.hasNext()) { String s = (String)localIterator.next();
/* 57 */       Location location = (Location)getLocationList(player).get(s);
/* 58 */       World world = Bukkit.getWorld(dataConfig.getString("Location." + s + ".world"));
/* 59 */       location.setWorld(world);
/* 60 */       double radius = dataConfig.getDouble("Location." + s + ".radius");
/* 61 */       if (player.getLocation().distance(location) > radius)
/* 62 */         return false;
/* 63 */       return true;
/*    */     }
/* 65 */     return false;
/*    */   }
/*    */ }
