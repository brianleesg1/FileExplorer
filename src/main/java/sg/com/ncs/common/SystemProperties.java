package sg.com.ncs.common;


import org.jasypt.commons.CommonUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.util.Properties;

public class SystemProperties implements ApplicationContextAware {

    static Logger log = LoggerFactory.getLogger(SystemProperties.class);

    private static Properties propertiesMap = new Properties();
    private ApplicationContext context;

    private static StandardPBEStringEncryptor encryptor;

    //private static String environment;
    public SystemProperties() {
    }

    public void init() {
        log.info("init");
        loadProperties();
        //SystemProperties.printProperties();
    }


    protected void loadProperties() {
        String environment = System.getenv().get(Constants.APPBASE_APP_ENV);
        log.error(Constants.APPBASE_APP_ENV + " = " + environment);

        if (StringUtil.isNullOrEmpty(environment)) {
            log.error("Unable to obtain env value. Please check your OS/VM environment setting!!!!!");
            //throw new RuntimeException("Unable to obtain env value. Please check your OS/VM environment setting!!!!!");
            log.error("DEFAULT ENVIRONMENT to DEV");
            environment = "DEV";
        }

        try {
            Resource sysRes = context.getResource("classpath:deploy/sys.properties");
            //log.info("input = " + sysRes.getFilename());
            //log.info("url = " + sysRes.getURL());
            //log.info("inputstream = " + sysRes.getInputStream());

            Properties temp = new Properties();
            temp.load(sysRes.getInputStream());

            for (Object key : temp.keySet()) {
                String keyStr = key.toString();
                String value = temp.getProperty(keyStr);

                if (value != null) {
                    if (value.trim().startsWith("ENC(")) {
                        String tempStr = CommonUtils.substringAfter(value, "ENC(");
                        tempStr = CommonUtils.substringBefore(tempStr, ")");
                        log.error("decrypting " + value);
                        //log.error(encryptor.decrypt(tempStr));
                        propertiesMap.put(keyStr,encryptor.decrypt(tempStr));
                    }
                    else {
                        propertiesMap.put(keyStr,value);
                    }

                }
            }

            Resource res = context.getResource("classpath:deploy/deploy-" + environment.trim() + ".properties");

            //log.info("input = " + res.getFilename());
            //log.info("url = " + res.getURL());
            //log.info("inputstream = " + res.getInputStream());

            temp = new Properties();
            temp.load(res.getInputStream());

            for (Object key : temp.keySet()) {
                String keyStr = key.toString();
                String value = temp.getProperty(keyStr);

                if (value != null) {
                    if (value.trim().startsWith("ENC(")) {
                        String tempStr = CommonUtils.substringAfter(value, "ENC(");
                        tempStr = CommonUtils.substringBefore(tempStr, ")");
                        log.error("decrypting " + value);
                        //log.error(encryptor.decrypt(tempStr));
                        propertiesMap.put(keyStr,encryptor.decrypt(tempStr));
                    }
                    else {
                        propertiesMap.put(keyStr,value);
                    }

                }
            }

            propertiesMap.put("environment",environment);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String name) {
        //log.info("name = " + name + " , property = " + propertiesMap.getProperty(name));
        return propertiesMap.getProperty(name);
    }

    public static void printProperties() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("SystemProperties: \n");
        for (Object key : propertiesMap.keySet()) {
            buffer.append(key.toString()).append(" = ").append(propertiesMap.getProperty(key.toString())).append("\n");
        }

        log.info(buffer.toString());
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        log.info("setApplicationContext");
        context = ctx;
    }

    public static String getEnvironment() {
        return propertiesMap.getProperty("environment");
    }

    public StandardPBEStringEncryptor getEncryptor() {
        return encryptor;
    }

    public void setEncryptor(StandardPBEStringEncryptor encryptor) {
        //log.info("setpassword");
        encryptor.setPassword("CrR3FqU9HdWB");
        SystemProperties.encryptor = encryptor;
    }
}