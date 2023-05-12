package yan0kom.userbal.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        //ObjectMapper mapper = JsonMapper.builder()
        //        .findAndAddModules()
        //        .build();
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues();
                //.serializeValuesWith(RedisSerializationContext.SerializationPair
                //        .fromSerializer(new GenericJackson2JsonRedisSerializer(mapper)));
    }
}
