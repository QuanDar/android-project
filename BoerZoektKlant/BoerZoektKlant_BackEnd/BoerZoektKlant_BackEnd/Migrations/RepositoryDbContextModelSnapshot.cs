﻿// <auto-generated />
using System;
using BoerZoektKlant_BackEnd.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace BoerZoektKlant_BackEnd.Migrations
{
    [DbContext(typeof(RepositoryDbContext))]
    partial class RepositoryDbContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.1.4-rtm-31024")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("BoerZoektKlant_BackEnd.Models.App.Business", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Address");

                    b.Property<string>("Description");

                    b.Property<string>("Excerpt");

                    b.Property<string>("HouseNumber");

                    b.Property<string>("ImageUrl");

                    b.Property<string>("PhoneNumber");

                    b.Property<string>("PostalCode");

                    b.Property<int>("Rating");

                    b.Property<string>("Title");

                    b.HasKey("Id");

                    b.ToTable("Businesses");

                    b.HasData(
                        new { Id = 1, Address = "Hasselt, Elfde-Straat", Description = "Description asdas dasfads fwsewgf sergdrfgh rdeghdsgf gsrdfg erdhgert hg", Excerpt = "This is an excerpt", HouseNumber = "23", PhoneNumber = "06 33332123", PostalCode = "2221EQ", Rating = 4, Title = "Title 1 object" },
                        new { Id = 2, Address = "Hasselt, Elfde-Straat", Description = "Description asdas dasfads fwsewgf sergdrfgh rdegh", Excerpt = "This is an excerpt", HouseNumber = "24", PhoneNumber = "06 33332123", PostalCode = "2221EQ", Rating = 3, Title = "Title 2 object" },
                        new { Id = 3, Address = "Nog een andere straat", Description = "asdasfg sdgfSDF asdasswwd sakopdasopjf jadsiofgn aosdeinionfgi sndeiognsod;oigfnssoingrn", Excerpt = "This is an excerpt", HouseNumber = "23", PhoneNumber = "06 33332123", PostalCode = "2221EQ", Rating = 3, Title = "Title 3 object" }
                    );
                });

            modelBuilder.Entity("BoerZoektKlant_BackEnd.Models.App.BusinessCategories", b =>
                {
                    b.Property<int>("BusinessId");

                    b.Property<int>("CategoryId");

                    b.HasKey("BusinessId", "CategoryId");

                    b.HasIndex("CategoryId");

                    b.ToTable("BusinessCategorieses");
                });

            modelBuilder.Entity("BoerZoektKlant_BackEnd.Models.App.Category", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Name");

                    b.HasKey("Id");

                    b.ToTable("Categories");
                });

            modelBuilder.Entity("BoerZoektKlant_BackEnd.Models.App.Prices", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<int?>("BusinessId");

                    b.Property<string>("Name");

                    b.Property<double>("Price");

                    b.HasKey("Id");

                    b.HasIndex("BusinessId");

                    b.ToTable("Prices");
                });

            modelBuilder.Entity("BoerZoektKlant_BackEnd.Models.App.BusinessCategories", b =>
                {
                    b.HasOne("BoerZoektKlant_BackEnd.Models.App.Business", "Businesses")
                        .WithMany("BusinessCategories")
                        .HasForeignKey("BusinessId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("BoerZoektKlant_BackEnd.Models.App.Category", "Categories")
                        .WithMany("BusinessCategories")
                        .HasForeignKey("CategoryId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("BoerZoektKlant_BackEnd.Models.App.Prices", b =>
                {
                    b.HasOne("BoerZoektKlant_BackEnd.Models.App.Business")
                        .WithMany("Prices")
                        .HasForeignKey("BusinessId");
                });
#pragma warning restore 612, 618
        }
    }
}