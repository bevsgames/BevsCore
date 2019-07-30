package games.bevs.core.redis.example;

import com.google.gson.JsonObject;

import games.bevs.core.redis.JedisPublisher;
import games.bevs.core.redis.JedisSettings;
import games.bevs.core.redis.JedisSubscriber;

public class Main {
	private static JedisPublisher<JsonObject> messagesPublisher;
	private static JedisSubscriber<JsonObject> messagesSubscriber;

	public static void main(String[] args) {
		Redis redis = new Redis();
		redis.connect();

		JedisSettings settings = new JedisSettings("78.31.71.65", 6379, "McpvpIsLife4378@13123!F");

		messagesPublisher = new JedisPublisher<JsonObject>(settings, "global-messages");
		messagesSubscriber = new JedisSubscriber<JsonObject>(settings, "global-messages", JedisSubscriber.JSON_GENERATOR,
				new BasicSubscriptionHandler());
		
		writeTest("Sprock the bot");
	}

	private static JsonObject generateMessage(String type, JsonObject data) {
        JsonObject object = new JsonObject();
        object.addProperty("server", "1");
        object.addProperty("type", type);
        object.add("data", data);
        return object;
    }
	
	public static void writeTest(String name) {
		JsonObject object = new JsonObject();
		object.addProperty("name", name);
		messagesPublisher.write(generateMessage("ban", object));
	}

}
