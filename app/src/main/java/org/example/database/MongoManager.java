package org.example.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoManager {
  private static Datastore datastore;
private static final  Logger log = LoggerFactory.getLogger(MongoManager.class);
  public static void connect() {
    Dotenv dotenv = Dotenv.load();
    String uri = dotenv.get("MONGO_URI");
    if (uri == null) {
      log.error("MONGO_URI is not set");
      throw new RuntimeException("MONGO_URI is not set");
    }
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
