package com.bebehp.mc.carrotvotifier;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CarrotCommand extends CommandBase {
	public static final CarrotCommand INSTANCE = new CarrotCommand();

	private CarrotCommand() {
	}

	@Override
	public String getCommandName() {
		return "carrotvotifier";
	}

	@Override
	public String getCommandUsage(final ICommandSender icommandsender) {
		return "carrotvotifier <name>";
	}

	@Override
	public void processCommand(final ICommandSender icommandsender, final String[] astring) {
		String name;
		if (astring.length == 0) {
			name = icommandsender.getCommandSenderName();
		} else {
			name = astring[0];
		}
		EventVotifier.INSTANCE.rewards(name);
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 3;
	}
}