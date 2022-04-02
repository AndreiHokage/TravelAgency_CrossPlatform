using System;

namespace NET.model.validators
{
    public class ValidationException : Exception
    {
        public ValidationException(String msg) : base(msg)
        {
            
        }
    }
}