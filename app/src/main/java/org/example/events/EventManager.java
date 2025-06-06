package org.example.events;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.example.commands.CommandManager;

public class EventManager implements EventListener {
  private final Map<Class<? extends GenericEvent>, Consumer<? super GenericEvent>> events =
      new HashMap<>();

  public EventManager(CommandManager commandManager) {
    registerEvent(new MessageReceived(commandManager));
    registerEvent(new SlashCommandInteraction(commandManager));
    registerEvent(new GuildMemberJoin());
  }

  public void registerEvent(TypedListener<? extends GenericEvent> event) {
    @SuppressWarnings("unchecked")
    Class<GenericEvent> eventType = (Class<GenericEvent>) event.getEventType();
    @SuppressWarnings("unchecked")
    Consumer<? super GenericEvent> consumer = (Consumer<? super GenericEvent>) event;
    events.put(eventType, consumer);
  }

  @Override
  public void onEvent(@Nonnull GenericEvent event) {
    Consumer<? super GenericEvent> listener = events.get(event.getClass());
    if (listener != null) {
      listener.accept(event);
    }
  }
}
