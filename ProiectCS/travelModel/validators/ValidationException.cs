using System;

namespace travelModel.validators
{
    public class ValidationException : Exception
    {
        public ValidationException(String msg) : base(msg)
        {
            
        }
    }
}