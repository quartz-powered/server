package org.quartzpowered.common.eventbus;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.common.Properties;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;

import javax.inject.Singleton;

@Singleton
public class EventBus {
    private MBassador bus = new MBassador(new BusConfiguration()
            .addFeature(Feature.SyncPubSub.Default())
            .addFeature(Feature.AsynchronousHandlerInvocation.Default())
            .addFeature(Feature.AsynchronousMessageDispatch.Default())
            .setProperty(Properties.Handler.PublicationError, (IPublicationErrorHandler) error -> error.getCause().printStackTrace())
    );

    public void subscribe(Object handler) {
        bus.subscribe(handler);
    }

    @SuppressWarnings("unchecked")
    public void publish(Object message) {
        bus.publish(message);
    }
}
