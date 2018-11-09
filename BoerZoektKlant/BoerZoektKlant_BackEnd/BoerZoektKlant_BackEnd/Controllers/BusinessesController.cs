using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BoerZoektKlant_BackEnd.Models;
using BoerZoektKlant_BackEnd.Models.App;
using BoerZoektKlant_BackEnd.Models.ViewModel;

namespace BoerZoektKlant_BackEnd.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BusinessesController : Controller
    {
        private readonly RepositoryDbContext _context;

        public BusinessesController(RepositoryDbContext context)
        {
            _context = context;
        }

        // GET: api/Businesses
        [HttpGet]
        public async Task<IActionResult> GetBusinesses()
        {
            var viewModel = from b in _context.Businesses
                            select new BusinessCategoriesViewModel
                            {
                                Id = b.Id,
                                Address = b.Address,
                                Description = b.Description,
                                Excerpt = b.Excerpt,
                                HouseNumber = b.HouseNumber,
                                ImageUrl = b.ImageUrl,
                                PostalCode = b.PostalCode,
                                Rating = b.Rating,
                                Title = b.Title,
                                PhoneNumber = b.PhoneNumber,
                                Categories = (from c in _context.Categories
                                              join bc in _context.BusinessCategorieses on c.Id equals bc.CategoryId
                                              where bc.BusinessId == b.Id
                                              select new Category
                                              {
                                                  Id = c.Id,
                                                  Name = c.Name
                                              }).ToList()
                            };

            return Ok(viewModel);
        }

        // GET: api/Businesses/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetBusiness([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var business = await _context.Businesses.FindAsync(id);

            if (business == null)
            {
                return NotFound();
            }

            return Ok(business);
        }

        // PUT: api/Businesses/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutBusiness([FromRoute] int id, [FromBody] Business business)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != business.Id)
            {
                return BadRequest();
            }

            _context.Entry(business).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!BusinessExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Businesses
        [HttpPost]
        public async Task<IActionResult> PostBusiness([FromBody] Business business)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Businesses.Add(business);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetBusiness", new { id = business.Id }, business);
        }

        // DELETE: api/Businesses/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteBusiness([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var business = await _context.Businesses.FindAsync(id);
            if (business == null)
            {
                return NotFound();
            }

            _context.Businesses.Remove(business);
            await _context.SaveChangesAsync();

            return Ok(business);
        }

        private bool BusinessExists(int id)
        {
            return _context.Businesses.Any(e => e.Id == id);
        }
    }
}