package org.marketcetera.event.impl;

import javax.annotation.concurrent.ThreadSafe;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.marketcetera.event.FutureEvent;
import org.marketcetera.event.MarketstatEvent;
import org.marketcetera.event.beans.FutureBean;
import org.marketcetera.event.beans.MarketstatBean;
import org.marketcetera.trade.*;
import org.marketcetera.util.misc.ClassVersion;

/* $License$ */

/**
 * Provides a Future implementation of {@link MarketstatEvent}.
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id: FutureMarketstatEventImpl.java 16867 2014-03-23 23:06:05Z colin $
 * @since 2.1.0
 */
@ThreadSafe
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="futureMarketstat")
@ClassVersion("$Id: FutureMarketstatEventImpl.java 16867 2014-03-23 23:06:05Z colin $")
public class FutureMarketstatEventImpl
        extends AbstractMarketstatEventImpl
        implements FutureEvent
{
    /* (non-Javadoc)
     * @see org.marketcetera.event.FutureEvent#getDeliveryType()
     */
    @Override
    public DeliveryType getDeliveryType()
    {
        return future.getDeliveryType();
    }
    /* (non-Javadoc)
     * @see org.marketcetera.event.FutureEvent#getProviderSymbol()
     */
    @Override
    public String getProviderSymbol()
    {
        return future.getProviderSymbol();
    }
    /* (non-Javadoc)
     * @see org.marketcetera.event.FutureEvent#getStandardType()
     */
    @Override
    public StandardType getStandardType()
    {
        return future.getStandardType();
    }
    /* (non-Javadoc)
     * @see org.marketcetera.event.FutureEvent#getType()
     */
    @Override
    public FutureType getType()
    {
        return future.getType();
    }
    /* (non-Javadoc)
     * @see org.marketcetera.event.FutureEvent#getUnderylingAssetType()
     */
    @Override
    public FutureUnderlyingAssetType getUnderylingAssetType()
    {
        return future.getUnderlyingAssetType();
    }
    /* (non-Javadoc)
     * @see org.marketcetera.event.HasFuture#getInstrument()
     */
    @Override
    public Future getInstrument()
    {
        return (Future)super.getInstrument();
    }
    /* (non-Javadoc)
     * @see org.marketcetera.event.FutureEvent#getContractSize()
     */
    @Override
    public int getContractSize()
    {
        return future.getContractSize();
    }
    /**
     * Create a new FutureMarketstatEventImpl instance.
     *
     * @param inMarketstatBean a <code>MarketstatBean</code> value
     * @throws IllegalArgumentException if <code>MessageId</code> &lt; 0
     * @throws IllegalArgumentException if <code>Timestamp</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>Instrument</code> is <code>null</code>
     */
    FutureMarketstatEventImpl(MarketstatBean inMarketstat,
                              FutureBean inFuture)
    {
        super(inMarketstat);
        future = inFuture;
        future.validate();
    }
    /**
     * Create a new FutureMarketstatEventImpl instance.
     * 
     * <p>This constructor is intended to be used by JAXB.
     */
    @SuppressWarnings("unused")
    private FutureMarketstatEventImpl()
    {
        future = new FutureBean();
    }
    /**
     * the future attributes 
     */
    @XmlElement
    private final FutureBean future;
    private static final long serialVersionUID = 1L;
}
