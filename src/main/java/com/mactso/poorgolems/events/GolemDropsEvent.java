package com.mactso.poorgolems.events;

import java.util.Collection;

import com.mactso.poorgolems.config.MyConfig;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GolemDropsEvent {

		public static long tickTimer = 0;
		
		@SubscribeEvent
	    public void handleGolemDropsEvent(LivingDropsEvent event) { 
			
			Entity eventEntity = event.getEntityLiving();
			if (event.getEntity() == null) {
				return;
			}

	
			if (!(eventEntity.level instanceof ServerLevel)) {
				return;
			}
			
			if (eventEntity instanceof IronGolem) {
				Collection<ItemEntity> eventItems = event.getDrops();
				eventItems.removeIf((itemEntity) -> {return itemEntity.getItem().getItem() == Items.IRON_INGOT;});
				
				long worldTime = eventEntity.level.getGameTime();
				if (tickTimer > worldTime) {
					MyConfig.dbgPrintln(0, "Poor Golems: A Golem Died at: "
							+ (int) eventEntity.getX() + ", "
							+ (int) eventEntity.getY() + ", "
							+ (int) eventEntity.getZ() + ", "
							+"and dropped no iron.");
					return;
				}

				tickTimer = worldTime + (long) (MyConfig.secondsBetweenIronDrops * 20);

				int rollRange = MyConfig.MaxIronDropAmount - MyConfig.MinIronDropAmount;
				if (rollRange < 0) {
					rollRange = 0;
				}
				int randomLootRoll = (int) (Math.ceil(eventEntity.level.random.nextDouble() * rollRange ) + MyConfig.MinIronDropAmount);
		        ItemStack itemStackToDrop = new ItemStack(Items.IRON_INGOT, (int) randomLootRoll );
				ItemEntity myItemEntity = new ItemEntity (eventEntity.level, eventEntity.getX(),eventEntity.getY(),eventEntity.getZ(),itemStackToDrop);
				eventItems.add(myItemEntity);

				if (MyConfig.debugLevel > 0) {
					MyConfig.dbgPrintln(0, "Poor Golems: A Golem Died at: "
							+ (int) eventEntity.getX() + ", "
							+ (int) eventEntity.getY() + ", "
							+ (int) eventEntity.getZ() + ", "
							+"and dropped "+ randomLootRoll +" iron.");

				}	

				int debugline = 3;
			}
		}
	 
}
