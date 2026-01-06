package com.mactso.poorgolems.common.logic;

import java.util.concurrent.atomic.AtomicInteger;

import com.mactso.poorgolems.common.util.MyUtilities;
import com.mactso.poorgolems.modloader.config.MyConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.phys.AABB;

public class HandleGolemSpawnLimits {
	public static void handleChunkLimit(ServerLevel serverLevel, IronGolem ie) {

		// Prevent double-processing of the same golem
		if (ie.getPersistentData().getBoolean("PoorSpawned").orElse(false)) {
			return;
		}

		BlockPos spawnPos = BlockPos.containing(ie.getX(), ie.getY(), ie.getZ());
		

		if (hasTooManyNearbyGolems(serverLevel, ie, spawnPos)) {
			// Too many golems in the chunk, kill the excess
			BlockPos pos = spawnPos;
			ie.setPos(pos.getX(), serverLevel.getMinY() - 3, pos.getZ());
			ie.hurtServer(serverLevel, ie.damageSources().fellOutOfWorld(), 200);
			if (MyConfig.getDebugLevel() == 2)
				MyUtilities.debugMsg(2, ie.blockPosition(), "Poor Golems: " + MyConfig.getIronGolemChunkLimit()
						+ " Golem Chunk Limit Blocked Attempted Golem Spawn.");
		} else {
			// Mark this entity so it won't be processed again
			ie.getPersistentData().putBoolean("PoorSpawned", true);
		}

	}
	
    /**
     * Returns true if the number of alive Iron Golems near spawnPos
     * is greater than or equal to the configured chunk limit.
     */
    private static boolean hasTooManyNearbyGolems(ServerLevel serverLevel, IronGolem ie, BlockPos spawnPos) {
        AABB aabb = AABB.encapsulatingFullBlocks(
                spawnPos.east(16).above(2).north(16),
                spawnPos.west(16).below(24).south(16)
        );

        final int chunkLimit = MyConfig.getIronGolemChunkLimit();
        AtomicInteger count = new AtomicInteger(0);

        serverLevel.getEntities().get(ie.getType(), aabb, entity -> {
            if (entity.isAlive()) {
                int current = count.incrementAndGet();
                if (current >= chunkLimit) {
                    return AbortableIterationConsumer.Continuation.ABORT; // stop iterating early
                }
            }
            return AbortableIterationConsumer.Continuation.CONTINUE;
        });

        if (count.get() >= chunkLimit) 
        	return true;
        return false;
    }
}
