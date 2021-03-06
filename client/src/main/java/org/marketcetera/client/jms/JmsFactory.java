package org.marketcetera.client.jms;

import javax.jms.ConnectionFactory;
import org.marketcetera.util.misc.ClassVersion;

/**
 * A factory of Spring-wrapped JMS connections.
 *
 * @author tlerios@marketcetera.com
 * @since 1.0.0
 * @version $Id: JmsFactory.java 16154 2012-07-14 16:34:05Z colin $
 */

/* $License$ */

@ClassVersion("$Id: JmsFactory.java 16154 2012-07-14 16:34:05Z colin $")
public class JmsFactory
{

    // INSTANCE DATA.

    private final ConnectionFactory mConnectionFactory;


    // CONSTRUCTORS.

    /**
     * Creates a new factory that uses the given standard JMS
     * connection factory to create connections.
     *
     * @param connectionFactory The factory.
     */    

    public JmsFactory
        (ConnectionFactory connectionFactory)
    {
        mConnectionFactory=connectionFactory;
    }


    // INSTANCE METHODS.

    /**
     * Returns the receiver's standard JMS connection factory.
     *
     * @return The factory.
     */

    public ConnectionFactory getConnectionFactory()
    {
        return mConnectionFactory;
    }
}
