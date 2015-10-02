package cz.pikadorama.framework.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Tomas on 27.9.2015.
 */
public class EventManager {

    private static final Map<EventType, List<EventProcessor>> processors = new HashMap<>();

    /**
     * Register event processor implementation for the given event type. If the registration is successful
     * (e.i. the processor hasn't been registered before), the {@link EventProcessor#processEvent(EventType)}}
     * method is called afterwards.
     * <p/>
     * Note that if you register your processor for {@link EventType#ALL}, you can also register it for other types too.
     * In such case, the processor can be notified twice. It is your responsibility to handle registration properly.
     *
     * @param eventProcessor event processor to register
     * @param eventType      event type
     * @throws IllegalStateException in case parameters are null or the processor has already been registered
     */
    public synchronized static void registerEventProcessor(EventProcessor eventProcessor, EventType eventType) {
        Objects.requireNonNull(eventProcessor, "Registered event processor cannot be null.");
        Objects.requireNonNull(eventType, "Registered event processor must be registered for some event type.");

        List<EventProcessor> processorsForType = processors.get(eventType);
        if (processorsForType == null) {
            List<EventProcessor> procAsList = new ArrayList<>();
            procAsList.add(eventProcessor);
            processors.put(eventType, procAsList);
        } else {
            if (processorsForType.contains(eventProcessor)) {
                throw new IllegalStateException("Event processor " + eventProcessor + " is already registered.");
            } else {
                processors.get(eventType).add(eventProcessor);
            }

        }
        eventProcessor.processEvent(eventType);
    }

    /**
     * Unregister event processor.
     *
     * @param eventProcessor event processor to unregister
     * @param eventType      event type
     * @throws IllegalStateException in case parameters are null or the processor has already been unregistered
     *                               or not registered at all
     */
    public synchronized static void unregisterEventProcessor(EventProcessor eventProcessor, EventType eventType) {
        Objects.requireNonNull(eventProcessor, "Event processor to unregister cannot be null.");
        Objects.requireNonNull(eventType, "Event processor must be unregistered for some event type.");

        if (processors.containsKey(eventType)) {
            if (!processors.get(eventType).contains(eventProcessor)) {
                throw new IllegalStateException("Event processor" + eventProcessor + " is not registered.");
            }
            processors.get(eventType).remove(eventProcessor);
        } else {
            throw new IllegalStateException("Event processor" + eventProcessor + " is not registered.");
        }
    }

    /**
     * Notify all processors of the given {@link EventType}. Few rules to follow:
     * <ul>
     * <li>{@link EventType} == {@link EventType#ALL}: processors registered in as {@link EventType#ALL}
     * and also all processors for other event types are notified</li>
     * <li>{@link EventType} != {@link EventType#ALL}: only processor registered for the type are notified</li>
     * </ul>
     *
     * @param eventType event type
     */
    public synchronized static void notifyEventProcessors(EventType eventType) {
        switch (eventType) {
            case ALL:
                for (Map.Entry<EventType, List<EventProcessor>> entry : processors.entrySet()) {
                    for (EventProcessor processor : entry.getValue()) {
                        processor.processEvent(entry.getKey());
                    }
                }
                break;
            default:
                if (processors.containsKey(eventType)) {
                    for (EventProcessor processor : processors.get(eventType)) {
                        processor.processEvent(eventType);
                    }
                }
        }
    }

}
