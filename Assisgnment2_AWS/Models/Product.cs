using Amazon.DynamoDBv2.DataModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Assisgnment2_AWS.Models
{
    [DynamoDBTable("Product_TruongQuocLap")]
    public class Product
    {
        [DynamoDBHashKey("productId")]
        public string id { get; set; }
        [DynamoDBRangeKey("productName")]
        public string name { get; set; }

    }
}
