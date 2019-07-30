package games.bevs.core.redis.example;
import com.google.gson.JsonObject;

import games.bevs.core.redis.subscribe.JedisSubscriptionHandler;

public class BasicSubscriptionHandler implements JedisSubscriptionHandler<JsonObject> {

    @Override
    public void handleMessage(JsonObject object) {

        System.out.println(object);
    }

}