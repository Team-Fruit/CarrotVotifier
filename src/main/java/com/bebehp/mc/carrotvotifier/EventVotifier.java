package com.bebehp.mc.carrotvotifier;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class EventVotifier {
	public static final EventVotifier INSTANCE = new EventVotifier();

	private final MinecraftServer s = MinecraftServer.getServer();

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		Reference.logger.info("VOTE!!");
		final Vote vote = event.getVote();
		//final String name = vote.getUsername();
		final String name = vote.getUsername();
		//		final String name = name1.replaceAll(" ", "");
		final World world = MinecraftServer.getServer().getEntityWorld();
		final IChatComponent c1 = ChatUtil.byText(String.format("§e%sが投票しました。引き換えアイテムをゲット！", name));
		final IChatComponent c2 = ChatUtil.byJson("{\"text\":\"手に持ったTConstructのツールがレベルアップ！投票はこちら\",\"underlined\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://minecraft.jp/servers/mc.bebehp.com/vote\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§1クリック§7して §eJapan Minecraft Servers §7で §6FruitServer §7の投票をしよう！\"}}");
		ChatUtil.sendServerChat(c1, c2);
		this.s.getCommandManager().executeCommand(this.s, String.format("/give %s minecraft:spawn_egg 1 0 {display:{Name:\"投票引き換え券\",Lore:[\"Spawn地点で引き換えよう！\"]},ench:[{id:21,lvl:1}]}", name));
		this.s.getCommandManager().executeCommand(this.s, String.format("/leveluptool %s", name));
		final EntityPlayer player = world.getPlayerEntityByName(name);
		if (player != null) {
			fireworksPlayer(world, player);
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
