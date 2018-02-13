using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PicPayTest
{
    public class ApiQueryMaker
    {
        private const string ServerUrl = "http://careers.picpay.com/tests/mobdev";
        private readonly IDictionary<string,string > _parameters;
        private QueryType Type { get; }

        public ApiQueryMaker(QueryType type)
        {
            Type = type;
            _parameters = new Dictionary<string, string>();
        }

        public void AddParameter(string name, object value)
        {
            _parameters.Add(name, value.ToString());
        }

        public string GetRawUrl()
        {
            return ServerUrl + QueryUrl.Get(Type);
        }

        public string GetPostContent()
        {
            var json = JsonConvert.SerializeObject(_parameters);
            return json;
        }

        public string GetUrl()
        {
            try
            {
                return ServerUrl + _parameters.Aggregate(QueryUrl.Get(Type),
                           (current, entry) => current.Replace("[" + entry.Key + "]", entry.Value));
            }
            catch (Exception)
            {
                /* bad url */
                throw new Exception("Incorrect Url");
            }
        }
    }
}
