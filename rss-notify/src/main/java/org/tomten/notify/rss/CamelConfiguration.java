package org.tomten.notify.rss;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfiguration {

    @Bean
    RouteBuilder jenkinsRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                String rssURL = "https://d1jenkins01.dev.as2116.net/rssAll";

                from("atom:" + rssURL).
                        to("bean:atomValuePicker").
                        to("bean:notifySendProcessor");

            }
        };
    }
/*

    @Bean
    RouteBuilder rssRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                String rssURL = "http://www.vg.no/rss/feed/?categories=&keywords=&limit=10&format=rss";

                from("rss:" + rssURL).
                        marshal().rss().
                        setHeader("title", xpath("/rss/channel/item/title/text()")).
                        setHeader("description", xpath("/rss/channel/item/description/text()")).
                        to("bean:notifySendProcessor");

            }
        };
    }
*/
    // Collaborators fixtures
    @Autowired
    CamelContext camelContext;

}
