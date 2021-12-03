package ro.giohnnysoftware.springlesson12security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    /*
    UserDetails = the bean that Spring uses to load the user
    UserDetailsService -> UserDetailsManager
    PasswordEncoder
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();
        var u1 = User.withUsername("bill")
                               .password("12345")
                               .roles("ADMIN")
                               .authorities("write")
                               .build();
        var u2 = User.withUsername("john")
                .password("12345")
                .roles("MANAGER")
                .authorities("read")
                .build();

        manager.createUser(u1);
        manager.createUser(u2);

        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
/*

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET)
                    .access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST)
                    .access("hasAnyRole('ROLE_MANAGER')")
                .anyRequest().permitAll();
*/
        http.authorizeRequests()
                .antMatchers("/hello")
                .access("hasAnyRole('ADMIN')")
                .anyRequest().permitAll();
        //super.configure(http);
    }
}
