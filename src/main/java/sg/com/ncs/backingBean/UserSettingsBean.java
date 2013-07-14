package sg.com.ncs.backingBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 18/6/13
 * Time: 9:19 AM
 * To change this template use File | Settings | File Templates.
 */

@Component
@Scope("session")
public class UserSettingsBean extends BaseBackingBean {

    private String theme = "aristo"; //default
    private Map<String,String> themes;

    private int thresholdSize = 51200;

    @PostConstruct
    public void init() {
        themes = new TreeMap<String, String>();
        themes.put("Aristo", "aristo");
        themes.put("Cupertino", "cupertino");
        themes.put("Afterdark", "afterdark");
    }

    public Map<String, String> getThemes() {
        return themes;
    }

    public String getTheme() {
        return theme;
    }

    public int getThresholdSize() {
        log.info("thresholdSize = " + thresholdSize);
        return thresholdSize;
    }

    public void setThresholdSize(int thresholdSize) {
        this.thresholdSize = thresholdSize;
    }
}
