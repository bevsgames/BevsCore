package games.bevs.core.commons.database.redis.example;

import com.google.gson.JsonObject;

import games.bevs.core.commons.database.redis.JedisPublisher;
import games.bevs.core.commons.database.redis.JedisSettings;
import games.bevs.core.commons.database.redis.JedisSubscriber;
import games.bevs.core.commons.database.redis.subscribe.JedisSubscriptionHandler;

public class Main {
	private static JedisPublisher<JsonObject> messagesPublisher;
	@SuppressWarnings("unused")
	private static JedisSubscriber<JsonObject> messagesSubscriber;

	public static void main(String[] args) {

		JedisSettings settings = new JedisSettings("78.31.71.65", 6379, "McpvpIsLife4378@13123!F");

		messagesPublisher = new JedisPublisher<JsonObject>(settings, "global-messages");
		messagesSubscriber = new JedisSubscriber<JsonObject>(settings, "global-messages", JedisSubscriber.JSON_GENERATOR,
				new JedisSubscriptionHandler<JsonObject>()
				{
				    @Override
				    public void handleMessage(JsonObject object) {
				        System.out.println(object);
				    }
				});
		
		writeTest("Sprock the bot");
	}

	public static void writeTest(String name) {
		JsonObject object = new JsonObject();
		object.addProperty("name", name);
		messagesPublisher.write(object);
	}

}
