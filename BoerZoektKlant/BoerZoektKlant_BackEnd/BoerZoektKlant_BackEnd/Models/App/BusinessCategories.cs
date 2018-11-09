using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace BoerZoektKlant_BackEnd.Models.App
{
    public class BusinessCategories
    {
        [Key]
        public int BusinessId { get; set; }
        [ForeignKey("BusinessId")]
        public Business Businesses { get; set; }
        [Key]
        public int CategoryId { get; set; }
        [ForeignKey("CategoryId")]
        public Category Categories { get; set; }


    }
}
