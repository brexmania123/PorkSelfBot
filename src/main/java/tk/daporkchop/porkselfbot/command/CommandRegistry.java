package tk.daporkchop.porkselfbot.command;

import java.util.HashMap;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class CommandRegistry {
	
	/**
	 * A HashMap containing all the commands and their prefix
	 */
	private static final HashMap<String, Command> COMMANDS = new HashMap<>();

	/**
	 * Registers a command to the command registry.
	 * @param cmd
	 * @return cmd again lul
	 */
	public static final Command registerCommand(Command cmd)	{
		COMMANDS.put(cmd.prefix, cmd);
		return cmd;
	}
	
	/**
	 * Runs a command
	 * @param evt
	 */
	public static void runCommand(MessageReceivedEvent evt, String rawContent)	{
		try {
			String[] split = rawContent.split(" ");
			Command cmd = COMMANDS.getOrDefault(split[0].substring(2), null);
			if (cmd != null)	{
				new Thread() {
					@Override
					public void run()	{
						cmd.excecute(evt, split, rawContent);
					}
				}.start();
			}
		} catch (Exception e)	{
			e.printStackTrace();
			evt.getMessage().editMessage("Error running command: `" + evt.getMessage().getRawContent() + "`:\n`" + e.getClass().getCanonicalName() + "`").queue();
		}
	}
}
