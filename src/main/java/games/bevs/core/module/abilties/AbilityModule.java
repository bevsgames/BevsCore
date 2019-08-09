package games.bevs.core.module.abilties;

import games.bevs.core.BevsPlugin;
import games.bevs.core.commons.utils.ClassGetterUtils;
import games.bevs.core.module.ModInfo;
import games.bevs.core.module.Module;
import games.bevs.core.module.abilties.dummy.DummyAbilityParent;
import games.bevs.core.module.abilties.types.Ability;
import games.bevs.core.module.commandv2.CommandModule;
import games.bevs.core.module.cooldown.CooldownModule;
import lombok.Getter;

@ModInfo(name = "Ability")
public class AbilityModule extends Module {
	private @Getter CooldownModule cooldownModule;

	public AbilityModule(BevsPlugin plugin, CommandModule commandModule, CooldownModule cooldownModule, boolean debug) {
		super(plugin, commandModule, debug);
		this.cooldownModule = cooldownModule;
	}

	public AbilityModule(BevsPlugin plugin, CommandModule commandModule, CooldownModule cooldownModule) {
		super(plugin, commandModule);
		this.cooldownModule = cooldownModule;
	}

	@Override
	public void onEnable() {

		if (!this.isDebug())
			return;
		DummyAbilityParent dummy = new DummyAbilityParent(this.getPlugin(), this.getClientModule(),
				this.getCooldownModule(), this);
//		dummy.addAbility(new BoxerAbility());
//		dummy.addAbility(new DummyAbility());
//		dummy.addAbility(new GandpaAbility());
//		dummy.addAbility(new KangarooAbility());
//		dummy.addAbility(new ThorAbility());

		// Will register all classes in {@link games.bevs.core.module.abilties.abilities
		// }
		for (Class<?> abilityClazz : ClassGetterUtils.getClassesForPackage(this.getPlugin(),
				"games.bevs.core.module.abilties.abilities")) {
			try {
				Ability ability = (Ability) abilityClazz.newInstance();
				dummy.addAbility(ability);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

	}

}
