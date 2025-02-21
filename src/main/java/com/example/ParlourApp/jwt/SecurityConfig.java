package com.example.ParlourApp.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.DeleteMapping;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class SecurityConfig
{
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)

    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.cors(Customizer.withDefaults())
                .authorizeRequests(authorizeRequest ->authorizeRequest
                        .requestMatchers("/api/admin/AdminReg","/api/user/UserReg","/api/parlour/ParlourReg")
                        .permitAll()
                        .requestMatchers("/api/admin/AdminLogin").permitAll()
                        .requestMatchers("/api/parlour/ParlourLogin").permitAll()
                        .requestMatchers("/api/user/UserLogin").permitAll()
                        .requestMatchers("/api/Categories/add").hasRole("ADMIN")
                        .requestMatchers("/api/SubCategory/add_Sub").hasRole("ADMIN")
                        .requestMatchers("/api/SubSubCategory/add_Sub").hasRole("ADMIN")
                        .requestMatchers("/api/admin/approve").hasRole("ADMIN")
                        .requestMatchers("/api/admin/parlour/delete").hasRole("ADMIN")
                        .requestMatchers("/api/admin/approve-deletion").hasRole("ADMIN")
                        .requestMatchers("/api/gst/addGst").hasRole("ADMIN")
                        .requestMatchers("/api/gst/updateGst").hasRole("ADMIN")
                        .requestMatchers("/api/gst/deleteGst").hasRole("ADMIN")
                        .requestMatchers("/api/discount/add").hasRole("ADMIN")
                        .requestMatchers("/api/appCharge/addCharge").hasRole("ADMIN")
                        .requestMatchers("/api/appCharge/updateCharge").hasRole("ADMIN")
                        .requestMatchers("/api/appCharge/deleteCharge").hasRole("ADMIN")
                        .requestMatchers("/api/Categories/add").hasRole("ADMIN")
                        .requestMatchers("/api/SubCategory/add_Sub").hasRole("ADMIN")
                        .requestMatchers("/api/SubSubCategory/add_Sub").hasRole("ADMIN")
                        .requestMatchers("/api/admin/approve-deletion").hasRole("ADMIN")
                        .requestMatchers("/api/parlour/update").hasRole("PARLOUR")
                        .requestMatchers("/api/request-deletion").hasRole("PARLOUR")
                        .requestMatchers("/api/parlour/delete").hasRole("PARLOUR")
                        .requestMatchers("/api/Items/AddItems").hasRole("PARLOUR")
                        .requestMatchers("/api/Items/update").hasRole("PARLOUR")
                        .requestMatchers("/api/Items/delete").hasRole("PARLOUR")
                        .requestMatchers("/api/employees/addEmployee").hasRole("PARLOUR")
                        .requestMatchers("/api/employees/update").hasRole("PARLOUR")
                        .requestMatchers("/api/employees/delete").hasRole("PARLOUR")
                        .requestMatchers("/api/offer/add").hasRole("PARLOUR")
                        .requestMatchers("/api/offer/update").hasRole("PARLOUR")
                        .requestMatchers("/api/offer/delete").hasRole("PARLOUR")


                        .requestMatchers("/api/admin/allRegisteredParlour").permitAll()
                        .requestMatchers("/api/Categories/all").permitAll()
                        .requestMatchers("/api/SubCategory/all").permitAll()
                        .requestMatchers("/api/SubSubCategory/all").permitAll()
                        .requestMatchers("/api/parlour/getAllParlours").permitAll()
                        .requestMatchers("/api/admin/parlour/deletion-requests").permitAll()
                        .requestMatchers("/api/parlour/id").permitAll()
                        .requestMatchers("/api/parlour/generate_otp").permitAll()
                        .requestMatchers("/api/parlour/forgot_password").permitAll()
                        .requestMatchers("/api/parlour/ParlourStatus").permitAll()
                        .requestMatchers("/api/parlour/name/{parlourName}").permitAll()
                        .requestMatchers("/api/Items/getAllItems").permitAll()
                        .requestMatchers("/api/Items/itemId").permitAll()
                        .requestMatchers("/api/Items/itemByParlourId").permitAll()
                        .requestMatchers("/api/employees/by-parlourName").permitAll()
                        .requestMatchers("/api/employees/employeeById").permitAll()
                        .requestMatchers("/api/user/generate-Otp-for-User").permitAll()
                        .requestMatchers( "/api/user/forgotPasswordUser").permitAll()
                        .requestMatchers("/api/user/userLocation").permitAll()
                        .requestMatchers("/api/gst/allGst").permitAll()
                        .requestMatchers("/api/discount/all").permitAll()
                        .requestMatchers("/api/offer/offerId").permitAll()
                        .requestMatchers("/api/offer/getAllOffers").permitAll()
                        .requestMatchers("/api/appCharge/allCharge").permitAll()
                        .requestMatchers("/api/employees/by-parlourId").permitAll()







                        .anyRequest().authenticated())
                .csrf(csrf->csrf.disable());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
