package games.bevs.core.module.display.types;

import games.bevs.core.commons.io.Callback;
import games.bevs.core.commons.itemstack.ItemStackBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Clickable 
{
	private ItemStackBuilder itemstack;
	private Callback<ClickLog> clickAction;
}
