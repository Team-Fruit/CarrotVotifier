package com.bebehp.mc.carrotvotifier;

import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventVotifier {
	public static final EventVotifier INSTANCE = new EventVotifier();

	@SubscribeEvent
	public void onClientTick(final VotifierEvent event) {
		Reference.logger.info("VOTE!!");
	}
}
