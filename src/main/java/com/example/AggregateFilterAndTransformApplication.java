package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.aggregate.AggregateApplicationBuilder;
import org.springframework.cloud.stream.app.filter.processor.rabbit.FilterProcessorRabbitApplication;
import org.springframework.cloud.stream.app.http.source.rabbit.HttpSourceRabbitApplication;
import org.springframework.cloud.stream.app.transform.processor.rabbit.TransformProcessorRabbitApplication;

@SpringBootApplication
public class AggregateFilterAndTransformApplication {

	public static void main(String[] args) {
		new AggregateApplicationBuilder()
				.from(HttpSourceRabbitApplication.class)
				.via(FilterProcessorRabbitApplication.class)
				.args("--expression=#jsonPath(payload,'$.lang').equals('ja')")
				.to(TransformProcessorRabbitApplication.class)
				.args("--expression=payload.user.screen_name + ' ' + payload.text")
				.run(args);
	}
}
