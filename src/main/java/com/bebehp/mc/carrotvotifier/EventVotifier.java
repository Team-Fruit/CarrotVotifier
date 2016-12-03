package com.bebehp.mc.carrotvotifier;

import java.util.Queue;

import com.google.common.collect.Queues;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;

public class EventVotifier {
	public static final EventVotifier INSTANCE = new EventVotifier();

	private EventVotifier() {
	}

	private final MinecraftServer s = MinecraftServer.getServer();
	private final Queue<VotifierEvent> update = Queues.newConcurrentLinkedQueue();

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		this.update.add(event);
	}

	@SubscribeEvent
	public void onServerTick(final ServerTickEvent event) {
		VotifierEvent line;
		while ((line = this.update.poll())!=null)
			rewards(line.getVote().getUsername());
	}

	public void rewards(final String name) {
		// Execute Commands
		this.s.getCommandManager().executeCommand(this.s, String.format("/give %s minecraft:spawn_egg 1 0 {display:{Name:\"投票引き換え券\",Lore:[\"Spawn地点で引き換えよう！\"]},ench:[{id:21,lvl:1}]}", name));
		this.s.getCommandManager().executeCommand(this.s, String.format("/leveluptool %s", name));

		// Get Voter EntityPlayer
		final EntityPlayerMP player = this.s.getConfigurationManager().func_152612_a(name);

		// Notice
		IChatComponent c0 = ChatUtil.byText("");

		// Notice - Hold Items
		if (player!=null) {
			final ItemStack item = player.getHeldItem();
			if (item!=null)
				c0.appendSibling(item.func_151000_E()).appendSibling(ChatUtil.byText("§eを持った"));
		}

		// Notice
		c0 = c0.appendSibling(ChatUtil.byText(String.format("§e%sが投票しました。", name)));
		if (player!=null)
			c0 = c0.appendSibling(ChatUtil.byText("引き換えアイテムをゲット！"));
		final IChatComponent c1 = ChatUtil.byJson("{\"text\":\"手に持ったTConstructのツールがレベルアップ！投票はこちら\",\"underlined\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://minecraft.jp/servers/mc.bebehp.com/vote\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§1クリック§7して §eJapan Minecraft Servers §7で §6FruitServer §7の投票をしよう！\"}}");
		ChatUtil.sendServerChat(c0, c1);
	}

	/*
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
		*/

}
