package games.bevs.core.module.combat.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KnockbackMultipler 
{
	private String source;
	private double knockbackMultipler;
}
