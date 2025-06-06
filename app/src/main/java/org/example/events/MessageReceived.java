package org.example.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.commands.CommandManager;

public class MessageReceived implements TypedListener<MessageReceivedEvent> {
  private final CommandManager commandManager;

  public MessageReceived(CommandManager commandManager) {
    this.commandManager = commandManager;
  }

  @Override
  public void accept(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) return;
    commandManager.handleMessageCommand(event);
  }

  @Override
  public Class<MessageReceivedEvent> getEventType() {
    return MessageReceivedEvent.class;
  }
}
