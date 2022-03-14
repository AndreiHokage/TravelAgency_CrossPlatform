using System;
using System.Collections.Generic;
using System.Configuration;
using NET.model;
using NET.repository;

namespace ProiectCS
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            IDictionary<string, string> props = new SortedList<string, string>();
            props.Add("ConnectionString",GetConnectionStringByName("travelDB"));
 
            FlightRepository flightRepository = new FlightDBRepository(props);
            TicketRepository ticketRepository = new TicketDBRepository(props);
            Flight flight = new Flight("Roma", DateTime.Now, "Traian Vuia", 25);
            //flightRepository.Save(flight);
            Flight flightUpdate = new Flight("Roma", DateTime.Now, "Cluj Napoca International", 30);
            //flightRepository.Update(flightUpdate,4);

        
            Ticket ticket = new Ticket("Barbuta", "Cristi;Andrei;Laur;Mihhaela", "Bacau", 5, flight);
    
         

            /*Flight flightDublin = flightRepository.FindById(1);
            Ticket ticketDublin = new Ticket("Razvan", "Octav;Dochita", "Suceava", 3, flightDublin);
            ticketRepository.Save(ticketDublin);*/
  
            foreach (var objG in flightRepository.FindAll())
            {
                Console.WriteLine(objG);
            }
    
            Console.WriteLine(ticketRepository.FindById(5));
            foreach (var objG in ticketRepository.FindAll())
            {
                Console.WriteLine(objG);
            }
        }
        

        static string GetConnectionStringByName(String name)
        {
            string returnValue = null;
            
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }
}