namespace NET.model
{
    public interface IIdentifiable<TID>
    {
        TID getID();
        void setID(TID ID);
    }
}