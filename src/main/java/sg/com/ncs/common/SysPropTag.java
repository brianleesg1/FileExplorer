package sg.com.ncs.common;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;



/**
 * <p>This <code>MenuTag</code> class is the implements the tag for
 * the web JSP. It is the entry point for displaying the menu.
 * The tag will retrieve the corresponding classes from the
 * application context and invoke the renderer to display
 * the menu.</p>
 * <p>The following objects are being used.
 * <ol>
 * <li>Menu - the entire menu structure</li>
 * <li>Renderer - the renderer of the menu structure</li>
 * <li>AccessChecker - the checker to determine whether the
 * menu is allowed to be shown</li>
 * <li>Cache - the map which stores the functions allowed
 * by each user</li>
 * <li>UserId - the user id of the current user</li>
 * </ol>
 * </p>
 */
public class SysPropTag extends TagSupport {

    private static final Log log = LogFactory.getLog(SysPropTag.class);
    private String key = "app.junction";
    private String defaultValue = "";

    public void setKey(String value) { this.key = value; }
    public String getKey() { return this.key; }

    public void setDefault(String value) { this.defaultValue = value; }
    public String getDefault() { return this.defaultValue; }

    /**
     * Processes the menu when the start tag is encountered.
     * @return an int code to indicate that there is no body tag to be evaluated
     */
    public int doStartTag() throws JspException {

        ServletContext ctx = this.pageContext.getServletConfig().getServletContext();
        HttpSession session = this.pageContext.getSession();

        if (this.key == null) {
            this.key = "app.junction";
        }
        if (this.defaultValue == null) {
            this.defaultValue = "";
        }
        String junction = SystemProperties.getProperty(this.key);
        if (junction == null) {
            return (SKIP_BODY);
        }
        try {
            JspWriter jspw = this.pageContext.getOut();
            jspw.print(junction);
        } catch (Exception e) {
            if (log.isErrorEnabled()) { log.error(e); }
        }
        return (SKIP_BODY);
    }

}

