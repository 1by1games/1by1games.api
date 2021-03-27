package com.esgi.onebyone.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
open class WebSecurity(
    private val authProvider: AuthTokenSecurityProvider
) : WebSecurityConfigurerAdapter() {


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().cors().and()
            .authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .antMatchers(
                    "/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**").permitAll()
            .anyRequest().authenticated()
        http.addFilterAfter(JWTAuthorizationFilter(super.authenticationManager()), JWTAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.userDetailsService<UserDetailsService>(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
        auth.authenticationProvider(authProvider)
    }


    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource {
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", )
//        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())

        val configuration = CorsConfiguration().applyPermitDefaultValues()
        configuration.allowedMethods = HttpMethod.values().map { it.toString() }
        configuration.addExposedHeader(HttpHeaders.CONTENT_LENGTH)
        configuration.addExposedHeader(HttpHeaders.CONTENT_TYPE)
        configuration.addExposedHeader(HttpHeaders.LOCATION)
        configuration.addAllowedHeader("*")
        configuration.addAllowedOrigin("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}