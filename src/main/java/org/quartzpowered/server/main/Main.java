package org.quartzpowered.server.main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.quartzpowered.server.Server;
import org.quartzpowered.server.inject.ServerModule;

public class Main {
    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new ServerModule());
        injector.getInstance(Server.class).main(args);

        System.in.read();
    }
}
