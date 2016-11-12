package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.aggregate.AggregateApplicationBuilder;
import org.springframework.cloud.stream.app.filter.processor.rabbit.FilterProcessorRabbitApplication;
import org.springframework.cloud.stream.app.transform.processor.rabbit.TransformProcessorRabbitApplication;
import org.springframework.cloud.stream.app.twitter.TwitterCredentials;
import org.springframework.cloud.stream.app.twitterstream.source.rabbit.TwitterstreamSourceRabbitApplication;

@SpringBootApplication
@EnableConfigurationProperties(TwitterCredentials.class)
public class AggregateFilterAndTransformApplication {

	@Autowired
	TwitterCredentials credentials;

	public static void main(String[] args) {
		new AggregateApplicationBuilder()
				.from(TwitterstreamSourceRabbitApplication.class)
				.args("--debug=true")
				.via(FilterProcessorRabbitApplication.class)
				.args("--expression=#jsonPath(payload,'$.lang').equals('ja')")
				.to(TransformProcessorRabbitApplication.class)
				.args("--expression=payload.user.screen_name + ' ' + payload.text")
				.run(args);
	}
}
