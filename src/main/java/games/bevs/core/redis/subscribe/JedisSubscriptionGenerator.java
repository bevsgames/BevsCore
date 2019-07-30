package games.bevs.core.redis.subscribe;
public interface JedisSubscriptionGenerator<K> {

	K generateSubscription(String message);

}
