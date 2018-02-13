using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.Net;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using PicPayTest.Droid;
using Xamarin.Forms;

[assembly: Dependency(typeof(ConnectionChecker))]
namespace PicPayTest.Droid
{
    internal class ConnectionChecker : IConnectionState
    {
        private NetworkInfo _activeConnection;
        private ConnectivityManager _connectivityManager;

        public bool IsOnline()
        {
            _connectivityManager =
                (ConnectivityManager)Android.App.Application.Context.GetSystemService(Context.ConnectivityService);
            _activeConnection = _connectivityManager.ActiveNetworkInfo;
            return _activeConnection != null && _activeConnection.IsConnected;
        }

        public bool IsOnRoaming()
        {
            throw new NotImplementedException();
        }

        public bool IsOnWifi()
        {
            throw new NotImplementedException();
        }
    }
}