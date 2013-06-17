package sg.com.ncs.common;

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
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
