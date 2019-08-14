# BevsCore
A commons of all the plugins made by BevsGames.

## Modules
Modules are simply put, mini plugins that are used by the developers to make life easier 

### Why Modules?
Modules allow us to write modular code that can be used across the network
quickly and easily .

### How to add a Module
```
@ModInfo( name = "<NameOfModule>" )
public class <Name>Module extends Module
{
		//If you need PlayerDataModule, we have a constructor for it
		//If you need CommandModule, we have a constructor for it too
		public <Name>Module(BevsPlugin plugin)
		{
				super(plugin)
		}

		@Override
		public void onEnable()
		{
				//called after constructor by the "addModule()" function
				//Set up Listeners here
				this.registerListener(new <Listener>());

				//set up commands
				this.registerCommand(new <Command>());
		}

		@Override
		public void onDisable()
		{
			//Called when module is being turned off
		}
}
```
### What are the Modules?
#### PlayerDataModule
*games.bevs.core.module.player.PlayerDataModule*

This module loads and saves player infomation from the network to the server.
PlayerData can be found in *games.bevs.core.commons.player.PlayerData*

**Depencies**
* BevsPlugin
* CommandModule
* Database
  * MongoDatabase, NoneDatabase Or MySQLDatabase

#### TickerModule
*games.bevs.core.module.ticker.TickerModule*

This module will fire UpdateEvent every tick, which allows you
to filter by ```TICK, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR```

**Depencies**
* BevsPlugin

**Example**
```
@EventHandler
public void onSecond(UpdateEvent e)
{
		if(e.getType() != UnitType.MINUTE) return;
		Bukkit.broadcastMessage("I am called every minute");
}
```

#### SpongeModule
*games.bevs.core.module.sponge.SpongeModule*

This allows us to launch players in the oppsite way of sponge placement
inspired by mcpvp.

We have two modes, BEVS and CLASSIC which are different trigger methods
* BEVS will launch players if they walk on a presure plate with sponges under it
* CLASSIC will launch a player if they walk on a sponge

**Depencies**
* BevsPlugin
* SpongeSettings (see below)

**Settings**
```
	private @Getter @Setter Material launchMaterial = Material.SPONGE;
	private @Getter @Setter LauncherType launcherType = LauncherType.BEVS;
	private @Getter @Setter double vertVelocity = 10;
	private @Getter @Setter double hortVelocity = 4;
```

**Example**
```
SpongeSettings spongeSettings = new SpongeSettings();
spongeSettings.setLauncherType(LauncherType.CLASSIC);
spongeSettings.setHortVelocity(5); //Default value is 10
spongeSettings.setVertVelocity(2); //Default value is 4
//spongeSettings.setLaunchMaterial(Material.STONE); //Default is Sponge, this will switch it to stone

this.addModule(new SpongeModule(this, spongeSettings));
```

#### SoupModule
*games.bevs.core.module.soup.SoupModule*

This module gives players the ability to right click soup to drink it for
a health boost. this was inspired by mcpvp.

**Depencies**
* BevsPlugin
* SoupSettings (see below)

**Settings**
```
	private @Getter @Setter double health = 5;
	private @Getter @Setter Material material = Material.MUSHROOM_SOUP;
	private @Getter @Setter ItemStack resultsIn = new ItemStack(Material.BOWL);
	private @Getter @Setter boolean cooldown = false;
	private @Getter @Setter long cooldownMillis = 1000;
	private @Getter @Setter String cooldownMessage = CC.red + "You soup is on cooldown for another {cooldown}!";
	private @Getter @Setter LinkedList<Drinkable> drinkEffects = new LinkedList<>();
```

#### AbilityModule
*games.bevs.core.module.abilties.AbilityModule.java*

This module is a collection of all the kit abilities so we can reuse them
across the network in a number of different places.

**Depencies**
* BevsPlugin
* CommandModule
* CooldownModule

**Add Ability Notes**
* Abilties should all be put into games.bevs.core.module.abilties.abilities
* Abilites should be as option as possible, so we can support a number of configations.
* All Damages must go through CustomDamageEvent

**Ability Example**
```
@NoArgsConstructor
@AllArgsConstructor
@AbilityInfo(name="Grandpa", author = "Sprock", description = { "Hitting player with a selected item", "sends anyone flying" })
public class GrandpaAbility extends Ability
{
	//Ability Settings
	public @Getter @Setter String itemName = "Grandpa's Cane";
	public @Getter @Setter Material itemMaterial = Material.STICK;
	public @Getter @Setter double knockbackMultipler = 3.0d;
	
	//Class variables
	private @Getter ItemStack knockbackItem;
	
	@Override
	public void onLoad()
	{
		this.knockbackItem = new ItemStackBuilder(itemMaterial).displayName(itemName).build();
	}
	
	@Override
	public List<ItemStack> getItems()
	{
		return Arrays.asList(knockbackItem);
	}
	
	@EventHandler
	public void onPunch(CustomDamageEvent e)
	{
		if(e.isAttackerIsPlayer() && (this.hasAbility(e.getAttackerPlayer())))
		{
			Player player = e.getAttackerPlayer();
			ItemStack itemInHand = player.getItemInHand();
			if(itemInHand != null && itemInHand.getAmount() != 0 && itemInHand.isSimilar(this.getKnockbackItem()))
			{
				e.addKnockback(getKnockbackMultipler(), player.getName());
			}
		}
	}
}
```


## Set up
* Clone the project
* Import this project as a maven project
* set up your [jitpack for private repos](https://jitpack.io/private)
	* Add a settings.xml to your ~/.m2
* Boom done

## Requirements
* Java 1.8
* git

## Compile
```
mvn clean install
```

The jar will compile to the project folder under 
```./build/bevsHeart.jar```

## Git
Try a only push working modules but at the same time, try and push as many updates as possible
to download and install
```
git clone https://github.com/bevsgames/BevsCore.git
cd BevsCore
```

To add an update
```
git add .
git commit -m "<YOUR MESSAGE OF WHAT YOU CHANGED>"
git push
```

To get changes
```
git pull
```

