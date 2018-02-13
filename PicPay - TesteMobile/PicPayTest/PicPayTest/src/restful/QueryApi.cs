using System;
using System.Collections.Generic;
using System.Text;

namespace PicPayTest
{
    
    public enum QueryType
    {
        Users,
        Transaction
    }

    public static class QueryUrl
    {
        private static readonly IDictionary<int, string> GetUrl = new Dictionary<int, string>
        {
            {(int) QueryType.Users, "/users"},
            {(int) QueryType.Transaction, "/transaction"},
        };

        public static string Get(QueryType key)
        {
            return GetUrl[(int)key];
        }
    }
    
}
