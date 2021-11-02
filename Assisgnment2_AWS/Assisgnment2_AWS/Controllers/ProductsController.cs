using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Assisgnment2_AWS.Models;
using Amazon.DynamoDBv2.DataModel;

namespace Assisgnment2_AWS.Controllers
{
    public class ProductsController : Controller
    {
        private readonly IDynamoDBContext _context;

        public ProductsController(IDynamoDBContext context)
        {
            _context = context;
        }

        // GET: Products
        public async Task<IActionResult> Index()
        {
            var docs = await _context.ScanAsync<Product>(null).GetRemainingAsync();
            return View(docs);
        }

        //GET: Products/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var products = await _context.QueryAsync<Product>(id).GetRemainingAsync();
            if (products.Count == 0)
            {
                return NotFound();
            }

            return View(products[0]);
        }

        //GET: Products/Create
        public IActionResult Create()
        {
            return View();
        }

        //POST: Products/Create
        //To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("id,name")] Product product)
        {
            if(product.id == null || product.name == null)
            {
                return RedirectToAction(nameof(Create));
            }
            if (ModelState.IsValid)
            {
                await _context.SaveAsync<Product>(product);
                return RedirectToAction(nameof(Index));
            }
            return View(product);
        }

        //GET: Products/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var products = await _context.QueryAsync<Product>(id).GetRemainingAsync();

            if (products.Count == 0)
            {
                return NotFound();
            }
            return View(products[0]);
        }

        //POST: Products/Edit/5
         //To protect from overposting attacks, enable the specific properties you want to bind to, for 
         //more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("id,name")] Product product)
        {
            if (id != product.id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    await _context.SaveAsync(product);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.InnerException);
                }
                return RedirectToAction(nameof(Index));
            }
            return View(product);
        }

        //GET: Products/Delete/5
        public async Task<IActionResult> Delete(string id)
        {

            if (id == null)
            {
                return NotFound();
            }

            var product = await _context.QueryAsync<Product>(id).GetRemainingAsync();
            if (product.Count == 0)
            {
                return NotFound();
            }
            return View(product[0]);
        }

        //POST: Products/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id, string name)
        {
            await _context.DeleteAsync<Product>(id, name);
            return RedirectToAction(nameof(Index));
        }

    }
}
