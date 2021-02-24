package uk.co.marvel.character.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Value("${jasypt.encryptor.password}")
    private String password;

    @Bean
    public StringEncryptor myStringEncryptor() {
        final SimpleStringPBEConfig config =
                new SimpleStringPBEConfig() {
                    {
                        this.setPassword(password);
                        this.setPoolSize(1);
                        this.setAlgorithm("PBEWithMD5AndDES");

                    }
                };
        final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        return encryptor;
    }
}
