package org.rta.security.config;

import org.rta.security.RtaAuthenticationEntryPoint;
import org.rta.security.RtaAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RtaAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public RtaAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		RtaAuthenticationTokenFilter authenticationTokenFilter = new RtaAuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				// don't create session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// Each request should be authenticated except /login api
				.authorizeRequests().antMatchers("/login*/**").permitAll().antMatchers("/formdetails*/**").permitAll()
				.antMatchers("/payment/sbi/result*/**").permitAll().antMatchers("/adminsbiverification*/**").permitAll()
                .antMatchers("/adminsbiverificationgatewayresp*/**").permitAll()
                .antMatchers("/insurancetype*/**").permitAll().antMatchers("/addresstype*/**").permitAll()
                .antMatchers("/ownershiptype*/**").permitAll().antMatchers("/taxtype*/**").permitAll()
                .antMatchers("/district*/**").permitAll().antMatchers("/state*/**").permitAll()
                .antMatchers("/mandals*/**").permitAll().antMatchers("/postoffices*/**").permitAll()
                .antMatchers("/qualification*/**").permitAll().antMatchers("/country*/**").permitAll()
                .antMatchers("/registcategory*/**").permitAll().antMatchers("/designation*/**").permitAll()
                .antMatchers("/rtaoffices*/**").permitAll().antMatchers("/permittype*/**").permitAll()
                .antMatchers("/districts*/**").permitAll().antMatchers("/mandal*/**").permitAll()
                .antMatchers("/rta*/**").permitAll().antMatchers("/rtaoffices*/**").permitAll().antMatchers("/testmail*/**").permitAll()
                .antMatchers("/iib/vehicle/details*/**").permitAll()
                .antMatchers("/testsyncfitness*/**").permitAll()
                .antMatchers("/insurancedetails/{vehiclercid}").permitAll()
                .antMatchers("/iib/insurancedetails/{regNo}/**").permitAll()
                .antMatchers("/neighbouringdistrict/**").permitAll()
                .antMatchers("/neighbouringstate/**").permitAll()
                .antMatchers("/vehicle/migration/**").permitAll()
                .antMatchers("/pancard/details/**").permitAll()
                .antMatchers("/applicationdetails/status/**").permitAll()
                .antMatchers("/freshrcconsent").permitAll()
                .anyRequest().fullyAuthenticated();

		// Custom JWT based security filter
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}
}
