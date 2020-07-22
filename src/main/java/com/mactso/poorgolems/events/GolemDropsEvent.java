package com.mactso.poorgolems.events;

import java.util.Collection;

import com.mactso.poorgolems.config.MyConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GolemDropsEvent {

		public static int tickTimer = 0;
		
		@SubscribeEvent
	    public void handleGolemDropsEvent(LivingDropsEvent event) { 
			
			Entity eventEntity = event.getEntityLiving();
			if (event.getEntity() == null) {
				return;
			}

			int worldTick = 1000;
			if (tickTimer < worldTick) {
				// update tickTimer and exit
				tickTimer = worldTick;
				return;
			}
			
			if (!(eventEntity.world instanceof ServerWorld)) {
				return;
			}
			
			if (eventEntity instanceof GolemEntity) {
				Collection<ItemEntity> eventItems = event.getDrops();

				GolemEntity ge = (GolemEntity) eventEntity;
				
				if (MyConfig.debugLevel > 0) {
					System.out.println("Poor Golems: A Golem Died at X,Y,Z.");
				}	

				double randomLootRoll = Math.ceil(eventEntity.world.rand.nextDouble() * MyConfig.ironDropAmount);

		        ItemStack itemStackToDrop = new ItemStack(Items.IRON_INGOT, (int) randomLootRoll );

			}
		}
	 
}
