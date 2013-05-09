package sg.com.ncs.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 9/5/13
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LookUpTable<K,V> {

    private HashMap<K,V> map = new HashMap<K,V>();

    Logger log = LoggerFactory.getLogger(LookUpTable.class);

    public LookUpTable() {
        log.info("lookup table initialised");
    }

    public void add(K key, V value) {
        map.put(key,value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public void clear() {
        log.info("lookuptable clear");
        map.clear();
    }

}
