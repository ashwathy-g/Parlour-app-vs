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
                                .requestMatchers("/admin/AdminReg","/user/UserReg","/parlour/ParlourReg")
                                .permitAll()
                                .requestMatchers("/admin/AdminLogin").permitAll()
                                .requestMatchers("/parlour/ParlourLogin").permitAll()
                                .requestMatchers("/user/UserLogin").permitAll()
                                .requestMatchers("/Categories/add").hasRole("ADMIN")
                                .requestMatchers("/SubCategory/add_Sub").hasRole("ADMIN")
                                .requestMatchers("/SubSubCategory/add_Sub").hasRole("ADMIN")
                                .requestMatchers("/admin/approve/{id}").hasRole("ADMIN")
                                .requestMatchers("/admin/admin/parlour/delete/{id}").hasRole("ADMIN")
                                .requestMatchers("/admin/approve-deletion/{id}").hasRole("ADMIN")
                                .requestMatchers("/api/gst/addGst").hasRole("ADMIN")
                                .requestMatchers("/api/gst/updateGst/{Id}").hasRole("ADMIN")
                                .requestMatchers("/api/gst/deleteGst/{Id}").hasRole("ADMIN")
                                .requestMatchers("/parlour/update/{id}").hasRole("PARLOUR")
                                .requestMatchers("/request-deletion/{id}").hasRole("PARLOUR")
                                .requestMatchers("/parlour/delete/{id}").hasRole("PARLOUR")
                                .requestMatchers("/Items/AddItems").hasRole("PARLOUR")
                                .requestMatchers("/Items/{itemId}").hasRole("PARLOUR")
                                .requestMatchers("/employees/addEmployee").hasRole("PARLOUR")
                                .requestMatchers("/employees/updateEmployee/{employeeId}").hasRole("PARLOUR")
                                .requestMatchers("/employees/delete/{employeeId}").hasRole("PARLOUR")
                                .requestMatchers("/offers/offer").hasRole("PARLOUR")
                                .requestMatchers("/offers/{id}").hasRole("PARLOUR")
                                .requestMatchers("/offers/{id}").hasRole("PARLOUR")
                                .requestMatchers("/offer-categories/offer").hasRole("PARLOUR")
                                .requestMatchers("/offer-categories/{id}").hasRole("PARLOUR")
                                .requestMatchers("/offer-categories/{id}").hasRole("PARLOUR")
                                .requestMatchers("/userBill/create").hasRole("PARLOUR")
                                .requestMatchers("/api/cart/add").hasRole("USER")
                                .requestMatchers("/bookings/addBooking").hasRole("USER")
                                .requestMatchers("/orderDetails/createTransaction/{amount}/{userId}").hasRole("USER")
                                .requestMatchers("/verifypayment/payment").hasRole("USER")
                                .requestMatchers("/employees/by-parlourName").permitAll()
                                .requestMatchers("/employees/employeeById").permitAll()
                                .requestMatchers("/user/userLocation").permitAll()
                                .requestMatchers("/Items/getAllItems").permitAll()
                                .requestMatchers("/Categories/all").permitAll()
                                .requestMatchers("/SubCategory/all").permitAll()
                                .requestMatchers("/SubSubCategory/all").permitAll()
                                .requestMatchers("/Items/{itemId}").permitAll()
                                .requestMatchers("/parlour/{parlourId}/offers").permitAll()
                                .requestMatchers("/admin/allRegisteredParlour").permitAll()
                                .requestMatchers("/parlour/getAllParlours").permitAll()
                                .requestMatchers("/parlour/{id}").permitAll()
                                .requestMatchers("/parlour/generate_otp").permitAll()
                                .requestMatchers("/parlour/forgot_password").permitAll()
                                .requestMatchers("/parlour/name/{parlourName}").permitAll()
                                .requestMatchers("/user/generate-Otp-for-User").permitAll()
                                .requestMatchers( "/user/forgotPasswordUser").permitAll()
                                .requestMatchers("/api/gst/allGst").permitAll()
                                .requestMatchers("/userBill/{id}").permitAll()
                                .requestMatchers("/DashBoard/ids").permitAll()
                                .requestMatchers("/DashBoard/times").permitAll()
                                .requestMatchers("/DashBoard/prices").permitAll()
                                .requestMatchers("/DashBoard/statuses").permitAll()



                        .anyRequest().authenticated())
                .csrf(csrf->csrf.disable());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
