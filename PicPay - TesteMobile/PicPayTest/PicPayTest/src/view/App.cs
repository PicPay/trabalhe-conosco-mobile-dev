using System;
using System.Collections.Generic;
using System.Text;
using Xamarin.Forms;

namespace PicPayTest
{
    public class App : Application
    {
        public App()
        {
            MainPage = new NavigationPage(new MainPage());            
        }
    }
}

