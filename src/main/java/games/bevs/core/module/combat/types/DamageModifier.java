package games.bevs.core.module.combat.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DamageModifier 
{
	private String source;
	private String reason;
	private double damage;
}
