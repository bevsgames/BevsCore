package games.bevs.core.commons.database.redis.subscribe;
public interface JedisSubscriptionGenerator<K> {

	K generateSubscription(String message);

}
