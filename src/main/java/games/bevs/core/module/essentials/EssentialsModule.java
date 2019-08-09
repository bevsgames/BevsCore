package games.bevs.core.module.essentials;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.utils.ClassGetterUtils;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.commandv2.types.BevsCommand;
import games.bevs.core.module.player.PlayerDataModule;

@ModInfo(name = "Essentials")
public class EssentialsModule extends Module
{

	public EssentialsModule(BevsPlugin plugin, CommandModule commandModule, PlayerDataModule clientModule) {
		super(plugin, commandModule, clientModule);
	}

	@Override
	public void onEnable()
	{
		loadCommands();
	}
	
	/**
	 * Reigster all class within {@link games.bevs.core.module.essentials.commands}
	 */
	public void loadCommands()
	{
		ClassGetterUtils.getClassesForPackage(this.getPlugin(), 
				"games.bevs.core.module.essentials.commands").forEach(clazz -> {
			try {
				
				BevsCommand command = (BevsCommand) clazz.newInstance();
				this.registerCommand(command);
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				 | SecurityException e) {
				e.printStackTrace();
			}
		});
	}
}
