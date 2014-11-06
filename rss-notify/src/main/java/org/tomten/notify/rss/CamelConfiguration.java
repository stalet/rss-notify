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
                from("timer://foo?fixedRate=true&period=5000").
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
                String rssURL = "https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-rss/temp/SearchRequest.xml"
                        + "?pid=12311211&sorter/field=issuekey&sorter/order=DESC&tempMax=1000&delay=10s";

                from("rss:" + rssURL).
                        marshal().rss().
                        setBody(xpath("/rss/channel/item/title/text()")).
                        transform(body().prepend("Jira: ")).
                        to("log:jirabot?showHeaders=false&showExchangePattern=false&showBodyType=false");
            }
        };
    }

    // Collaborators fixtures
    @Autowired
    CamelContext camelContext;

}
