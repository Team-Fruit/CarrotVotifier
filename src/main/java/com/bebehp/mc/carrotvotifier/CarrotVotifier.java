package com.bebehp.mc.carrotvotifier;

import java.util.Map;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class CarrotVotifier {
	@Instance(Reference.MODID)
	public static CarrotVotifier instance;

	@NetworkCheckHandler
	public boolean checkModList(final Map<String, String> versions, final Side side) {
		return true;
	}

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		Reference.logger.info("INIT!!");
		MinecraftForge.EVENT_BUS.register(EventVotifier.INSTANCE);
		FMLCommonHandler.instance().bus().register(EventVotifier.INSTANCE);
	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event) {
		event.registerServerCommand(CarrotCommand.INSTANCE);
	}

	@EventHandler
	public void postInit(final FMLPostInitializationEvent event) {
	}

	@EventHandler
	public void serverStarting(final FMLServerStartingEvent event) {
	}
}