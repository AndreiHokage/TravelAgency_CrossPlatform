import chat.network.utils.AbstractServer;
import chat.network.utils.ServerException;
import chat.network.utils.TravelRpcAMSConcurrentServer;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class StartASMRpcServer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-server.xml");
        AbstractServer server = context.getBean("travelTCPServer", TravelRpcAMSConcurrentServer.class);
        try{
            server.start();
        }catch (ServerException e) {
            e.printStackTrace();
        }
    }
}
