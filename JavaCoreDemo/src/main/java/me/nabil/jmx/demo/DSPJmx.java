package me.nabil.jmx.demo;
import java.io.IOException;
import java.util.Set;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
/**
 * Created by baidu on 15/9/14.
 */
public class DSPJmx {
    public static void main(String[] args) throws IOException,
            MalformedObjectNameException, NullPointerException {
        JMXServiceURL url = new JMXServiceURL(
                "service:jmx:rmi:///jndi/rmi://10.100.43.30:8010/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(url);
        MBeanServerConnection connection = connector.getMBeanServerConnection();

        // list domains
        String[] domains = connection.getDomains();
        for (String domain : domains) {
            System.out.println("domain : " + domain);
        }

        // list ObjectNames
        Set<ObjectName> names = connection.queryNames(null, null);
        for (ObjectName name : names) {
            System.out.println("ObjectName : " + name);
        }

        // close connection
        connector.close();
    }
}
