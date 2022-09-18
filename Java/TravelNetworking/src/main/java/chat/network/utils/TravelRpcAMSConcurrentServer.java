package chat.network.utils;

import chat.network.ams.TravelClientRpcASMReflectionWorker;
import travel.services.ITravelASMServices;

import java.net.Socket;

public class TravelRpcAMSConcurrentServer extends AbsConcurrentServer{

    private ITravelASMServices travelServer;

    public TravelRpcAMSConcurrentServer(int port, ITravelASMServices travelServer){
        super(port);
        this.travelServer = travelServer;
        System.out.println("Travel- TravelRpcASMConcurrentServer port " + port);
    }

    @Override
    protected Thread createWorker(Socket client) {
        TravelClientRpcASMReflectionWorker worker = new TravelClientRpcASMReflectionWorker(travelServer, client);

        Thread tw = new Thread(worker);
        return tw;
    }
}
