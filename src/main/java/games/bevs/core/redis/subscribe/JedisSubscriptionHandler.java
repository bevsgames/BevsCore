package games.bevs.core.redis.subscribe;
public interface JedisSubscriptionHandler<K> {

	void handleMessage(K object);

}
