package org.tomten.notify.rss;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.atom.AtomConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class CamelConfiguration {

    private final static DateFormat FORMAT = new SimpleDateFormat(AtomConverter.DATE_PATTERN_NO_TIMEZONE);

    private final static String IRC_USERNAME = "JenkinsBoT1000";
    private final static String IRC_HOST = "irc.serv.as2116.net:6667";
    private final static String IRC_CHANNEL = "#bss";

    @Bean
    RouteBuilder jenkinsRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                String rssURL = "http://d1jenkins01.dev.as2116.net:8080/rssFailed";

                from("atom:" + rssURL + "?lastUpdate=" + FORMAT.format(new Date())).
                        to("bean:atomValuePicker").
                        to("irc:" + IRC_USERNAME + "@" + IRC_HOST + "/" + IRC_CHANNEL);

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
