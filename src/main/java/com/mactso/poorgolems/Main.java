// 16.1 - 0.0.0.1 Poor Golems
package com.mactso.poorgolems;


import com.mactso.poorgolems.config.MyConfig;
import com.mactso.poorgolems.events.GolemDropsEvent;
import com.mactso.poorgolems.events.GolemSpawnEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.event.RegisterCommandsEvent;

@Mod("poorgolems")
public class Main {

	    public static final String MODID = "poorgolems"; 
	    
	    public Main()
	    {

			FMLJavaModLoadingContext.get().getModEventBus().register(this);
	        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,MyConfig.COMMON_SPEC );
			MinecraftForge.EVENT_BUS.register(this);
	    }

	   // Register ourselves for server and other game events we are interested in
		@SubscribeEvent 
		public void preInit (final FMLCommonSetupEvent event) {
			System.out.println("Poor Golems: Registering Handler");
			MinecraftForge.EVENT_BUS.register(new GolemDropsEvent());
			MinecraftForge.EVENT_BUS.register(new GolemSpawnEvent());
		}   

		
	    @Mod.EventBusSubscriber()
	    public static class ForgeEvents
	    {
			@SubscribeEvent 		
			public static void onCommandsRegistry(final RegisterCommandsEvent event) {
				System.out.println("Poor Golems: Registering Command Dispatcher");
				ModCommands.register(event.getDispatcher());			
			}

	    }
}
