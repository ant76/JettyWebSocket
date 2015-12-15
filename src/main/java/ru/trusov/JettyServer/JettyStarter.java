package ru.trusov.JettyServer;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import ru.trusov.JettyWebSocket.SocketHandler;

/**
 * Hello world!
 *
 */
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

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {new SocketHandler(), resourceHandler});
        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }

    }
}
