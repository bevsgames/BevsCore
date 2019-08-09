package games.bevs.core.commons.database.redis.subscribe.impli;

import games.bevs.core.commons.database.redis.subscribe.JedisSubscriptionGenerator;

public class StringJedisSubscriptionGenerator implements JedisSubscriptionGenerator<String> {

	@Override
	public String generateSubscription(String message) {
		return message;
	}

}
