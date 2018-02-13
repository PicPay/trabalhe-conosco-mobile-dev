using System;
using System.Collections.Generic;
using System.Text;

namespace PicPayTest
{
    public interface IConnectionState
    {
        bool IsOnline();
        bool IsOnWifi();
        bool IsOnRoaming();
    }
}
