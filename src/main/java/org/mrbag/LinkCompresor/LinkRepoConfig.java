package org.mrbag.LinkCompresor;

import org.mrbag.LinkCompresor.Entity.IStringKeyGenerator;
import org.mrbag.LinkCompresor.Entity.Link;
import org.mrbag.LinkCompresor.Entity.StringKeyGenerator.XorShiftStringGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class LinkRepoConfig {

	@Bean
	public RedisTemplate<String, Link> redisTemplate(RedisConnectionFactory fact){
		RedisTemplate<String, Link> temp = new RedisTemplate<>();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		temp.setConnectionFactory(fact);
		temp.setKeySerializer(new StringRedisSerializer());
		temp.setValueSerializer(new Jackson2JsonRedisSerializer<Link>(mapper, Link.class));
		
		return temp;
	}
	
	@Bean
	//to do only test;
	public IStringKeyGenerator configKeyGenerator() {
		
		return new XorShiftStringGenerator(0);
	}
	
	
}
