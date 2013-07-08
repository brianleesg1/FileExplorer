package sg.com.ncs.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 24/6/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserDetailService implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(UserDetailService.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username = " + username);


        if (username.equals("admin")) {
            ArrayList<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
            grantedAuthority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            grantedAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
            //encoded password is password
            User user = new User("admin","ea918c9123b279d593868c551ccbeab4b6f56105a2fdd9f484a3b0c3695447cb4dee4d05e422f989",true,true,true,true, grantedAuthority);
            return user;
        }
        else
        if (username.equals("user1")) {
            ArrayList<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
            grantedAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
            //encoded password is password
            User user = new User("user1","3f485f72253aab8f71292c344436a89c19445e3b0845158964c202ee3854220aa3db7c8e41324c94",true,true,true,true, grantedAuthority);
            return user;
        }
        else {
            throw new UsernameNotFoundException("user not found");
        }




    }
}
