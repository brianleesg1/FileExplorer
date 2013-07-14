package sg.com.ncs.backingBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 10/6/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("request")
public class LogoutBean extends BaseBackingBean {


    public String logoutAction() throws IOException, ServletException {
        log.info("logoutAction");
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest)context.getRequest()).getRequestDispatcher("/j_spring_security_logout");
        dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }
}
