package com.mactso.poorgolems.events;

import java.util.Collection;

import com.mactso.poorgolems.config.MyConfig;
import com.mactso.poorgolems.util.Utility;

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
			
			Entity eventEntity = event.getEntity();
			if (event.getEntity() == null) {
				return;
			}

	
			if (!(eventEntity.level instanceof ServerLevel)) {
				return;
			}
			
			if (eventEntity instanceof IronGolem) {

				Collection<ItemEntity> eventItems = event.getDrops();
				eventItems.removeIf((itemEntity) -> {return itemEntity.getItem().getItem() == Items.IRON_INGOT;});

				if (eventEntity.blockPosition().getY() < eventEntity.level.getMinBuildHeight()) {
					return;
				}
				
				long worldTime = eventEntity.level.getGameTime();
				if (tickTimer > worldTime) {
					long seconds = 1 + ((tickTimer - worldTime) / 20);
					Utility.debugMsg (0, event.getEntity().blockPosition(), "Poor Golems: A Golem Died "+ seconds +" Seconds sToo soon and dropped no iron.");
					return;
				}

				tickTimer = worldTime + (long) (MyConfig.secondsBetweenIronDrops * 20);

				int rollRange = MyConfig.MaxIronDropAmount - MyConfig.MinIronDropAmount;
				if (rollRange < 0) {
					rollRange = 0;
				}
				int randomLootRoll = (int) (Math.ceil(eventEntity.level.random.nextDouble() * rollRange ) + MyConfig.MinIronDropAmount);

				if (MyConfig.ironGolemDropMode == 2) {
					ItemStack itemStackToDrop = new ItemStack(Items.IRON_NUGGET, (int) randomLootRoll);
					ItemEntity myItemEntity = new ItemEntity(eventEntity.level, eventEntity.getX(), eventEntity.getY(), eventEntity.getZ(), itemStackToDrop);
					eventItems.add(myItemEntity);
				}
				else
				if (MyConfig.ironGolemDropMode == 1) {
					ItemStack itemStackToDrop = new ItemStack(Items.IRON_INGOT, (int) randomLootRoll);
					ItemEntity myItemEntity = new ItemEntity(eventEntity.level, eventEntity.getX(), eventEntity.getY(), eventEntity.getZ(), itemStackToDrop);
					eventItems.add(myItemEntity);
				}
				else
				if (MyConfig.ironGolemDropMode == 3) {
					ItemStack itemStackToDrop = new ItemStack(Items.IRON_BLOCK, (int) randomLootRoll);
					ItemEntity myItemEntity = new ItemEntity(eventEntity.level, eventEntity.getX(), eventEntity.getY(), eventEntity.getZ(), itemStackToDrop);
					eventItems.add(myItemEntity);
				}

				Utility.debugMsg(0, event.getEntity().blockPosition(), "Poor Golems: A Golem Died at: "
							+"and dropped "+ randomLootRoll +" iron.");


			}
		}
	 
}
