// 16.1 - 0.0.0.1 Poor Golems
package com.mactso.poorgolems.modloader.main;


import com.mactso.poorgolems.common.commands.MyCommands;
import com.mactso.poorgolems.common.util.MyUtilities;
import com.mactso.poorgolems.modloader.config.MyConfig;
import com.mactso.poorgolems.modloader.events.GolemDropsEvent;
import com.mactso.poorgolems.modloader.events.GolemSpawnEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("poorgolems")
public class Main {

	    public static final String MODID = "poorgolems"; 
	    
	    public Main(FMLJavaModLoadingContext context)
	    {
			context.registerConfig(ModConfig.Type.COMMON, MyConfig.COMMON_SPEC);
//	        FMLCommonSetupEvent.getBus(context.getModBusGroup()).addListener(this::handleCommonSetup);
	    	MyUtilities.debugMsg(0,MODID + ": Registering Mod.");
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
				MyCommands.register(event.getDispatcher());			
			}

	    }
}
