namespace NET.model.validators
{
    public interface Validator<E>
    {
        void validate(E item);
    }
}