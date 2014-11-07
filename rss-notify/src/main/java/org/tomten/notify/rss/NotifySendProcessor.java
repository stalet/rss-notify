package org.tomten.notify.rss;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ståle Tomten
 */
public class NotifySendProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(NotifySendProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        LOG.debug("process() " + exchange.getIn().getBody(String.class));
        new ProcessBuilder("/usr/bin/notify-send", exchange.getIn().getHeader("title",
                "Rss-Notification", String.class), exchange.getIn().getBody(String.class)).start();
        propagateInfo(exchange);
    }

    /**
     * Ensure that all headers and attachements are propagated to the next processor.
     *
     * @param exchange the processor exchange
     */
    private void propagateInfo(final Exchange exchange) {
        // copy headers from IN to OUT to propagate them
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        exchange.getOut().setAttachments(exchange.getIn().getAttachments());
        exchange.getOut().setBody(exchange.getIn().getBody());
    }

}
