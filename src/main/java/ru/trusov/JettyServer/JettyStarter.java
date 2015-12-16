package ru.trusov.JettyServer;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import ru.trusov.JettyWebSocket.SocketHandler;

import java.net.URL;
import org.eclipse.jetty.webapp.WebAppContext;
import java.security.ProtectionDomain;

public class JettyStarter
{
    public static void main( String[] args ) {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        // add 1 handler
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[] { "index.html" });
        resourceHandler.setResourceBase(".");

        ProtectionDomain domain = JettyStarter.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();

        // add context
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar(location.toExternalForm());

        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[] {new SocketHandler(), resourceHandler});
        // first element  is webSocket handler
        // second element is first handler,
        // third element is webContext
        handlers.setHandlers(new Handler[] {new SocketHandler(), resourceHandler, webapp});
        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }

    }
}
