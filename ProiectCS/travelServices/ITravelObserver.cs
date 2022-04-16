using travelModel;

namespace travelServices
{

    public interface ITravelObserver
    {
        void soldTicket(Ticket ticket);
    }
}