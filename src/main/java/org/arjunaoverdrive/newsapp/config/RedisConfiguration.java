package org.arjunaoverdrive.newsapp.config;

import org.arjunaoverdrive.newsapp.model.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class RedisConfiguration {

    @Value("${app.jwt.refreshTokenTTL}")
    private Duration refreshTokenTTL;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

        configuration.setHostName(redisProperties.getHost());
        configuration.setPort(redisProperties.getPort());

        return new JedisConnectionFactory(configuration);
    }

    public class RefreshTokenKeyspaceConfiguration extends KeyspaceConfiguration{
        private static final String REFRESH_TOKEN_KEYSPACE = "refresh_tokens";

        @Override
        protected Iterable<KeyspaceSettings> initialConfiguration() {
            KeyspaceSettings keyspaceSettings= new KeyspaceSettings(RefreshToken.class, REFRESH_TOKEN_KEYSPACE);

            keyspaceSettings.setTimeToLive(refreshTokenTTL.getSeconds());
            return Collections.singleton(keyspaceSettings);
        }
    }
}
