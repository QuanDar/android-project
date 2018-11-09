using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BoerZoektKlant_BackEnd.Models.App;
using Microsoft.EntityFrameworkCore;

namespace BoerZoektKlant_BackEnd.Models
{
    public class RepositoryDbContext : DbContext
    {
        public RepositoryDbContext()
        {
        }

        public RepositoryDbContext(DbContextOptions<RepositoryDbContext> options) : base(options)
        {

        }
        public DbSet<Business> Businesses { get; set; }
        public DbSet<Category> Categories { get; set; }
        public DbSet<Prices> Prices { get; set; }
        public DbSet<BusinessCategories> BusinessCategorieses { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<BusinessCategories>().HasKey(
                table => new { table.BusinessId, table.CategoryId });

            modelBuilder.Entity<BusinessCategories>()
                .HasOne(bc => bc.Businesses)
                .WithMany(b => b.BusinessCategories)
                .HasForeignKey(bc => bc.BusinessId);

            modelBuilder.Entity<BusinessCategories>()
                .HasOne(bc => bc.Categories)
                .WithMany(b => b.BusinessCategories)
                .HasForeignKey(bc => bc.CategoryId);


            modelBuilder.Entity<Business>().HasData(
                new Business
                {
                    Id = 1,
                    Description = "Description asdas dasfads fwsewgf sergdrfgh rdeghdsgf gsrdfg erdhgert hg",
                    Rating = 4,
                    Title = "Title 1 object",
                    Excerpt = "This is an excerpt",
                    PhoneNumber = "06 33332123",
                    PostalCode = "2221EQ",
                    Address = "Hasselt, Elfde-Straat",
                    HouseNumber = "23"
                },
                new Business
                {
                    Id = 2,
                    Description = "Description asdas dasfads fwsewgf sergdrfgh rdegh",
                    Rating = 3,
                    Title = "Title 2 object",
                    Excerpt = "This is an excerpt",
                    PhoneNumber = "06 33332123",
                    PostalCode = "2221EQ",
                    Address = "Hasselt, Elfde-Straat",
                    HouseNumber = "24"
                },
                new Business
                {
                    Id = 3,
                    Description = "asdasfg sdgfSDF asdasswwd sakopdasopjf jadsiofgn aosdeinionfgi sndeiognsod;oigfnssoingrn",
                    Rating = 3,
                    Title = "Title 3 object",
                    Excerpt = "This is an excerpt",
                    PhoneNumber = "06 33332123",
                    PostalCode = "2221EQ",
                    Address = "Nog een andere straat",
                    HouseNumber = "23"
                }
                );

        }

    }
}
