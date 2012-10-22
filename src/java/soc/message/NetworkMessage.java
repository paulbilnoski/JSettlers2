package soc.message;

/**
 * Base type for message traffic sent between the client and server. Particular subclasses are
 * instantiated by the client and server by a {@link MessageFactory} when they receive the serialized
 * form of a message, and execute the message given a context. Some messages may have the same behavior
 * on both client and server, and some may not have behavior for one or the other. Particularly for the
 * client, the message will send an event notification that updates the user interface where typically
 * server messages do not do this. Also, server messages maintain the entire state of the game, while
 * client messages may have limited information.
 * 
 * @author paul.bilnoski
 * @since 2.0.00
 */
public interface NetworkMessage
{
    void execute(ClientMessageContext context) throws MessageException;
//    void execute(ServerMessageContext context) throws MessageException;
}
