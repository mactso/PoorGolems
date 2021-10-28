package com.mactso.poorgolems.config;


import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.poorgolems.Main;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
// @Mod.EventBusSubscriber(modid = Main.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class MyConfig
{
	private static final Logger LOGGER = LogManager.getLogger();
	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;
	static
	{
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	public static int getIronGolemChunkLimit() {
		return ironGolemChunkLimit;
	}


	public static int getDebugLevel() {
		return debugLevel;
	}

	public static void setDebugLevel(int debugLevel) {
		MyConfig.debugLevel = debugLevel;
	}

	public static int getSecondsBetweenIronDrops() {
		return secondsBetweenIronDrops;
	}

	public static int getMinIronDropAmount() {
		return MinIronDropAmount;
	}

	public static int getMaxIronDropAmount() {
		return MaxIronDropAmount;
	}

	// support for debug messages
	public static void dbgPrintln(int dbgLevel, String dbgMsg) {
		if (dbgLevel <= debugLevel) {
			System.out.println (dbgMsg);
		}
	}

	public static void dbgChatln(Player p, String dbgMsg, int dbgLevel) {
		if (dbgLevel <= debugLevel ) {
			sendChat (p, dbgMsg, TextColor.fromLegacyFormat((ChatFormatting.YELLOW)));
		}
	}

	// support for any color chattext
	public static void sendChat(Player p, String chatMessage, TextColor color) {
		TextComponent component = new TextComponent (chatMessage);
		component.getStyle().withColor(color);
		p.sendMessage(component, p.getUUID());
	}
	
	// support for any color, optionally bold text.
	public static void sendBoldChat(Player p, String chatMessage, TextColor color) {
		TextComponent component = new TextComponent (chatMessage);

		component.getStyle().withBold(true);
		component.getStyle().withColor(color);
		
		p.sendMessage(component, p.getUUID());
	}

	
	public static int debugLevel;
	public static int ironGolemChunkLimit;
	public static int secondsBetweenIronDrops;
	public static int MinIronDropAmount;
	public static int MaxIronDropAmount;
	
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfigEvent configEvent)
	{
		if (configEvent.getConfig().getSpec() == MyConfig.COMMON_SPEC)
		{
			bakeConfig();
		}
	}

	public static void bakeConfig()
	{
		debugLevel = COMMON.debugLevel.get();
		ironGolemChunkLimit = COMMON.ironGolemChunkLimit.get();
		secondsBetweenIronDrops = COMMON.secondsBetweenIronDrops.get();
		MinIronDropAmount = COMMON.MinIronDropAmount.get();
		MaxIronDropAmount = COMMON.MaxIronDropAmount.get();


	}


	public static class Common
	{

		public final IntValue debugLevel;
		public final IntValue ironGolemChunkLimit;
		public final IntValue secondsBetweenIronDrops;
		public final IntValue MinIronDropAmount;
		public final IntValue MaxIronDropAmount;

		
		public Common(ForgeConfigSpec.Builder builder)
		{
			builder.push("Poor Golems Control Values");

			debugLevel = builder
					.comment("Debug Level: 0 = Off, 1 = Log, 2 = Chat+Log")
					.translation(Main.MODID + ".config." + "debugLevel")
					.defineInRange("debugLevel", () -> 0, 0, 2);

			ironGolemChunkLimit = builder
					.comment("golemChunkLimit: Maximum Iron Golems per Chunk.")
					.translation(Main.MODID + ".config." + "ironGolemChunkLimit")
					.defineInRange("ironGolemChunkLimit", () -> 4, 1, 32);
						
			secondsBetweenIronDrops = builder
					.comment("Seconds Between Iron Drops")
					.translation(Main.MODID + ".config." + "secondsBetweenIronDrops")
					.defineInRange("secondsBetweenIronDrops", () -> 30, 1, 3600);

			
			MinIronDropAmount = builder
					.comment("Min Iron Drop Amount Maximum")
					.translation(Main.MODID + ".config." + "MinIronDropAmount")
					.defineInRange("MinIronDropAmount", () -> 1, 0, 1);

			MaxIronDropAmount = builder
					.comment("Max Iron Drop Amount Maximum")
					.translation(Main.MODID + ".config." + "MaxIronDropAmount")
					.defineInRange("MaxIronDropAmount", () -> 9, 0, 32);

			
			builder.pop();
		}
	}

}

