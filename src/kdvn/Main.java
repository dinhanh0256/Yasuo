/*    */ package kdvn;
/*    */ 
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class Main extends org.bukkit.plugin.java.JavaPlugin
/*    */ {
/*    */   public static Main plugin;
/* 10 */   private FileConfiguration config = getConfig();
/*    */   
/*    */   public void onEnable()
/*    */   {
/* 14 */     plugin = this;
/* 15 */     getCommand("yasuo").setExecutor(new CommandClass());
/* 16 */     SecondConfig.setUpConfig();
/* 17 */     SecondConfig.saveConfig();
/* 18 */     getConfig().options().copyDefaults(true);
/* 19 */     saveConfig();
/*    */     
/* 21 */     org.bukkit.Bukkit.getPluginManager().registerEvents(new KiemYasuo(), this);
/*    */   }
/*    */   
/*    */ 
/*    */   public void onDisable() {}
/*    */   
/*    */ 
/*    */   public FileConfiguration getConfiguration()
/*    */   {
/* 30 */     return this.config;
/*    */   }
/*    */ }
