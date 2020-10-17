# Simple netty client - example

Here is an example of a simple netty client, based on a framework I've written (https://github.com/Marcoral/SimpleNettyClient).
This minimalistic example illustrates how to connect to the server and communicate with it using protocol defined by SimpleNettyServer-example (available at: https://github.com/Marcoral/SimpleNettyServer-example).

## Launching

To connect to the server, run the program with the following arguments:

**1st:** Address ("localhost" if none specified)
**2nd:** Port (45256 if none specified)

For quick start, you can just run the compiled .jar file attached to the repository with the following command:

    java -jar SimpleNettyClient-example localhost 45256

(in the above command I called the default arguments explicitly).

## Controlling
When the client connects to the server, it can be controlled via command line. The following commands are available:

**ping** - Sends *ping packet* to the server. When it receives a reply, it displays a message.
**login** - After entering the command, the terminal expects a login. Then it sends a *login packet* to the server. Depending on the server's response, an appropriate message will appear on the terminal.
**data** - Sends a *simple data packet* to the server
**exit** - Disconnects from the server and terminates the client's operation
