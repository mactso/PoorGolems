package com.mactso.poorgolems.events;

import java.util.ArrayList;
import java.util.List;

import com.mactso.poorgolems.config.MyConfig;
import com.mactso.poorgolems.util.Utility;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GolemSpawnEvent {

	
	@SubscribeEvent
	public void handleGolemSpawnEvent(EntityJoinLevelEvent event) {

		if (event.getLevel() instanceof ServerLevel varW && 
		    event.getEntity() instanceof IronGolem e) {

			CompoundTag nbt = e.getPersistentData();
			
			if (nbt.getBoolean("PoorSpawned")) {
				return;
			}
			
			System.out.println("EntityId=" + e.getId());
			BlockPos spawnPos = new BlockPos(e.getX(), e.getY(), e.getZ());
			AABB aabb = new AABB(spawnPos.east(16).above(8).north(16), spawnPos.west(16).below(8).south(16));
			List<Entity> l  = new ArrayList<>();
			varW.getEntities().get(EntityType.IRON_GOLEM,
					aabb,
					(entity)->{
						l.add(entity);
						});
			if (l.size() >= MyConfig.getIronGolemChunkLimit()) {
				BlockPos pos = spawnPos;
				event.getEntity().setPos(pos.getX(), -3, pos.getZ());
				event.getEntity().hurt(DamageSource.OUT_OF_WORLD, 200);		
					Utility.debugMsg(0, event.getEntity().blockPosition(), "Poor Golems: "+ MyConfig.getIronGolemChunkLimit()+" Golem Chunk Limit Blocked Attempted Golem Spawn.");

			} else {
				nbt.putBoolean("PoorSpawned", true);
			}
			
		}
	}
}
