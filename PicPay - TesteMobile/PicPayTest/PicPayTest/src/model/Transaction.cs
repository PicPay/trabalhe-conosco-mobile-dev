using System;
using System.Collections.Generic;
using System.Text;

namespace PicPayTest.src.model
{
    public class Transaction
    {
        public string CardNumber { get; set; }
        public int CVV { get; set; }
        public int Value { get; set; }
        public string ExpiryDate { get; set; }
        public int DestinationUserId { get; set; }
    }
}
