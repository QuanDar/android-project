using BoerZoektKlant_BackEnd.Models.App;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BoerZoektKlant_BackEnd.Models.ViewModel
{
    public class BusinessCategoriesViewModel
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string Excerpt { get; set; }
        public string ImageUrl { get; set; }
        public int Rating { get; set; }
        public string PhoneNumber { get; set; }
        public string PostalCode { get; set; }
        public string Address { get; set; }
        public string HouseNumber { get; set; }
        public List<Prices> Prices { get; set; }
        public List<Category> Categories { get; set; }
    }
}
