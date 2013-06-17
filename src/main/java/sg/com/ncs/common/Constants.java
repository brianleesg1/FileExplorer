package sg.com.ncs.common;

import org.springframework.stereotype.Component;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 9/5/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@ManagedBean
@ApplicationScoped
public class Constants {

    public static final String EMPTY_STR = "";
    public static final String ZERO_VALUE = "0";
    public static final String SPACE = " ";
    public static final String DASH = " - ";
    public static final String SEMICOLON = ";";
    public static final String HASH = "#";
    public static final String COMMA = ",";
    public static final String NO = "N";
    public static final String YES = "Y";
    public static final String NO_DESC = "No";
    public static final String YES_DESC = "Yes";
    public static final String ACTIVE = "A";
    public static final String VALID = "V";
    public static final String INACTIVE = "I";

    public static final String APPBASE_APP_ENV = "VITAS_APP_ENV";
    public static final String DATE_PATTERN = "dd-MM-yyyy";

    /* EL constants */
    String date_pattern = DATE_PATTERN;

    public String getDate_pattern() {
        System.out.println("getDate_pattern : " + date_pattern);
        return date_pattern;
    }

    public void setDate_pattern(String date_pattern) {
        this.date_pattern = date_pattern;
    }

}
