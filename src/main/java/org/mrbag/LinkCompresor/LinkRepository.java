package org.mrbag.LinkCompresor;

import org.mrbag.LinkCompresor.Entity.IStringKeyGenerator;
import org.mrbag.LinkCompresor.Entity.KeyLinkAttach;
import org.mrbag.LinkCompresor.Entity.Link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LinkRepository {

	private final RedisTemplate<String, Link> template;  
	
	private final IStringKeyGenerator generator; 

	@Autowired
	public LinkRepository(
			@Autowired RedisTemplate<String, Link> template,
			@Autowired IStringKeyGenerator generator
			) {
		this.template = template;
		this.generator = generator;
	}
	
	public String put(Link link) {
		//mayby add implement operation contron unuclye
		template.opsForValue().set(generator.get(), link);
		return generator.next();
	}

	public KeyLinkAttach get(String key) {
		if (template.hasKey(key))
			return new KeyLinkAttach(key, template.opsForValue().get(key));
		return new KeyLinkAttach(key, null);
	}
	
	
}
