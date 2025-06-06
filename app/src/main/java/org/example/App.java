package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.commands.CommandManager;
import org.example.events.EventManager;

public class App {
  public static void main(String[] args) throws Exception {
    Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("DISCORD_TOKEN");

    org.example.database.MongoManager.connect();

    if (token == null) {
      System.err.println("DISCORD_TOKEN environment variable not set!");
      System.exit(1);
    }

    JDA jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).build();

    CommandManager commandManager = new CommandManager();
    EventManager eventManager = new EventManager(commandManager);
    commandManager.registerSlashCommands(jda);

    jda.addEventListener(eventManager);

    System.out.println("Bot started!");
  }
}
