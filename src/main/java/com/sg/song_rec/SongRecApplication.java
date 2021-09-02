package com.sg.song_rec;

import com.sg.song_rec.data.*;
import com.sg.song_rec.data.database.UserDbDao;
import com.sg.song_rec.data.rest.AudioFeaturesRestDao;
import com.sg.song_rec.data.rest.RecommendationRestDao;
import com.sg.song_rec.data.rest.TrackRestDao;
import com.sg.song_rec.data.rest.UserAffinityRestDao;
import com.sg.song_rec.entities.application.*;
import com.sg.song_rec.entities.authorization.OAuth2Token;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class SongRecApplication {
	public static void main(String[] args) {
		SpringApplication.run(SongRecApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
