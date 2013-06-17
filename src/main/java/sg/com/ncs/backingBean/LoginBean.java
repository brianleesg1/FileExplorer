package sg.com.ncs.backingBean;

import org.apache.myfaces.extensions.validator.baseval.annotation.Pattern;
import org.apache.myfaces.extensions.validator.baseval.annotation.Required;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import sg.com.ncs.common.MessageUtil;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 8/6/13
 * Time: 9:52 AM
 * To change this template use File | Settings | File Templates.
 */

@Component
@Scope("request")
public class LoginBean implements PhaseListener {

    Logger log = LoggerFactory.getLogger(LoginBean.class);
    @Required
    private String j_username;
    @Required
    private String j_password;
    private boolean storeUser = false;
    private boolean logIn = false;

    public String getJ_username() {
        return j_username;
    }

    public void setJ_username(String j_username) {
        this.j_username = j_username;
    }

    public String getJ_password() {
        return j_password;
    }

    public void setJ_password(String j_password) {
        this.j_password = j_password;
    }

    public boolean isStoreUser() {
        return storeUser;
    }

    public void setStoreUser(boolean storeUser) {
        this.storeUser = storeUser;
    }

    public boolean isLogIn() {
        return logIn;
    }

    public void setLogIn(boolean logIn) {
        this.logIn = logIn;
    }

    public String loginAction() throws IOException, ServletException
    {
        log.info("loginAction");
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest)context.getRequest()).getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        log.info("afterPhase");
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
        log.info("beforePhase");

        Exception e = (Exception) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (e instanceof BadCredentialsException) {
            log.info("login error -> " + e);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, MessageUtil.getMessage("login.failed"), e.getMessage()));
        }
        else if (e instanceof SessionAuthenticationException) {
            log.info("login error -> " + e);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,MessageUtil.getMessage("login.exceed.maximum"), e.getMessage()));
        }
        else if (e != null) {
            e.printStackTrace();;
        }

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
