using PicPayTest.src.model;
using PicPayTest.src.restful;
using Realms;
using System;
using Xamarin.Forms;
using System.Linq;
using System.Collections.Generic;

namespace PicPayTest
{
    internal class InsertDataPage : ContentPage
    {
        StackLayout Stack;
        Entry Value;
        Entry CardNumber;
        Entry ExpiryDate;
        Entry Cvv;
        ListView ListView;
        int DestinationUserID;


        public InsertDataPage(int id)
        {
            Title = "Informações";
            DestinationUserID = id;
            Stack = new StackLayout();
            Content = Stack;

            DecideHowShowContent();

        }

        public void DecideHowShowContent()
        {
            var cards = GetSavedCard();

            if (cards.Count == 0)
            {
                BuildNoCardContent();
            }
            else
            {
                BuildHaveCardContent(cards);
            }
        }

        private void BuildNoCardContent()
        {
            Value = new Entry { Placeholder = "Valor que deseja enviar", Keyboard = Keyboard.Numeric };
            CardNumber = new Entry { Placeholder = "Número do Cartão", Keyboard = Keyboard.Numeric };
            ExpiryDate = new Entry { Placeholder = "Data de Validade" };
            Cvv = new Entry { Placeholder = "CVV", Keyboard = Keyboard.Numeric };

            var noHaveCardConfirm = new Button { Text = "Confirmar" };

            Stack.Children.Add(Value);
            Stack.Children.Add(CardNumber);
            Stack.Children.Add(ExpiryDate);
            Stack.Children.Add(Cvv);
            Stack.Children.Add(noHaveCardConfirm);

            noHaveCardConfirm.Clicked += Confirm_ClickedAsync;            
        }

        private void BuildHaveCardContent(List<Card> cards)
        {
            Value = new Entry { Placeholder = "Valor que deseja enviar", Keyboard = Keyboard.Numeric };

            ListView = new ListView()
            {
                ItemTemplate = new DataTemplate(() =>
                {
                    var textCell = new TextCell();
                    textCell.SetBinding(TextCell.TextProperty, "CardNumber");
                    textCell.SetBinding(TextCell.DetailProperty, "Detail");

                    return textCell;
                })
            };

            ListView.ItemsSource = cards;

            Stack.Children.Add(Value);
            Stack.Children.Add(ListView);

            ListView.ItemTapped += ListView_ItemTappedAsync;
        }

        private async void ListView_ItemTappedAsync(object sender, ItemTappedEventArgs e)
        {
            var card = (Card)e.Item;
            bool result = false;

            src.model.Transaction transaction = new src.model.Transaction();

            if (!String.IsNullOrEmpty(Value.Text))
            {
                transaction.DestinationUserId = DestinationUserID;
                transaction.Value = int.Parse(Value.Text);
                transaction.CardNumber = card.CardNumber;
                transaction.CVV = card.CVV;
                transaction.ExpiryDate = card.ExpiryDate;

                result = await TransactionRequester.PostTransaction(transaction);
            }


            if (result)
            {
                await DisplayAlert("", "Transação realizada com Sucesso", "Ok");
                await Navigation.PopAsync();
            }
            else
            {
                await DisplayAlert("", "Transação não realizada", "Ok");
            }
        }

        private void HaveCardconfirm_Clicked(object sender, EventArgs e)
        {
            throw new NotImplementedException();
        }

        private async void Confirm_ClickedAsync(object sender, EventArgs e)
        {
            bool result = false;
            src.model.Transaction transaction = new src.model.Transaction();

            if (!String.IsNullOrEmpty(Cvv.Text) && !String.IsNullOrEmpty(Value.Text) && !String.IsNullOrEmpty(CardNumber.Text) && !String.IsNullOrEmpty(ExpiryDate.Text))
            {

                transaction.DestinationUserId = DestinationUserID;
                transaction.Value = int.Parse(Value.Text);
                transaction.CardNumber = CardNumber.Text;
                transaction.CVV = int.Parse(Cvv.Text);
                transaction.ExpiryDate = ExpiryDate.Text;

                result = await TransactionRequester.PostTransaction(transaction);
            }         
            

            if (result)
            {
                SaveCardInfo(transaction);
                await DisplayAlert("", "Transação realizada com Sucesso", "Ok");
                await Navigation.PopAsync();
            }
            else
            {
                await DisplayAlert("", "Transação não realizada", "Ok");
            }

        }

        private void SaveCardInfo(src.model.Transaction transaction)
        {
            var realm = Realm.GetInstance();
            realm.Write(() =>
            {
                realm.Add(new Card
                {
                    CardNumber = transaction.CardNumber,
                    CVV = transaction.CVV,
                    ExpiryDate = transaction.ExpiryDate,
                    Detail = "CVV:  " + transaction.CVV + "   Vencimento  " + transaction.ExpiryDate
                });
            });
        }

        private List<Card> GetSavedCard()
        {
            var realm = Realm.GetInstance();
            return realm.All<Card>().ToList();

            //return new List<Card>();
        }
    }
}