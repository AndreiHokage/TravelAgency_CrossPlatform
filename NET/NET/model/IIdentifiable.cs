namespace NET.model
{
    public interface IIdentifiable<TID>
    {
        TID ID { set; get; }
    }
}