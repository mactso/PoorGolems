package com.mactso.poorgolems.modloader.events;

import com.mactso.poorgolems.common.logic.HandleGolemSpawnLimits;
import com.mactso.poorgolems.modloader.main.Main;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = Main.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class GolemSpawnEvent {

    @SubscribeEvent
    public static void handleGolemSpawnEvent(EntityJoinLevelEvent event) {

        if (event.getLevel() instanceof ServerLevel serverLevel && event.getEntity() instanceof IronGolem ie) {
            HandleGolemSpawnLimits.handleChunkLimit(serverLevel, ie);
        }
    }
}

