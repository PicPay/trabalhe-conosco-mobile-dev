using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using PicPayTest.src.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PicPayTest.src.json
{
    public static class UserJson
    {
        public static List<User> DeserializeList(string json)
        {
            List<User> users = JsonConvert.DeserializeObject<List<User>>(json);

            return users;
        }
    }

}
