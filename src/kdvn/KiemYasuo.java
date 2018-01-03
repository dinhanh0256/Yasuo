/*     */ package kdvn;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractAtEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.EquipmentSlot;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class KiemYasuo implements org.bukkit.event.Listener
/*     */ {
/*  28 */   private static final String CHEM_1 = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("ChemLan1"));
/*  29 */   private static final String CHEM_2 = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("ChemLan2"));
/*  30 */   private static final String CHEM_3 = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("ChemLan3"));
/*  31 */   private static final String E_DAMAGE_LORE = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("LuotLore"));
/*  32 */   private static final int E_DELAY = Main.plugin.getConfig().getInt("LuotDelay");
/*     */   
/*  34 */   private static final String loreConfig = Main.plugin.getConfig().getString("Lore");
/*  35 */   private static final String LORE = ChatColor.translateAlternateColorCodes('&', loreConfig);
/*     */   
/*  37 */   private static final String DAMAGE_LORE = ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("DamageLore"));
/*     */   
/*  39 */   private static HashMap<Player, List<Location>> playerLocation = new HashMap();
/*  40 */   private static HashMap<Player, Integer> triggeredPlayer = new HashMap();
/*  41 */   private static HashMap<Player, List<LivingEntity>> eYasuoList = new HashMap();
/*  42 */   private static HashMap<Player, Integer> playerCount = new HashMap();
/*  43 */   public static HashMap<Player, Integer> playerSche = new HashMap();
/*     */   
/*  45 */   private static List<Player> dangBanLocXoay = new ArrayList();
/*     */   public static int sche;
/*     */   
/*     */   public static void fireEffect(Player player, final double damage)
/*     */   {
/*  50 */     addLocationToPlayer(player);
/*  51 */     final List<Location> loList = getListLocation(player);
/*     */     
/*  53 */     if (playerCount.containsKey(player)) {
/*  54 */       playerCount.put(player, Integer.valueOf(0));
/*     */     }
/*  56 */     playerCount.put(player, Integer.valueOf(0));
/*  57 */     sche = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
/*     */       public void run() {
/*  59 */         KiemYasuo.playerCount.put(KiemYasuo.this, Integer.valueOf(((Integer)KiemYasuo.playerCount.get(KiemYasuo.this)).intValue() + 1));
/*  60 */         if (((Integer)KiemYasuo.playerCount.get(KiemYasuo.this)).intValue() == 50) {
/*  61 */           KiemYasuo.removePlayerFromLocationList(KiemYasuo.this);
/*  62 */           KiemYasuo.removeDangBanLocXoay(KiemYasuo.this);
/*  63 */           KiemYasuo.playerCount.put(KiemYasuo.this, Integer.valueOf(0));
/*  64 */           int temp = ((Integer)KiemYasuo.playerSche.get(KiemYasuo.this)).intValue();
/*  65 */           KiemYasuo.playerSche.remove(KiemYasuo.this);
/*  66 */           Bukkit.getScheduler().cancelTask(temp);
/*     */         }
/*     */         Iterator localIterator2;
/*  69 */         for (Iterator localIterator1 = loList.iterator(); localIterator1.hasNext(); 
/*     */             
/*     */ 
/*     */ 
/*  73 */             localIterator2.hasNext())
/*     */         {
/*  69 */           Location lo = (Location)localIterator1.next();
/*  70 */           lo.add(lo.getDirection().getX(), 0.0D, lo.getDirection().getZ());
/*  71 */           float offset = loList.indexOf(lo) * 0.2F;
/*  72 */           Particle.sendParticle(net.minecraft.server.v1_10_R1.EnumParticle.SWEEP_ATTACK, lo, offset, offset, offset, 0.05F, 5);
/*  73 */           localIterator2 = KiemYasuo.getEntityByLocation(lo, 2.0D).iterator(); continue;LivingEntity e = (LivingEntity)localIterator2.next();
/*  74 */           if (!e.equals(KiemYasuo.this))
/*     */           {
/*     */ 
/*  77 */             e.setVelocity(new Vector(0.0F, 1.1F, 0.0F));
/*  78 */             e.damage(damage, KiemYasuo.this);
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */       }
/*  84 */     }, 0L, 1L);
/*  85 */     playerSche.put(player, Integer.valueOf(sche));
/*     */   }
/*     */   
/*     */   public static void eYasuo(final Player player, LivingEntity e, float damage)
/*     */   {
/*  90 */     if (isTargetYasuo(player, e)) {
/*  91 */       player.sendMessage(ChatColor.RED + "Bạn chưa thể lướt tới mục tiêu này tiếp");
/*  92 */       return;
/*     */     }
/*  94 */     triggerPlayerEYasuo(player, e);
/*  95 */     player.setVelocity(player.getLocation().getDirection().multiply(2.5F));
/*  96 */     e.damage(damage, player);
/*  97 */     Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
/*     */       public void run() {
/*  99 */         KiemYasuo.removeEYasuo(KiemYasuo.this, player);
/*     */       }
/* 101 */     }, E_DELAY * 20);
/*     */   }
/*     */   
/*     */   public static void addBanLocXoay(Player player)
/*     */   {
/* 106 */     if (!dangBanLocXoay.contains(player)) {
/* 107 */       dangBanLocXoay.add(player);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void removeDangBanLocXoay(Player player) {
/* 112 */     if (dangBanLocXoay.contains(player)) {
/* 113 */       dangBanLocXoay.remove(player);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void removeEYasuo(LivingEntity e, Player player)
/*     */   {
/* 119 */     if (!isTargetYasuo(player, e)) {
/* 120 */       return;
/*     */     }
/* 122 */     List<LivingEntity> list = (List)eYasuoList.get(player);
/* 123 */     list.remove(e);
/* 124 */     eYasuoList.put(player, list);
/*     */   }
/*     */   
/*     */   public static void addLocationToPlayer(Player player) {
/* 128 */     if (playerLocation.containsKey(player)) {
/* 129 */       return;
/*     */     }
/* 131 */     List<Location> loList = new ArrayList();
/* 132 */     Location mainLocation = player.getLocation();
/* 133 */     Location location1 = player.getLocation().add(0.0D, 1.0D, 0.0D);
/* 134 */     Location location2 = player.getLocation().add(0.0D, 2.0D, 0.0D);
/* 135 */     Location location3 = player.getLocation().add(0.0D, 3.0D, 0.0D);
/* 136 */     Location location4 = player.getLocation().add(0.0D, 4.0D, 0.0D);
/* 137 */     Location location5 = player.getLocation().add(0.0D, 5.0D, 0.0D);
/* 138 */     Location location6 = player.getLocation().add(0.0D, -1.0D, 0.0D);
/* 139 */     Location location7 = player.getLocation().add(0.0D, -2.0D, 0.0D);
/*     */     
/* 141 */     loList.add(location6);
/* 142 */     loList.add(location7);
/* 143 */     loList.add(mainLocation);
/* 144 */     loList.add(location1);
/* 145 */     loList.add(location2);
/* 146 */     loList.add(location3);
/* 147 */     loList.add(location4);
/* 148 */     loList.add(location5);
/* 149 */     playerLocation.put(player, loList);
/*     */   }
/*     */   
/*     */   public static List<Location> getListLocation(Player player) {
/* 153 */     return (List)playerLocation.get(player);
/*     */   }
/*     */   
/*     */   public static void addEYasuoDamage(ItemStack item, int damage) {
/* 157 */     ItemMeta meta = item.getItemMeta();
/* 158 */     if (item.getItemMeta().hasLore()) {
/* 159 */       List<String> lore = item.getItemMeta().getLore();
/* 160 */       lore.add(E_DAMAGE_LORE + damage);
/* 161 */       meta.setLore(lore);
/* 162 */       item.setItemMeta(meta);
/*     */     }
/*     */     else {
/* 165 */       List<String> lore = new ArrayList();
/* 166 */       lore.add(E_DAMAGE_LORE + damage);
/* 167 */       meta.setLore(lore);
/* 168 */       item.setItemMeta(meta);
/*     */     }
/*     */   }
/*     */   
/*     */   public static float getEYasuoDamage(ItemStack item) {
/* 173 */     float damage = 0.0F;
/* 174 */     if (!item.getItemMeta().hasLore()) {
/* 175 */       return 0.0F;
/*     */     }
/* 177 */     ItemMeta meta = item.getItemMeta();
/* 178 */     List<String> lore = meta.getLore();
/* 179 */     for (int i = 0; i < lore.size(); i++) {
/* 180 */       if (((String)lore.get(i)).contains(E_DAMAGE_LORE)) {
/* 181 */         String damageLore = (String)lore.get(i);
/* 182 */         String satThuongChar = damageLore.substring(damageLore.lastIndexOf(" ") + 1, damageLore.length());
/*     */         try {
/* 184 */           int satThuong = Integer.parseInt(satThuongChar);
/* 185 */           damage = satThuong;
/*     */         }
/*     */         catch (Exception e) {
/* 188 */           return 0.0F;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 194 */     return damage;
/*     */   }
/*     */   
/*     */   public static void removePlayerFromLocationList(Player player) {
/* 198 */     if (playerLocation.containsKey(player)) {
/* 199 */       playerLocation.remove(player);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isTargetYasuo(Player mainPlayer, LivingEntity e) {
/* 204 */     if (!eYasuoList.containsKey(mainPlayer)) {
/* 205 */       return false;
/*     */     }
/* 207 */     List<LivingEntity> list = (List)eYasuoList.get(mainPlayer);
/* 208 */     if (list.contains(e)) {
/* 209 */       return true;
/*     */     }
/* 211 */     return false;
/*     */   }
/*     */   
/*     */   public static void triggerPlayerEYasuo(Player player, LivingEntity e) {
/* 215 */     if (isTargetYasuo(player, e)) {
/* 216 */       return;
/*     */     }
/* 218 */     List<LivingEntity> list = new ArrayList();
/* 219 */     list.add(e);
/* 220 */     eYasuoList.put(player, list);
/*     */   }
/*     */   
/*     */   public static List<LivingEntity> getEntityByLocation(Location location, double radius) {
/* 224 */     List<LivingEntity> entityList = new ArrayList();
/* 225 */     for (Entity e : location.getWorld().getEntities()) {
/* 226 */       if ((location.distance(e.getLocation()) <= radius) && 
/* 227 */         ((e instanceof LivingEntity))) {
/* 228 */         entityList.add((LivingEntity)e);
/*     */       }
/*     */     }
/*     */     
/* 232 */     return entityList;
/*     */   }
/*     */   
/*     */   public static void addDamageLore(ItemStack item, int damage) {
/* 236 */     ItemMeta meta = item.getItemMeta();
/* 237 */     if (item.getItemMeta().hasLore()) {
/* 238 */       List<String> lore = item.getItemMeta().getLore();
/* 239 */       lore.add(DAMAGE_LORE + damage);
/* 240 */       meta.setLore(lore);
/* 241 */       item.setItemMeta(meta);
/*     */     }
/*     */     else {
/* 244 */       List<String> lore = new ArrayList();
/* 245 */       lore.add(DAMAGE_LORE + damage);
/* 246 */       meta.setLore(lore);
/* 247 */       item.setItemMeta(meta);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setItem(ItemStack item) {
/* 252 */     ItemMeta meta = item.getItemMeta();
/* 253 */     if (item.getItemMeta().hasLore()) {
/* 254 */       List<String> lore = item.getItemMeta().getLore();
/* 255 */       lore.add(LORE);
/* 256 */       meta.setLore(lore);
/* 257 */       item.setItemMeta(meta);
/*     */     }
/*     */     else {
/* 260 */       List<String> lore = new ArrayList();
/* 261 */       lore.add(LORE);
/* 262 */       meta.setLore(lore);
/* 263 */       item.setItemMeta(meta);
/*     */     }
/*     */   }
/*     */   
/*     */   public static float getDamageThroughItem(ItemStack item) {
/* 268 */     float damage = 0.0F;
/* 269 */     if (!item.getItemMeta().hasLore()) {
/* 270 */       return 0.0F;
/*     */     }
/* 272 */     ItemMeta meta = item.getItemMeta();
/* 273 */     List<String> lore = meta.getLore();
/* 274 */     for (int i = 0; i < lore.size(); i++) {
/* 275 */       if (((String)lore.get(i)).contains(DAMAGE_LORE)) {
/* 276 */         String damageLore = (String)lore.get(i);
/* 277 */         String satThuongChar = damageLore.substring(damageLore.lastIndexOf(" ") + 1, damageLore.length());
/*     */         try {
/* 279 */           int satThuong = Integer.parseInt(satThuongChar);
/* 280 */           damage = satThuong;
/*     */         }
/*     */         catch (Exception e) {
/* 283 */           return 0.0F;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 289 */     return damage;
/*     */   }
/*     */   
/*     */   public static boolean isKiemYasuo(ItemStack i) {
/* 293 */     if (i == null) {
/* 294 */       return false;
/*     */     }
/* 296 */     if (!i.getItemMeta().hasLore()) {
/* 297 */       return false;
/*     */     }
/* 299 */     if (i.getItemMeta().getLore().contains(LORE)) {
/* 300 */       return true;
/*     */     }
/* 302 */     return false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteract(PlayerInteractEvent e) {
/* 307 */     if (e.getHand() != EquipmentSlot.HAND) {
/* 308 */       return;
/*     */     }
/* 310 */     if ((e.getAction() != Action.LEFT_CLICK_AIR) && (e.getAction() != Action.LEFT_CLICK_BLOCK)) {
/* 311 */       return;
/*     */     }
/* 313 */     if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
/* 314 */       return;
/*     */     }
/* 316 */     ItemStack item = e.getItem();
/* 317 */     if (!item.getItemMeta().hasLore()) {
/* 318 */       return;
/*     */     }
/* 320 */     if (!isKiemYasuo(item)) {
/* 321 */       return;
/*     */     }
/* 323 */     Player player = e.getPlayer();
/* 324 */     if (dangBanLocXoay.contains(player)) {
/* 325 */       return;
/*     */     }
/* 327 */     if (SafeZone.inLocation(player)) {
/* 328 */       return;
/*     */     }
/* 330 */     if (!triggeredPlayer.containsKey(player)) {
/* 331 */       triggeredPlayer.put(player, Integer.valueOf(1));
/* 332 */       player.sendMessage(CHEM_1);
/*     */     }
/*     */     else {
/* 335 */       int i = ((Integer)triggeredPlayer.get(player)).intValue();
/* 336 */       if (i == 1) {
/* 337 */         triggeredPlayer.put(player, Integer.valueOf(2));
/* 338 */         player.sendMessage(CHEM_2);
/*     */       }
/* 340 */       else if (i == 2) {
/* 341 */         addBanLocXoay(player);
/* 342 */         triggeredPlayer.remove(player);
/* 343 */         player.sendMessage(CHEM_3);
/* 344 */         fireEffect(player, getDamageThroughItem(item));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteractToEntity(PlayerInteractAtEntityEvent e) {
/* 351 */     if (e.getHand() != EquipmentSlot.HAND) {
/* 352 */       return;
/*     */     }
/* 354 */     if (SafeZone.inLocation(e.getPlayer())) {
/* 355 */       return;
/*     */     }
/* 357 */     Entity entity = e.getRightClicked();
/* 358 */     if (!(entity instanceof LivingEntity)) {
/* 359 */       return;
/*     */     }
/* 361 */     if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
/* 362 */       return;
/*     */     }
/* 364 */     ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
/* 365 */     if (!isKiemYasuo(item)) {
/* 366 */       return;
/*     */     }
/* 368 */     if (isTargetYasuo(e.getPlayer(), (LivingEntity)entity)) {
/* 369 */       e.getPlayer().sendMessage(ChatColor.RED + "Bạn chưa thể lướt tới mục tiêu này tiếp");
/* 370 */       return;
/*     */     }
/* 372 */     eYasuo(e.getPlayer(), (LivingEntity)entity, getEYasuoDamage(item));
/*     */   }
/*     */ }
