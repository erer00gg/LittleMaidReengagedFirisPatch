package net.blacklab.lmc.common.event;

import net.blacklab.lmc.common.entity.LMEntityItemAntiDamage;
import net.blacklab.lmc.common.helper.LittleMaidHelper;
import net.blacklab.lmr.LittleMaidReengaged.LMItems;
import net.blacklab.lmr.config.LMRConfig;
import net.blacklab.lmr.entity.littlemaid.EntityLittleMaid;
import net.blacklab.lmr.network.LMRNetwork;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class LivingDeathEventHandler {

	/**
	 * イベントの優先度は高め
	 * @param event
	 */
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public static void onLivingDeathEvent(LivingDeathEvent event) {
		
		if (!LMRConfig.cfg_isResurrection) return;
		
		//メイドさんのみ処理を行う
		if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityLittleMaid) {
			
			EntityLittleMaid entityMaid = (EntityLittleMaid) event.getEntityLiving();
			
			//野良メイドはお土産を残さない
			if (entityMaid.getOwnerId() == null) return;
			
			//回復させる
			entityMaid.setHealth(1.0F);
			
			//炎上を消去
			entityMaid.extinguish();

			//メイドの土産
			ItemStack maidSouvenir = LittleMaidHelper.getItemStackFromEntity(entityMaid, new ItemStack(LMItems.MAID_SOUVENIR));
			
			World world = entityMaid.getEntityWorld();
			
			//Drop用EntityItem
			EntityItem entityMaidSouvenir = new LMEntityItemAntiDamage(world, 
					entityMaid.posX, entityMaid.posY, entityMaid.posZ,
					maidSouvenir);
			entityMaidSouvenir.setDefaultPickupDelay();
			world.spawnEntity(entityMaidSouvenir);
			
			
			//ClientSideの処理が動いてないみたいだからパケット飛ばしてパーティクルを発生させる
			LMRNetwork.PacketSpawnParticleS2C(entityMaid.getPosition(), 0);
			
			//メイドさん消滅処理
			entityMaid.setDead();
			event.setCanceled(true);
			
		}
		
	}
	
}
