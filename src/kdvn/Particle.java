/*    */ package kdvn;
/*    */ 
/*    */ import net.minecraft.server.v1_10_R1.EntityPlayer;
/*    */ import net.minecraft.server.v1_10_R1.EnumParticle;
/*    */ import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;
/*    */ import net.minecraft.server.v1_10_R1.PlayerConnection;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class Particle
/*    */ {
/*    */   public static void sendParticle(EnumParticle type, Location loc, float xOffset, float yOffset, float zOffset, float speed, int count)
/*    */   {
/* 16 */     float x = (float)loc.getX();
/* 17 */     float y = (float)loc.getY();
/* 18 */     float z = (float)loc.getZ();
/* 19 */     PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, x, y, z, xOffset, yOffset, 
/* 20 */       zOffset, speed, count, null);
/* 21 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 22 */       ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void sendParticle(EnumParticle type, double x, double y, double z, float xOffset, float yOffset, float zOffset, float speed, int count)
/*    */   {
/* 28 */     float xf = (float)x;
/* 29 */     float yf = (float)y;
/* 30 */     float zf = (float)y;
/* 31 */     PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, xf, yf, zf, xOffset, yOffset, 
/* 32 */       zOffset, speed, count, null);
/* 33 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 34 */       ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void sendParticleTo(EnumParticle type, Player p, Location loc, float xOffset, float yOffset, float zOffset, float speed, int count)
/*    */   {
/* 40 */     float x = (float)loc.getX();
/* 41 */     float y = (float)loc.getY();
/* 42 */     float z = (float)loc.getZ();
/* 43 */     PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, x, y, z, xOffset, yOffset, 
/* 44 */       zOffset, speed, count, null);
/* 45 */     ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
/*    */   }
/*    */   
/*    */   public static void sendParticleTo(EnumParticle type, Player p, double x, double y, double z, float xOffset, float yOffset, float zOffset, float speed, int count)
/*    */   {
/* 50 */     float xf = (float)x;
/* 51 */     float yf = (float)y;
/* 52 */     float zf = (float)y;
/* 53 */     PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, xf, yf, zf, xOffset, yOffset, 
/* 54 */       zOffset, speed, count, null);
/* 55 */     ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
/*    */   }
/*    */ }
