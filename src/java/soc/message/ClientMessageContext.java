package soc.message;

import soc.client.SOCPlayerClient;

/**
 * @author paul.bilnoski
 * @since 2.0.00
 */
public interface ClientMessageContext
{
    boolean isPractice();
    SOCPlayerClient getClient();
}
