package org.marketcetera.trade;

import org.marketcetera.util.misc.ClassVersion;

/* $License$ */

/**
 * Indicates the commodity underlying asset type of a <code>Future</code>.
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id: CommodityUnderlyingAssetType.java 16154 2012-07-14 16:34:05Z colin $
 * @since 2.1.0
 */
@ClassVersion("$Id: CommodityUnderlyingAssetType.java 16154 2012-07-14 16:34:05Z colin $")
public enum CommodityUnderlyingAssetType
        implements HasCFICode, FutureUnderlyingAssetType
{
    /**
     * agricultural underlying asset type
     */
    AGRICULTURAL('A'),
    /**
     * extraction underlying asset type
     */
    EXTRACTION('E'),
    /**
     * industrial underlying asset type
     */
    INDUSTRIAL('I'),
    /**
     * service underlying asset type
     */
    SERVICE('S');
    /**
     * Get the cfiCode value.
     *
     * @return a <code>char</code> value
     */
    public char getCfiCode()
    {
        return cfiCode;
    }
    /**
     * Create a new CommodityUnderlyingAssetType instance.
     *
     * @param inCfiCode
     */
    private CommodityUnderlyingAssetType(char inCfiCode)
    {
        cfiCode = inCfiCode;
    }
    /**
     * the cfi code of the commodity underlying asset type 
     */
    private final char cfiCode;
}
