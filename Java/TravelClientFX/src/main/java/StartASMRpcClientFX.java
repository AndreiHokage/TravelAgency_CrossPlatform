import ams.NotificationReceiverImpl;
import chat.network.ams.TravelServicesRpcASMProxy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import travel.client.LogInController;
import travel.client.TravelController;
import travel.services.INotificationReceiver;
import travel.services.ITravelASMServices;

public class StartASMRpcClientFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-client.xml");
        ITravelASMServices server = context.getBean("travelServices", TravelServicesRpcASMProxy.class);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("views/login.fxml"));
        Parent root = fxmlLoader.load();


        LogInController logInController = fxmlLoader.getController();
        logInController.setServer(server);

        FXMLLoader cloader = new FXMLLoader(getClass().getClassLoader().getResource("views/travel.fxml"));
        Parent croot = cloader.load();
        TravelController travelController = cloader.<TravelController>getController();
        INotificationReceiver receiver = context.getBean("notificationReceiver", NotificationReceiverImpl.class);
        travelController.setReceiver(receiver);


        logInController.setTravelController(travelController);
        logInController.setMainTravelParent(croot);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
