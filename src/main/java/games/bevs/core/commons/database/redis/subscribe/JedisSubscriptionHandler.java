package games.bevs.core.commons.database.redis.subscribe;
public interface JedisSubscriptionHandler<K> {

	void handleMessage(K object);

}
