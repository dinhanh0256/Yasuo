/*    */ package kdvn;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class SecondConfig
/*    */ {
/*    */   private static final String fileName = "data.yml";
/* 11 */   private static File file = null;
/* 12 */   private static FileConfiguration secondConfig = null;
/*    */   
/*    */   public static void setUpConfig() {
/* 15 */     file = new File(Main.plugin.getDataFolder(), "data.yml");
/* 16 */     if (!file.exists()) {
/*    */       try {
/* 18 */         file.createNewFile();
/*    */       }
/*    */       catch (IOException e) {
/* 21 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 24 */     secondConfig = YamlConfiguration.loadConfiguration(file);
/*    */   }
/*    */   
/*    */   public static void reloadConfig() {
/* 28 */     secondConfig = YamlConfiguration.loadConfiguration(file);
/*    */   }
/*    */   
/*    */   public static void saveConfig() {
/*    */     try {
/* 33 */       secondConfig.save(file);
/*    */     } catch (IOException e) {
/* 35 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */   public static FileConfiguration getConfig() {
/* 40 */     if (secondConfig == null) {
/* 41 */       setUpConfig();
/*    */     }
/* 43 */     return secondConfig;
/*    */   }
/*    */ }
