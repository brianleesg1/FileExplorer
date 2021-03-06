package sg.com.ncs.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 17/6/13
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */

@Component
@Scope("session")
public class UserSessionInfo implements Serializable {
    Logger log = LoggerFactory.getLogger(UserSessionInfo.class);
    String username;

    public UserSessionInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
    }

    public String getUsername() {
        log.info("getUsername -> " + username);
        return username;
    }

    public void setUsername(String username) {
        log.info("setUsername -> " + username);
        this.username = username;
    }
}
