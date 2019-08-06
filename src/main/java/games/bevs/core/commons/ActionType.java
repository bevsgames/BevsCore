package games.bevs.core.commons;

import java.util.Arrays;
import java.util.List;

import org.bukkit.event.block.Action;

public enum ActionType 
{
	BLOCK(Action.RIGHT_CLICK_BLOCK, Action.LEFT_CLICK_BLOCK),
	AIR(Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_AIR),
	PHYSICAL(Action.PHYSICAL);
	
	private List<Action> actions;
	
	ActionType(Action... actions)
	{
		this.actions = Arrays.asList(actions);
	}
	
	public boolean containsAction(Action action)
	{
		return this.actions.contains(action);
	}
}
