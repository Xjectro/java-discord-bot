package org.example.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PingCommandTest {
  @Test
  void onMessageCommand_sendsPong() {
    MessageChannel channel = Mockito.mock(MessageChannel.class);
    MessageReceivedEvent event = Mockito.mock(MessageReceivedEvent.class);
    MessageAction messageAction = Mockito.mock(MessageAction.class);
    Mockito.when(event.getChannel()).thenReturn(channel);
    Mockito.when(channel.sendMessage("Pong! 🏓")).thenReturn(messageAction);

    PingCommand cmd = new PingCommand();
    cmd.onMessageCommand(event, new String[] {});

    Mockito.verify(channel).sendMessage("Pong! 🏓");
    Mockito.verify(messageAction).queue();
  }
}
