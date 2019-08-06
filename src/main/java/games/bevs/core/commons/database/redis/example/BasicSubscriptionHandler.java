package games.bevs.core.commons.database.redis.example;
import com.google.gson.JsonObject;

import games.bevs.core.commons.database.redis.subscribe.JedisSubscriptionHandler;

public class BasicSubscriptionHandler implements JedisSubscriptionHandler<JsonObject> {

    @Override
    public void handleMessage(JsonObject object) {

        System.out.println(object);
    }

}