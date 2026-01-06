package com.mactso.poorgolems.common.commands;

import com.mactso.poorgolems.common.util.MyUtilities;
import com.mactso.poorgolems.modloader.config.MyConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class MyCommands {

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
	    dispatcher.register(
	        Commands.literal("poorgolems")
	            .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
	            
	            // ---- poorgolems debugLevel <0-2> ----
	            .then(
	                Commands.literal("debugLevel")
	                    .then(
	                        Commands.argument("debugLevel", IntegerArgumentType.integer(0, 2))
	                            .executes(ctx -> {
	                                ServerPlayer serverPlayerEntity = (ServerPlayer) ctx.getSource().getEntity();
	                                MyUtilities.sendChat(serverPlayerEntity, "Poor Golems Debug level set.");
	                                return setDebugLevel(IntegerArgumentType.getInteger(ctx, "debugLevel"));
	                            }) // end executes debugLevel
	                    ) // end argument "debugLevel"
	            ) // end literal "debugLevel"
	            
	            // ---- poorgolems info ----
	            .then(
	                Commands.literal("info")
	                    .executes(ctx -> {
	                        ServerPlayer serverPlayerEntity = (ServerPlayer) ctx.getSource().getEntity();
	                        String chatMessage = "Poor Golems Info" 
	                                + "\n Debug Level....: " + MyConfig.debugLevel
	                                + "\n Drop Mode....: " + MyConfig.getIronGolemDropMode() + " ("
	                                + MyConfig.getIronGolemDropModeAsString() + ")"
	                                + "\n Seconds Between Drops ... :" + MyConfig.getSecondsBetweenIronDrops();
	                        MyUtilities.sendChat(serverPlayerEntity, chatMessage);
	                        return 1;
	                    }) // end executes info
	            ) // end literal "info"
	    ); // end dispatcher.register
	}

	public static int setDebugLevel(int newDebugLevel) {
		MyConfig.debugLevel = newDebugLevel;
		MyConfig.pushDebugValue();
		return 1;
	}

}
