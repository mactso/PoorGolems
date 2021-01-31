package com.mactso.poorgolems.events;

import com.mactso.poorgolems.config.MyConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GolemSpawnEvent {

	@SubscribeEvent
	public void handleGolemSpawnEvent(LivingSpawnEvent event) {
		IWorld w = event.getWorld();

		if (!(event.getEntity() instanceof GolemEntity)) {
			return;
		}
		
		if (!(w instanceof ServerWorld)) {
			return;
		}

		Chunk cen = (Chunk) w.getChunk(event.getEntity().getPosition());
		int max = MyConfig.getIronGolemChunkLimit();
		int cur = 0;
		int height = event.getEntity().getPosition().getY();
		height = height / 16;
		if (height < 0) {
			height = 4;
		}
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				Chunk c = (Chunk) w.getChunk(cen.getPos().x + i, cen.getPos().z + j);
				ClassInheritanceMultiMap<Entity>[] aL = c.getEntityLists();
				cur = cur + aL[height].getByClass(GolemEntity.class).size();
			}
		}
//		System.out.println(
//				"Poor Golem : Spawn Attempt. cur: " + cur + " >=  max:" + max + "   " + event.getEntity().getPosition());


		if (cur-1 >= max) {
			BlockPos pos = event.getEntity().getPosition();
			event.getEntity().setPosition(pos.getX(), -3, pos.getZ());
			event.getEntity().attackEntityFrom(DamageSource.OUT_OF_WORLD, 200);
			if (MyConfig.debugLevel > 0) {
				System.out.println(
						"Poor Golem : Spawn Blocked. " + cur + " >= " + max + "   " + event.getEntity().getPosition());
			}
		}
	}
}
