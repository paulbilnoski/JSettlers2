package soc.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * A {@link MessageFactory} implementation that uses the "classic SOC" serialized format of strings
 * with separators.
 */
public final class SOCClassicMessageFactory implements MessageFactory
{
    /**
     * Knows how to build a particular type of message given the serialized form of it.
     */
    interface MessageBuilder
    {
        /**
         * Build the message for this builder.
         * @param msg The serialized form of a message.
         * @param multiMsg If the message is a multi-part message, the parts of the message.
         * @return The message built
         * @throws MessageException If the argument is not properly formed
         *                          for this builder, or there is an error in message construction.
         */
        NetworkMessage build(String data, List<String> multiMsg) throws MessageException;
    }
    
    private final Map<Integer, MessageBuilder> builders;
    
    public SOCClassicMessageFactory()
    {
        builders = new HashMap<Integer, MessageBuilder>();
        populateBuilders();
    }

    public NetworkMessage createMessage(String messageStr) throws MessageException
    {
        StringTokenizer st = new StringTokenizer(messageStr, SOCMessage.sep);

        // get the id that identifies the type of message
        Integer msgId = Integer.valueOf(st.nextToken());

        // get the rest of the data
        String data = null;

        /**
         * to handle {@link SOCMessageMulti} subclasses -
         * multiple parameters with sub-fields.
         * If only one param is seen, this will be null;
         * use {@link #toSingleElemArray(String)} to build it.
         *<P>
         * Note that if you passed a non-null gamename to the
         * {@link SOCMessageTemplateMs} or {@link SOCMessageTemplateMi} constructor,
         * then multiData[0] here will be gamename,
         * and multiData[1] == param[0] as passed to that constructor.
         *<code>
         *     case POTENTIALSETTLEMENTS:
         *         if (multiData == null)
         *             multiData = toSingleElemArray(data);
         *         return SOCPotentialSettlements.parseDataStr(multiData);
         *</code>
         */
        List<String> multiData = null;

        try
        {
            data = st.nextToken();
            while (st.hasMoreTokens())
            {
                // test for a null list instead of testing for hasMoreTokens outside and inside the loop
                if (multiData == null)
                    multiData = new ArrayList<String>();
                
                multiData.add(st.nextToken());
            }
        }
        catch (NoSuchElementException e)
        {
            data = "";
        }
        
        MessageBuilder builder = builders.get(msgId);
        if (builder == null)
            throw new MessageException("No builder found for message id ["+ msgId+ "] message: ["+messageStr+"]");
        
        return builder.build(data, multiData);
    }
    
    // called from the constructor, so should not be overridden by subclasses
    private final void populateBuilders()
    {
        /*
         * These builders just forward parsing to the existing functionality in each message until all the
         * parsing and reconstruction functionality is moved to this factory
         */
        builders.put(Integer.valueOf(SOCMessage.VERSION), new MessageBuilder() {
            public NetworkMessage build(String data, List<String> multiMsg) {
                return SOCVersion.parseDataStr(data);
            }
        });
    }
}
