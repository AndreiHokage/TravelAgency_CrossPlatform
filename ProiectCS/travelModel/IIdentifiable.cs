namespace travelModel
{
    public interface IIdentifiable<TID>
    {
        TID ID { set; get; }
    }
}