
using ModernHttpClient;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace PicPayTest
{
    public enum Status
    {
        Error,
        Ok,
        Timeout,
        NoConnection
    }

    public struct RestAnswer
    {
        public Status Status { get; set; }
        public string Result { get; set; }
    }
    
    public static class Rest
    {
            private static readonly HttpClient Client =
                   new HttpClient(new NativeMessageHandler()) { Timeout = TimeSpan.FromSeconds(5) };

            public static async Task<RestAnswer> Request(ApiQueryMaker apiMaker)
            {
                if (!DependencyService.Get<IConnectionState>().IsOnline())
                    return new RestAnswer { Status = Status.NoConnection };

                try
                {
                    return new RestAnswer { Status = Status.Ok, Result = await Client.GetStringAsync(apiMaker.GetUrl()) };
                }
                catch (Exception e)
                {
                    DependencyService.Get<ILog>().Log(e.StackTrace); // treate all erros type to show appropiate menssage
                    return new RestAnswer { Status = Status.Error };
                }
            }

            public static async Task<RestAnswer> Send(ApiQueryMaker apiMaker)
            {
                if (!DependencyService.Get<IConnectionState>().IsOnline())
                    return new RestAnswer { Status = Status.NoConnection };

                try
                {
                    var content = new StringContent(apiMaker.GetPostContent(), Encoding.UTF8, "application/json");
                    var response = await Client.PostAsync(apiMaker.GetRawUrl(), content);
                    return new RestAnswer { Status = Status.Ok, Result = await response.Content.ReadAsStringAsync() };
                }
                catch (Exception e)
                {
                    DependencyService.Get<ILog>().Log(e.StackTrace);
                    return new RestAnswer { Status = Status.Error };
                }
            }
    }   
    
}
