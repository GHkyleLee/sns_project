package com.example.fastcampusmysql.config;


import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jasyptConfig
 * 암호화 설정
 *
 * @author gh.LEE
 * @since 2024.03.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2024.03.08  gh.Lee       최초 생성
 *
 *  </pre>
 */
@Configuration
public class JasyptConfig {

    @Value("${jasypt.encryptor.key}")
    private String key;


    @Bean("jasyptEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();		//설정

        config.setPassword(key);					// 암호화 및 복호화에 사용할 비밀 키 설정
        config.setAlgorithm("PBEWithMD5AndDES"); // 암호화에서 사용할 알고림즈 설정
        config.setKeyObtentionIterations("1000");	// 암호화 키 생성 반복 횟수를 설정
        config.setPoolSize("1");					// 암호화에서 사용할 스레드 풀 크기 설정
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");	// salt 생성 클래스 지정
        config.setStringOutputType("base64"); // 암호화 타입 인코딩
        encryptor.setConfig(config);				// 암호화 정보 set
        return encryptor;
    }
}