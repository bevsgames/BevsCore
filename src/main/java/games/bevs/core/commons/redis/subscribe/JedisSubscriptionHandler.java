package games.bevs.core.commons.redis.subscribe;
public interface JedisSubscriptionHandler<K> {

	void handleMessage(K object);

}
