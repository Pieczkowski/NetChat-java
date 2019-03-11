# NetChat-java
Console application which allows to communicate with other users in LAN. It is my first contact with java sockets and low-level APIs dealing with synchronization of threads.

## Usage
Application has two modes server and client, the former of which has to be launched before client.

To run application use the following: 
`java App server/client port (address)`

example: `java App client 8001 127.0.0.1`

The address is optional. Without that parameter application uses your loop back address:

```java
  private static InetAddress address = InetAddress.getLoopbackAddress();
```

After connecting to the server user is asked to register by stating his/hers name. It has to be unique, sorry!

The next step is choosing the receiver of your message and off you go!

There are some special commands that help you use tha chat:

**/set** - used to change receiver of ur messages

**/clients** - used to get list of registered users

 
## Requirements:

- [x] Command signature should look in a following way: java App mode port [address]
- [x] Application should have to modes: client, server.
- [x] Application should allow bi-directional communication between server and client.
- [x] Application should use TCP protocol.
- [x] Communication should be done through exchanging *Message* objects not Strings.
- [x] Application should use serialization
- [ ] When a client disconnects from a server, server should wait for the next client.
- [ ] When a server disconnects, client should be turned off gracefully.

## (Possibly) Future tasks:

- Implement disconnecting
- Write tests
- Write javadocs

