package sg.com.ncs.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 17/6/13
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("singleton")
public class MessageUtil {

    public static String getMessage(String key) {

        try {
            ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
            bean.setBasename("messages");
            return bean.getMessage(key, null, Locale.getDefault());
        }
        catch (Exception e) {
            return "Unresolved key: " + key;
        }

    }
}

