package cm.belrose.gestionutilisateur.servicesImpl;

import cm.belrose.gestionutilisateur.entities.User1;
import cm.belrose.gestionutilisateur.exception.ResourceNotFoundException;
import cm.belrose.gestionutilisateur.services.RoleService;
import cm.belrose.gestionutilisateur.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            User1 user11 =userService.findByLogin(login).get();
            if(user11 ==null){
                throw new UsernameNotFoundException(login);
            }
            Collection<GrantedAuthority> authorities=new ArrayList<>();
            user11.getRoles().forEach(r->{
                authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
            });
            return new User(user11.getLogin(), user11.getPassword(),authorities);
        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }


    }
}
