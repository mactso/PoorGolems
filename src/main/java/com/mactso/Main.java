// 16.1 - 0.0.0.1 Poor Golems
package com.mactso.poorgolems;


import com.mactso.poorgolems.config.MyConfig;
import com.mactso.poorgolems.events.GolemLootEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("poorgolems")
public class Main {

	    public static final String MODID = "poorgolems"; 
	    
	    public Main()
	    {

			FMLJavaModLoadingContext.get().getModEventBus().register(this);
	        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,MyConfig.SERVER_SPEC );
			MinecraftForge.EVENT_BUS.register(this);
	    }

	   // Register ourselves for server and other game events we are interested in
		@SubscribeEvent 
		public void preInit (final FMLCommonSetupEvent event) {
			System.out.println("Poor Golems: Registering Handler");
			MinecraftForge.EVENT_BUS.register(new GolemLootEvent());
			
		}       

		// in 14.4 and later, config file loads when the server starts when the world starts.
		@SubscribeEvent 
		public void onServerStarting (FMLServerStartingEvent event) {
//			VillagerRespawnCommands.register(event.getCommandDispatcher());
		}
}
