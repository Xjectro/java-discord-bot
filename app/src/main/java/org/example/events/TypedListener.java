package org.example.events;

import java.util.function.Consumer;
import net.dv8tion.jda.api.events.GenericEvent;

public interface TypedListener<T extends GenericEvent> extends Consumer<T> {
  Class<T> getEventType();
}
