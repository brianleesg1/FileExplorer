package sg.com.ncs.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 9/5/13
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomPropertyPlaceholder extends PropertyPlaceholderConfigurer {

    static Logger log = LoggerFactory.getLogger(CustomPropertyPlaceholder.class);

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
        //log.info("placeholder = " + placeholder);
        //log.info("props = " + props);

        //log.info("value = " + SystemProperties.getProperty(placeholder));
        return SystemProperties.getProperty(placeholder);
    }

}

