package com.mactso.poorgolems.config;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.poorgolems.Main;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = Main.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
// @Mod.EventBusSubscriber(modid = Main.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class MyConfig
{
	private static final Logger LOGGER = LogManager.getLogger();
	public static final Server SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;
	static
	{
		final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
		SERVER_SPEC = specPair.getRight();
		SERVER = specPair.getLeft();
	}

	public static int debugLevel;
	public static int secondsBetweenIronDrops;
	public static int ironDropAmount;

	
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent)
	{
		if (configEvent.getConfig().getSpec() == MyConfig.SERVER_SPEC)
		{
			bakeConfig();
		}
	}

	public static void bakeConfig()
	{
		debugLevel = SERVER.debugLevel.get();
		secondsBetweenIronDrops = SERVER.secondsBetweenIronDrops.get();
		ironDropAmount = SERVER.ironDropAmount.get();


	}


	public static class Server
	{

		public final IntValue debugLevel;
		public final IntValue secondsBetweenIronDrops;
		public final IntValue ironDropAmount;

		
		public Server(ForgeConfigSpec.Builder builder)
		{
			builder.push("Villager Respawn Control Values");

			debugLevel = builder
					.comment("Debug Level: 0 = Off, 1 = Log, 2 = Chat+Log")
					.translation(Main.MODID + ".config." + "debugLevel")
					.defineInRange("debugLevel", () -> 0, 0, 2);
			
			secondsBetweenIronDrops = builder
					.comment("Seconds Between Iron Drops")
					.translation(Main.MODID + ".config." + "secondsBetweenIronDrops")
					.defineInRange("secondsBetweenIronDrops", () -> 60, 1, 3600);

			
			ironDropAmount = builder
					.comment("Iron Drop Amount Maximum")
					.translation(Main.MODID + ".config." + "ironDropAmount")
					.defineInRange("ironDropAmount", () -> 9, 0, 32);


			
			builder.pop();
		}
	}
}

