package org.example.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import io.github.cdimascio.dotenv.Dotenv;

public class MongoManager {
  private static Datastore datastore;

  public static void connect() {
    Dotenv dotenv = Dotenv.load();
    String uri = dotenv.get("MONGO_URI");
    MongoClient client = MongoClients.create(uri);
    datastore = Morphia.createDatastore(client);
  }

  public static Datastore getDatastore() {
    if (datastore == null) {
      throw new IllegalStateException("Datastore not initialized. Call connect() first.");
    }
    return datastore;
  }
}
