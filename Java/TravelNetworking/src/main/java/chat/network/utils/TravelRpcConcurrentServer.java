package chat.network.utils;

import chat.network.TravelClientRpcReflectionWorker;
import travel.services.ITravelServices;

import java.net.Socket;

public class TravelRpcConcurrentServer extends AbsConcurrentServer{

    private ITravelServices travelServices;

    public TravelRpcConcurrentServer(int port, ITravelServices travelServices) {
        super(port);
        this.travelServices = travelServices;
        System.out.println("Travel- TravelRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        TravelClientRpcReflectionWorker worker = new TravelClientRpcReflectionWorker(travelServices, client);
        Thread tw = new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
