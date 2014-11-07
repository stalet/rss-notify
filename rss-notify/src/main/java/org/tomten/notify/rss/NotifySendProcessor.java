package org.tomten.notify.rss;

import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Ståle Tomten
 */
@Component
public class NotifySendProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(NotifySendProcessor.class);

    public void process(@Header("title") final String title, @Header("description") final String description) throws Exception {
        LOG.info("process() " + title);
        new ProcessBuilder("/usr/bin/notify-send", title, description).start();
    }

}
