package de.werdasliesstistdoof.websocket.server.util;

import de.werdasliesstistdoof.websocket.server.client.ClientManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

public class LogAppender extends AbstractAppender {

    public LogAppender() {
        super("SpigotWebSocketAppender", null, null);
        start();
    }

    @Override
    public void append(LogEvent event) {
        ClientManager.broadcast(event.getMessage().getFormattedMessage());
    }

}
