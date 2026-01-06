package com.mactso.poorgolems.modloader.events;

import com.mactso.poorgolems.common.logic.IronGolemDropHandler;
import com.mactso.poorgolems.modloader.main.Main;

import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
	    modid = Main.MODID,
	    bus = Mod.EventBusSubscriber.Bus.FORGE
	)
public class GolemDropsEvent {

		public static long tickTimer = 0;

	    @SubscribeEvent
	    public static void onLivingDrops(LivingDropsEvent event) {
	        // Call the common logic
	        IronGolemDropHandler.handleGolemDeathDrops(event.getEntity(), event.getDrops());
	    }
	    

}
