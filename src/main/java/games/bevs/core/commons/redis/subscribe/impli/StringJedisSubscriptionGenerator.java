package games.bevs.core.commons.redis.subscribe.impli;

import games.bevs.core.commons.redis.subscribe.JedisSubscriptionGenerator;

public class StringJedisSubscriptionGenerator implements JedisSubscriptionGenerator<String> {

	@Override
	public String generateSubscription(String message) {
		return message;
	}

}
