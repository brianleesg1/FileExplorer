package sg.com.ncs.backingBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 14/7/13
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseBackingBean implements Serializable {

    transient protected Logger log = LoggerFactory.getLogger(this.getClass());
}
