import chat.network.TravelServicesRpcProxy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import travel.client.LogInController;
import travel.client.TravelController;
import travel.services.ITravelServices;

import java.io.IOException;
import java.util.Properties;

public class StartRpcClientFX extends Application {
    private Stage primaryStage;

    private static int defaultTravelPart = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/travelclient.properties"));
            System.out.println("Client properties set.");
            clientProps.list(System.out);
        }catch (IOException e){
            System.err.println("Cannot find travelclient.properties " + e);
            return;
        }

        String serverIP = clientProps.getProperty("travel.server.host", defaultServer);
        int serverPort = defaultTravelPart;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("travel.server.port"));
        }catch (NumberFormatException ex){
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultTravelPart);
        }

        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        ITravelServices server = new TravelServicesRpcProxy(serverIP, serverPort);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("views/login.fxml"));
        Parent root = fxmlLoader.load();


        LogInController logInController = fxmlLoader.getController();
        logInController.setServer(server);

        FXMLLoader cloader = new FXMLLoader(getClass().getClassLoader().getResource("views/travel.fxml"));
        Parent croot = cloader.load();
        TravelController travelController = cloader.<TravelController>getController();


        logInController.setTravelController(travelController);
        logInController.setMainTravelParent(croot);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
