package com.mactso.poorgolems.common.logic;

import java.util.Collection;

import com.mactso.poorgolems.common.util.MyUtilities;
import com.mactso.poorgolems.modloader.config.MyConfig;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class IronGolemDropHandler {


    // Track last drop time per server
    private static long tickTimer = 0;

    /**
     * Handles a golem's death drops.

     */
    public static void handleGolemDeathDrops(Entity eventEntity, Collection<ItemEntity> eventItems) {
    	if (eventEntity == null) {
    		return;
    	}


    	if (!(eventEntity.level() instanceof ServerLevel serverLevel)) {
    		return ;
    	}
    	

    	
    	if (eventEntity instanceof IronGolem) {


    		eventItems.removeIf((itemEntity) -> {return itemEntity.getItem().getItem() == Items.IRON_INGOT;});

    		if (eventEntity.blockPosition().getY() < serverLevel.getMinY()) {
    			// excess golems killed by the golem limit.
    			return;
    		}

    		
    		long worldTime = serverLevel.getGameTime();
    		if (tickTimer > worldTime) {
    			long seconds = 1 + ((tickTimer - worldTime) / 20);
    			MyUtilities.debugMsg (1, eventEntity.blockPosition(), "Poor Golems: A Golem Died "+ seconds +" Seconds sToo soon and dropped no iron.");
    			return;
    		}

    		tickTimer = worldTime + (long) (MyConfig.secondsBetweenIronDrops * 20);

    		int rollRange = MyConfig.MaxIronDropAmount - MyConfig.MinIronDropAmount;
    		if (rollRange < 0) {
    			rollRange = 0;
    		}
    		

    		int randomLootRoll = serverLevel.random.nextInt(rollRange + 1) + MyConfig.MinIronDropAmount;

    		// Determine what type of iron to drop
    		ItemStack itemStackToDrop = switch (MyConfig.ironGolemDropMode) {
    	    case MyConfig.DROP_NUGGETS -> new ItemStack(Items.IRON_NUGGET, randomLootRoll);
    	    case MyConfig.DROP_BLOCKS -> new ItemStack(Items.IRON_BLOCK, randomLootRoll);
    	    default -> new ItemStack(Items.IRON_INGOT, randomLootRoll); // default to ingots
    	};

    		ItemEntity myItemEntity = new ItemEntity(serverLevel, eventEntity.getX(), eventEntity.getY(), eventEntity.getZ(), itemStackToDrop);
			eventItems.add(myItemEntity);
			
    		MyUtilities.debugMsg(1, eventEntity.blockPosition(), "Poor Golems: A Golem Died at: "
    					+"and dropped "+ randomLootRoll +" iron.");


    	}
    }
	
	
}
