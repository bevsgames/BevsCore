package games.bevs.core.client;

import games.bevs.core.commons.CC;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rank 
{
	STAFF(CC.red, "Staff"),
	MOD_PLUS(CC.dPurple, "Mod+"),
	MOD(CC.lPurple, "Mod"),
	FAMOUS(CC.black, "Famous"),
	PRO_PLUS(CC.gold, "Pro+"),
	PRO(CC.gold, "Pro"),
	MVP(CC.blue, "MVP"),
	VIP(CC.green, "VIP"),
	NORMAL(CC.gray, "");
	
	String tagColor;
	String displayName;
	
	public boolean hasPermissionsOf(Rank rank)
	{
		return this.compareTo(rank) <= 0;
	}
	
	public String getDisplayName()
	{
		return this.getTagColor() + this.getDisplayName() + CC.reset;
	}
	
	public static Rank toRank(String rankStr)
	{
		rankStr = rankStr.toUpperCase();
		Rank rank = Rank.valueOf(rankStr);
		if(rank == null)
		{
			rank = Rank.NORMAL;
			try {
				throw new Exception("Unknown rank '" + rankStr + "'");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rank;
	}
}
