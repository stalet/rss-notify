package org.tomten.notify.rss;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfiguration {

    @Bean
    RouteBuilder helloWorldRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("timer://foo?fixedRate=true&period=60000").
                        setBody().constant("<hello>world!</hello>").
                        log(">>> ${body}");
            }
        };
    }

    @Bean
    RouteBuilder rssRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                String rssURL = "http://www.vg.no/rss/feed/?categories=&keywords=&limit=10&format=rss";

                from("rss:" + rssURL).
                        marshal().rss().
                        setBody(xpath("/rss/channel/item/title/text()")).
                        setHeader("title").constant("VG Nyheter").
                        to("log:rssRoute?showHeaders=false&showExchangePattern=false&showBodyType=false").
                        process(new NotifySendProcessor());

            }
        };
    }

    // Collaborators fixtures
    @Autowired
    CamelContext camelContext;

}
