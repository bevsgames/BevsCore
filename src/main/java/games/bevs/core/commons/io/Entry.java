package games.bevs.core.commons.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Entry<L, R>
{
	private L left;
	private R right;
}
