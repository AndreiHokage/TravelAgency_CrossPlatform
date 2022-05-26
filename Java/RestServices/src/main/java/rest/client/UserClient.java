package rest.client;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import rest.ServiceException;
import travel.model.Flight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

public class UserClient {

    public static final String URL = "http://localhost:8080/travel/flights";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable){
        try{
            return callable.call();
        }catch (ResourceAccessException | HttpClientErrorException e){
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Flight createFlight(Flight flight){
        return execute(() -> restTemplate.postForObject(URL, flight, Flight.class));
    }

    public void deleteFlight(Integer id){
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id.toString()));
            return null;
        });
    }

    public void update(Flight flight){
        execute(() -> {
           restTemplate.put(String.format("%s/%s", URL, flight.getID().toString()), flight);
           return null;
        });
    }

    public Flight flightById(Integer id){
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id.toString()), Flight.class));
    }

    public Flight[] findAll(){
        return execute(() -> restTemplate.getForObject(URL, Flight[].class));
    }
}
