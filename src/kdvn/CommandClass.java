/*    */ package kdvn;
/*    */ 
/*    */ import net.md_5.bungee.api.ChatColor;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.PlayerInventory;
/*    */ 
/*    */ public class CommandClass implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*    */   {
/* 15 */     if ((cmd.getName().equalsIgnoreCase("yasuo")) && (sender.hasPermission("yasuo.*"))) {
/* 16 */       if (args.length == 0) {
/* 17 */         sender.sendMessage(ChatColor.YELLOW + "1. /yasuo setitem");
/* 18 */         sender.sendMessage(ChatColor.YELLOW + "2. /yasuo setlocxoaydamage <damage>");
/* 19 */         sender.sendMessage(ChatColor.YELLOW + "3. /yasuo setluotdamage <damage>");
/* 20 */         sender.sendMessage(ChatColor.YELLOW + "4. /yasuo safezone <ban_kinh> <ten_khu_vuc>");
/*    */ 
/*    */       }
/* 23 */       else if (args[0].equalsIgnoreCase("setlocxoaydamage")) {
/*    */         try {
/* 25 */           int satThuong = Integer.parseInt(args[1]);
/* 26 */           Player player = (Player)sender;
/* 27 */           KiemYasuo.addDamageLore(player.getInventory().getItemInMainHand(), satThuong);
/*    */         }
/*    */         catch (Exception e) {
/* 30 */           sender.sendMessage(ChatColor.RED + "Xãy ra lỗi");
/*    */         }
/*    */       }
/* 33 */       else if (args[0].equalsIgnoreCase("setluotdamage")) {
/*    */         try {
/* 35 */           int satThuong = Integer.parseInt(args[1]);
/* 36 */           Player player = (Player)sender;
/* 37 */           KiemYasuo.addEYasuoDamage(player.getInventory().getItemInMainHand(), satThuong);
/*    */         }
/*    */         catch (Exception e) {
/* 40 */           sender.sendMessage(ChatColor.RED + "Xãy ra lỗi");
/*    */         }
/*    */       }
/* 43 */       else if (args[0].equalsIgnoreCase("setitem")) {
/* 44 */         Player player = (Player)sender;
/* 45 */         KiemYasuo.setItem(player.getInventory().getItemInMainHand());
/*    */ 
/*    */       }
/* 48 */       else if (args[0].equalsIgnoreCase("safezone")) {
/* 49 */         int radius = Integer.parseInt(args[1]);
/* 50 */         Player player = (Player)sender;
/* 51 */         SafeZone.setLocation(player, radius, args[2], player.getWorld().getName());
/*    */       }
/*    */     }
/*    */     
/* 55 */     return false;
/*    */   }
/*    */ }
