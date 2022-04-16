namespace travelModel.validators
{
    public interface Validator<E>
    {
        void validate(E item);
    }
}