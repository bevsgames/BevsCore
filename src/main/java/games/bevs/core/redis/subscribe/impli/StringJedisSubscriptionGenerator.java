package games.bevs.core.redis.subscribe.impli;

import games.bevs.core.redis.subscribe.JedisSubscriptionGenerator;

public class StringJedisSubscriptionGenerator implements JedisSubscriptionGenerator<String> {

	@Override
	public String generateSubscription(String message) {
		return message;
	}

}
