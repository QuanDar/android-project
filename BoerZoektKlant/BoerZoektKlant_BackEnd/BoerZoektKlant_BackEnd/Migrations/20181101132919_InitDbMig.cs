using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace BoerZoektKlant_BackEnd.Migrations
{
    public partial class InitDbMig : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Businesses",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Title = table.Column<string>(nullable: true),
                    Description = table.Column<string>(nullable: true),
                    Excerpt = table.Column<string>(nullable: true),
                    ImageUrl = table.Column<string>(nullable: true),
                    Rating = table.Column<int>(nullable: false),
                    PhoneNumber = table.Column<string>(nullable: true),
                    PostalCode = table.Column<string>(nullable: true),
                    Address = table.Column<string>(nullable: true),
                    HouseNumber = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Businesses", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Categories",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Name = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Categories", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Prices",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Name = table.Column<string>(nullable: true),
                    Price = table.Column<double>(nullable: false),
                    BusinessId = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Prices", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Prices_Businesses_BusinessId",
                        column: x => x.BusinessId,
                        principalTable: "Businesses",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "BusinessCategorieses",
                columns: table => new
                {
                    BusinessId = table.Column<int>(nullable: false),
                    CategoryId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_BusinessCategorieses", x => new { x.BusinessId, x.CategoryId });
                    table.ForeignKey(
                        name: "FK_BusinessCategorieses_Businesses_BusinessId",
                        column: x => x.BusinessId,
                        principalTable: "Businesses",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_BusinessCategorieses_Categories_CategoryId",
                        column: x => x.CategoryId,
                        principalTable: "Categories",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.InsertData(
                table: "Businesses",
                columns: new[] { "Id", "Address", "Description", "Excerpt", "HouseNumber", "ImageUrl", "PhoneNumber", "PostalCode", "Rating", "Title" },
                values: new object[] { 1, "Hasselt, Elfde-Straat", "Description asdas dasfads fwsewgf sergdrfgh rdeghdsgf gsrdfg erdhgert hg", "This is an excerpt", "23", null, "06 33332123", "2221EQ", 4, "Title 1 object" });

            migrationBuilder.InsertData(
                table: "Businesses",
                columns: new[] { "Id", "Address", "Description", "Excerpt", "HouseNumber", "ImageUrl", "PhoneNumber", "PostalCode", "Rating", "Title" },
                values: new object[] { 2, "Hasselt, Elfde-Straat", "Description asdas dasfads fwsewgf sergdrfgh rdegh", "This is an excerpt", "24", null, "06 33332123", "2221EQ", 3, "Title 2 object" });

            migrationBuilder.InsertData(
                table: "Businesses",
                columns: new[] { "Id", "Address", "Description", "Excerpt", "HouseNumber", "ImageUrl", "PhoneNumber", "PostalCode", "Rating", "Title" },
                values: new object[] { 3, "Nog een andere straat", "asdasfg sdgfSDF asdasswwd sakopdasopjf jadsiofgn aosdeinionfgi sndeiognsod;oigfnssoingrn", "This is an excerpt", "23", null, "06 33332123", "2221EQ", 3, "Title 3 object" });

            migrationBuilder.CreateIndex(
                name: "IX_BusinessCategorieses_CategoryId",
                table: "BusinessCategorieses",
                column: "CategoryId");

            migrationBuilder.CreateIndex(
                name: "IX_Prices_BusinessId",
                table: "Prices",
                column: "BusinessId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "BusinessCategorieses");

            migrationBuilder.DropTable(
                name: "Prices");

            migrationBuilder.DropTable(
                name: "Categories");

            migrationBuilder.DropTable(
                name: "Businesses");
        }
    }
}
