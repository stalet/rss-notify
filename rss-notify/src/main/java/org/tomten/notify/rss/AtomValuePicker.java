package org.tomten.notify.rss;

import org.apache.abdera.model.Entry;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class AtomValuePicker implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Entry entry = exchange.getIn().getBody(Entry.class);
        exchange.getOut().setHeader("title", entry.getTitle());
        final String description = "Jenkins build FAILED: " + entry.getTitle() + "(" + entry.getLink("alternate").getHref() + ")";
        exchange.getOut().setHeader("description", description);
        exchange.getOut().setBody(description);
    }
}
