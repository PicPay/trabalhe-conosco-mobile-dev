using PicPayTest.src.model;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace PicPayTest.src.restful
{
    class TransactionRequester
    {
        public static async Task<bool> PostTransaction(Transaction transaction)
        {
            var queryMaker = new ApiQueryMaker(QueryType.Transaction);

            queryMaker.AddParameter("card_number", transaction.CardNumber);
            queryMaker.AddParameter("cvv", transaction.CVV);
            queryMaker.AddParameter("value", transaction.Value);
            queryMaker.AddParameter("expiry_date", transaction.ExpiryDate);
            queryMaker.AddParameter("destination_user_id", transaction.DestinationUserId);



            var answer = await Rest.Send(queryMaker);

            if (answer.Status != Status.Ok)
                return false;        

            return true;
        }

    }
}
