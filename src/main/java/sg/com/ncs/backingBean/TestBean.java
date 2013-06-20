package sg.com.ncs.backingBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 18/6/13
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */

@Component
@Scope("session")
public class TestBean {
    Logger log = LoggerFactory.getLogger(TestBean.class);

    private int value = 0;


    public int getValue() {
        log.info("getValue -> " + value);
        return value;
    }

    public void setValue(int value) {
        log.info("setValue -> " + value);
        this.value = value;
    }

    public void value() {
        value++;
    }



    public void test() {
        value++;
    }
}
