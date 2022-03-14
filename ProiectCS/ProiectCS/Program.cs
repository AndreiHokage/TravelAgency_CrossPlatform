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
            Flight flight = new Flight("Warsaw", DateTime.Now, "Cluj Napoca International", 45);
            //flightRepository.Save(flight);
            //Flight flightUpdate = new Flight("Poland", DateTime.Now, "Cluj Napoca International", 90);
            //flightRepository.Update(flightUpdate,3);

            //flight.ID = 3;
            //Ticket ticket = new Ticket("Andrei", "Cristi;Andrei;Laur", "Neamt", 4, flight);
            //ticketRepository.Save(ticket);

            /*Flight flightDublin = flightRepository.FindById(1);
            Ticket ticketDublin = new Ticket("Razvan", "Octav;Dochita", "Suceava", 3, flightDublin);
            ticketRepository.Save(ticketDublin);*/
  
            foreach (var objG in flightRepository.FindAll())
            {
                Console.WriteLine(objG);
            }
    
            Console.WriteLine(ticketRepository.FindById(4));
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