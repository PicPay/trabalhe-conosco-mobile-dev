using Realms;
using System;
using System.Collections.Generic;
using System.Text;

namespace PicPayTest.src.model
{
    class Card : RealmObject
    {
        public string CardNumber { get; set; }
        public string Detail { get; set; }
        public int CVV { get; set; }
        public string ExpiryDate { get; set; }

        public Card() { }
    }
}
