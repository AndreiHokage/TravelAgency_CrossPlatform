using System;

namespace travelServices
{

    public class TravelException : Exception
    {
        public TravelException() : base()
        {
        }

        public TravelException(String msg) : base(msg)
        {
        }

        public TravelException(String msg, Exception ex) : base(msg, ex)
        {
        }
    }
}