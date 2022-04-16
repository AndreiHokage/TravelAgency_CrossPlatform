using System.Collections.Generic;
using travelModel;

namespace travelPersistence
{
    public interface IRepository<T, TID> where T : IIdentifiable<TID>
    {
        void Save(T elem);

        void Delete(T elem);

        void Update(T elem, TID ID);

        T FindById(TID ID);

        IEnumerable<T> FindAll();
        
    }
}