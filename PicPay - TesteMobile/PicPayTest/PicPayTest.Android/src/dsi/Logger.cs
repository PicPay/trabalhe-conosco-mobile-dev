using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using PicPayTest.Droid;
using Xamarin.Forms;

[assembly: Dependency(typeof(Logger))]
namespace PicPayTest.Droid
{
    internal class Logger : ILog
    {
        public void Log(string output)
        {
            Console.WriteLine(output);
        }
    }
}