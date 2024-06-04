package jp.co.nss.ojt2024.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/login").permitAll() // /loginへのアクセスは認証不要
                                                .anyRequest().authenticated() // それ以外のリクエストは認証を要求
                                )
                                .formLogin(formLogin -> formLogin
                                                .loginPage("/login") // ログインページを設定
                                                .defaultSuccessUrl("/hello", true) // 認証成功後にリダイレクトするURLを設定
                                                .failureUrl("/login?error=true") // 認証失敗時のリダイレクトURL
                                                .permitAll() // ログインページへのアクセスは許可
                                )
                                .logout(logout -> logout
                                                .permitAll() // ログアウトページへのアクセスを許可
                                );
                return http.build();
        }
}
