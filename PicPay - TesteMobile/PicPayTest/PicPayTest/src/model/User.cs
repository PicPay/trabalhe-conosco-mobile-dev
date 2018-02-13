
using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms.Internals;

namespace PicPayTest.src.model
{
    [Preserve(AllMembers = true)]
    public class User
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Img { get; set; }
        public string Username { get; set; }

        public User() { }
    }
}
