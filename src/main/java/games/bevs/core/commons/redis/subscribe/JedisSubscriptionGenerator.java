package games.bevs.core.commons.redis.subscribe;
public interface JedisSubscriptionGenerator<K> {

	K generateSubscription(String message);

}
