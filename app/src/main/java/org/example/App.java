package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.commands.CommandManager;
import org.example.events.EventManager;
import org.example.log.LogConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

  private static final Logger log = LoggerFactory.getLogger(App.class);
  public static void main(String[] args) throws Exception {
    LogConfig.init();
    Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("DISCORD_TOKEN");

    org.example.database.MongoManager.connect();

    if (token == null) {
      log.error("DISCORD_TOKEN environment variable not set!");
      System.exit(1);
    }

    JDA jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).build();

    CommandManager commandManager = new CommandManager();
    EventManager eventManager = new EventManager(commandManager);
    commandManager.registerSlashCommands(jda);

    jda.addEventListener(eventManager);
    log.info("Bot started!");
  }
}
