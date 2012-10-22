package soc.message;

/**
 * A bridge between the network layer and {@link NetworkMessage} objects, which each know how to perform
 * some change on the client or server. A MessageFactory allows encapsulation of serialization and
 * deserialization of messages so multiple representations may be allowed.
 * <p>This will allow not only
 * the "classic" serialized form, but also XML, JSON, binary, or other formats, provided the server
 * and client support the format.
 * <p>It is also possible for the server to use a different MessageFactory
 * for individual clients connected, so the clients may be a variety of implementations connected
 * to the same game.
 * 
 * @author paul.bilnoski
 * @since 2.0.00
 */
public interface MessageFactory
{
    /**
     * Creates a new {@link NetworkMessage} from the serialized form of the message.
     * 
     * @param messageStr The serialized form of a message to be reconstructed by this factory.
     * @return A message resolved from the provided string. Does not return {@code null}
     * @throws MessageException If a message could not be constructed from the given string.
     */
    NetworkMessage createMessage(String messageStr) throws MessageException;
}
