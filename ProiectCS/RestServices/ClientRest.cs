using System;
using System.Collections.ObjectModel;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading;
using System.Threading.Tasks;


namespace RestServices
{
    public class ClientRest
    {
        private static HttpClient client = new HttpClient();
        private static String URL = "http://localhost:8080/travel/flights";

        public static void Main(string[] args)
        {
            RunAsync();
        }

        static async Task RunAsync()
        {
            client.BaseAddress = new Uri(URL);
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            
            // Console.WriteLine("Create flight: -----------------------");
            // Flight createdFlight =
            //     new Flight("Warsaw", TimeZoneInfo.ConvertTimeToUtc(DateTime.Now), "Bucharest", 30);
            // Flight newFlight = CreateFlight(createdFlight).Result;
            // Console.WriteLine(newFlight);
            //
            //
            // Console.WriteLine("FindAll flights: ---------------------");
            // Flight[] flights = FinaAllFlights().Result;
            // foreach (var flight in flights)
            //     Console.WriteLine(flight);
            
            
             Flight createdFlight =
                new Flight(16,"Warsaw", TimeZoneInfo.ConvertTimeToUtc(DateTime.Now), "Bucharest", 30);
             Console.WriteLine("Update flight: -----------------------");
             Flight updateFlight =createdFlight;
             updateFlight.Airport = "Suceava Airport";
             UpdateFlight(updateFlight);
            
            
            Console.WriteLine("Find flight: -----------------------");
            Console.WriteLine(FindFlightById(createdFlight.ID).Result);
            

            Console.WriteLine("Delete flight: -----------------------");
            Console.WriteLine(DeleteFlight(18).Result);
            Console.WriteLine(DeleteFlight(18).Result);
        }

        static async Task<Flight> CreateFlight(Flight flight)
        {
            Flight newFlight = null;

            string jsonStr = JsonSerializer.Serialize(flight);
            HttpContent c = new StringContent(jsonStr, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await client.PostAsync(URL, c);
            if (response.IsSuccessStatusCode)
            {
                newFlight = await response.Content.ReadAsAsync<Flight>();
            }
            return newFlight;
        }

        static async void UpdateFlight(Flight flight)
        {
            string jsonStr = JsonSerializer.Serialize(flight);
            HttpContent c = new StringContent(jsonStr, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await client.PutAsync(URL + '/' + flight.ID.ToString(), c);
            
        }

        static async Task<String> DeleteFlight(int id)
        {
            HttpResponseMessage response = await client.DeleteAsync(URL + '/' + id.ToString());
            String message = await response.Content.ReadAsStringAsync();
            return message;
        }
        
        static async Task<Flight> FindFlightById(int id)
        {
            Flight flight = null;
            HttpResponseMessage response = await client.GetAsync(URL + '/' + id.ToString());
            if (response.IsSuccessStatusCode)
            {
                flight = await response.Content.ReadAsAsync<Flight>();
            }
            return flight;
        }

        static async Task<Flight[]> FinaAllFlights()
        {
            Flight[] flights = null;
            HttpResponseMessage response = await client.GetAsync(URL);
            if (response.IsSuccessStatusCode)
            {
                flights = await response.Content.ReadAsAsync<Flight[]>();
            }

            return flights;
        }
    }

 
    public class Flight
    {
        [JsonPropertyName("id")]
        public int ID { get; set; }
        
        [JsonPropertyName("destination")]
        public string Destination { set; get; }

        [JsonPropertyName("departure")]
        public DateTime Departure { set; get; }
        
        [JsonPropertyName("airport")]
        public string Airport { set; get; }
        
        [JsonPropertyName("availableSeats")]
        public int AvailableSeats { set; get; }

        public Flight()
        {
        }

        public Flight(int ID, string destination, DateTime departure, string airport, int availableSeats)
        {
            this.ID = ID;
            this.Destination = destination;
            this.Departure = departure;
            this.Airport = airport;
            this.AvailableSeats = availableSeats;
        }

        public Flight(string destination, DateTime departure, string airport, int availableSeats)
        {
            this.Destination = destination;
            this.Departure = departure;
            this.Airport = airport;
            this.AvailableSeats = availableSeats;
        }

        public override string ToString()
        {
            return ID + ";" + Destination + ";" + Departure + ";" + Airport + ";" + AvailableSeats;
        }

        public override bool Equals(object obj)
        {
            Flight flight = obj as Flight;
            if (obj == null)
                return false;
            return ID == flight.ID;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

    }
}