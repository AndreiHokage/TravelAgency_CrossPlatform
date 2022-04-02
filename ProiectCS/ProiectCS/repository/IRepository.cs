using System.Collections.Generic;
using NET.model;

namespace NET.repository
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