using System;

namespace ProiectCS
{
    public enum TravelUserEvent
    {
        BuyTicket
    }
    
    public class TravelUserEventArgs : EventArgs
    {
        private readonly TravelUserEvent userEvent;
        private readonly Object data;

        public TravelUserEventArgs(TravelUserEvent userEvent, object data)
        {
            this.userEvent = userEvent;
            this.data = data;
        }

        public TravelUserEvent TravelEventType
        {
            get { return userEvent; }
        }

        public object Data
        {
            get { return data; }
        }
    }
}