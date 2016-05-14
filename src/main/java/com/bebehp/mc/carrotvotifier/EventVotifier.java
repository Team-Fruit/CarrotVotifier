package com.bebehp.mc.carrotvotifier;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class EventVotifier {
	public static final EventVotifier INSTANCE = new EventVotifier();

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		Reference.logger.info("VOTE!!");
		final Vote vote = event.getVote();
		final String name1 = vote.getUsername();
		final String name = name1.replaceAll(" ", "");
		final World world = MinecraftServer.getServer().getEntityWorld();
		final EntityPlayer player = world.getPlayerEntityByName(name);
		sendServerChat(String.format("§e%sが投票しました。ありがとうございます。", name));
		if (player != null) {
			fireworksPlayer(world, player);
		}
	}

	public static void sendServerChat(final String msg) {
		final ServerConfigurationManager sender = FMLCommonHandler.instance().getMinecraftServerInstance()
				.getConfigurationManager();

		final String[] linemsg = msg.split("\n");
		for (final String line : linemsg) {
			sender.sendChatMsg(new ChatComponentText(line));
		}
	}

	public static void fireworksPlayer(final World world, final EntityPlayer player) {
		world.spawnEntityInWorld(new EntityFireworkRocket(
				world,
				player.getPlayerCoordinates().posX,
				player.getPlayerCoordinates().posY,
				player.getPlayerCoordinates().posZ,
				makeFireworks()
				));
	}

	public static ItemStack makeFireworks() {
		final NBTTagCompound explosion = new NBTTagCompound();
		explosion.setByte("Type", (byte)2);
		explosion.setByte("Trail", (byte)1);
		explosion.setIntArray("Colors", ItemDye.field_150922_c);

		final NBTTagList nbttaglist = new NBTTagList();
		nbttaglist.appendTag(explosion);
		nbttaglist.appendTag(explosion);
		nbttaglist.appendTag(explosion);

		final NBTTagCompound nbt_fireworks = new NBTTagCompound();
		nbt_fireworks.setTag("Explosions", nbttaglist);
		nbt_fireworks.setByte("Flight", (byte)1);

		final NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("Fireworks", nbt_fireworks);

		final ItemStack itemstack = new ItemStack(Items.firework_charge);
		itemstack.setTagCompound(nbt); return itemstack;
	}

}
