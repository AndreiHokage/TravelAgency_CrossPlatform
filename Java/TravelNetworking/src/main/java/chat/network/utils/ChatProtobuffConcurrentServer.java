package chat.network.utils;

import chat.network.TravelClientRpcReflectionWorker;
import travel.services.ITravelServices;

import java.net.Socket;

public class ChatProtobuffConcurrentServer extends AbsConcurrentServer{
    private ITravelServices chatServer;
    public ChatProtobuffConcurrentServer(int port, ITravelServices chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatProtobuffConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        TravelClientRpcReflectionWorker worker=new TravelClientRpcReflectionWorker(chatServer,client);
        Thread tw=new Thread(worker);
        return tw;
    }
}
