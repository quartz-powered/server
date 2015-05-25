package org.quartzpowered.common.inject;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.quartzpowered.common.inject.logger.LoggerTypeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonModule extends AbstractModule {
    @Override
    protected void configure() {
        // Inject anonymous logger by default
        bind(Logger.class).toInstance(LoggerFactory.getLogger("Anonymous"));

        // Replace logger with non-anonymous logger
        bindListener(Matchers.any(), new LoggerTypeListener());

        bind(ObjectMapper.class).toInstance(JsonFactory.create());
    }
}
