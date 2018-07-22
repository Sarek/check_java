/*
 * Copyright (c) 2015, Sebastian Schäfer <me@sebastianschaefer.net>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of check_java nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL SEBASTIAN SCHÄFER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.l8systems.check_java;

import javax.management.*;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * JMX interface class.
 *
 * This class allows to connect to a JMX server and retrieve results from MBeans.
 */
public class JMXClient {
    private MBeanServerConnection mbsc;
    private JMXConnector connector;

    /**
     * An exemplary listener to retrieve notifications through JMX.
     */
    public static class ClientListener implements NotificationListener {

        public void handleNotification(Notification notification,
                                       Object handback) {
            System.out.println("\nReceived notification:");
            System.out.println("\tClassName: " + notification.getClass().getName());
            System.out.println("\tSource: " + notification.getSource());
            System.out.println("\tType: " + notification.getType());
            System.out.println("\tMessage: " + notification.getMessage());
            if (notification instanceof AttributeChangeNotification) {
                AttributeChangeNotification acn =
                        (AttributeChangeNotification) notification;
                System.out.println("\tAttributeName: " + acn.getAttributeName());
                System.out.println("\tAttributeType: " + acn.getAttributeType());
                System.out.println("\tNewValue: " + acn.getNewValue());
                System.out.println("\tOldValue: " + acn.getOldValue());
            }
        }
    }

    /**
     * Create a new connection to a JMX server.
     *
     * @param location the URL of the JMX server
     * @throws MalformedURLException
     * @throws java.io.IOException
     */
    public JMXClient(String location) throws MalformedURLException, java.io.IOException {
        System.out.println("Connecting to JMX server");
	
	String jmxUser = System.getenv("check_java_jmxUser");
	String jmxPass = System.getenv("check_java_jmxPass");
	
	Map<String, Object> env = new HashMap<>();
	String[] creds = new String[] {jmxUser, jmxPass};
        env.put(JMXConnector.CREDENTIALS, creds);
	
        JMXServiceURL url = new JMXServiceURL(location);
	
        connector = JMXConnectorFactory.connect(url, env);
	
        mbsc = connector.getMBeanServerConnection();
    }

    /**
     * Return the available Domains on the server
     * @return a collection of available domains
     * @throws java.io.IOException
     */
    public String[] getDomains() throws java.io.IOException {
        String[] domains = mbsc.getDomains();
        Arrays.sort(domains);

        return domains;
    }

    /**
     * Return the JMX server's default domain.
     * @return the default domain
     * @throws java.io.IOException
     */
    public String getDefaultDomain() throws java.io.IOException {
        return mbsc.getDefaultDomain();
    }

    /**
     * Return the number of available MBeans on the JMX server
     * @return the number of MBeans
     * @throws java.io.IOException
     */
    public int getMBeanCount() throws java.io.IOException {
        return mbsc.getMBeanCount();
    }

    /**
     * Return all MBean names on the server
     * @return the MBean names
     * @throws java.io.IOException
     */
    public Set<ObjectName> getAllMBeans() throws java.io.IOException {
        return mbsc.queryNames(null, null);
    }

    /**
     * Retrieve information on a specific MBean available through the JMX server
     * @param objectName the MBean in question
     * @return the information on the MBean
     * @throws MalformedObjectNameException
     * @throws IntrospectionException
     * @throws ReflectionException
     * @throws InstanceNotFoundException
     * @throws IOException
     */
    public MBeanInfo getMBeanInfo(String objectName) throws MalformedObjectNameException, IntrospectionException, ReflectionException, InstanceNotFoundException, IOException {
        ObjectName obj = new ObjectName(objectName);

        return mbsc.getMBeanInfo(obj);
    }

    public Object getAttribute(String objectName, String attribute, String subAttribute) throws AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, IOException, MalformedObjectNameException {
        ObjectName obj = new ObjectName(objectName);
        Object val = mbsc.getAttribute(obj, attribute);

        if (val instanceof CompositeDataSupport && subAttribute != null) {
            return ((CompositeDataSupport) val).get(subAttribute);
        } else {
            return val;
        }
    }

    public void close() throws IOException {
        connector.close();
    }
}
