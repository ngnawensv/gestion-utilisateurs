/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author PC-NGNAWEN
 * Cette classe permet de declarer les beans. Ce que permettra à Spring de creer une instance de cette objet pour nous
 * Par exemple le bean BCryptPasswordEncoder
 */
/*@Configuration: indique a spring que cette classe est une source de configuration des beans*/
@Configuration
public class BeanConfiguration implements WebMvcConfigurer {

    /*@Bean: permet que je puisse creer une instance de BCryptPasswordEncoder en appliquant l'annotation @Autowired
     de Spring partout ou on besoin */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

}
