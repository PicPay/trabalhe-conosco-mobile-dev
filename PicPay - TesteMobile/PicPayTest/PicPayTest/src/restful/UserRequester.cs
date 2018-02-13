using PicPayTest.src.json;
using PicPayTest.src.model;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace PicPayTest.src.restful
{
    public static class UserRequester
    {
        public static async Task<List<User>> GetUsers()
        {
            var queryMaker = new ApiQueryMaker(QueryType.Users);
            var answer = await Rest.Request(queryMaker);

            return answer.Status == Status.Ok ? UserJson.DeserializeList(answer.Result) : null;
        }

    }
}
