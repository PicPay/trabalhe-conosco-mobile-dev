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


        public InsertDataPage(int id, bool flagRegisterCard = false)
        {
            Title = "Informações";
            DestinationUserID = id;
            Stack = new StackLayout();
            Content = Stack;

            if (!flagRegisterCard)
            {
                DecideHowShowContent();
            }
            else
            {
                BuildRegisterCard_Content();
            }

        }

        public void DecideHowShowContent()
        {
            var cards = GetSavedCard();

            if (cards.Count == 0)
            {
                BuildNoHaveCard_Content();
            }
            else
            {
                BuildHaveCard_Content(cards);
            }
        }


        private void BuildRegisterCard_Content()
        {
            CardNumber = new Entry { Placeholder = "Número do Cartão", Keyboard = Keyboard.Numeric };
            ExpiryDate = new Entry { Placeholder = "Data de Validade" };
            Cvv = new Entry { Placeholder = "CVV", Keyboard = Keyboard.Numeric };

            var registerNewCard = new Button { Text = "Confirmar" };
            
            Stack.Children.Add(CardNumber);
            Stack.Children.Add(ExpiryDate);
            Stack.Children.Add(Cvv);
            Stack.Children.Add(registerNewCard);

            registerNewCard.Clicked += RegisterNewCard_ClickedAsync;

        }        

        private void BuildNoHaveCard_Content()
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

        private void BuildHaveCard_Content(List<Card> cards)
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
            var newCardButton = new Button { Text = "Adicionar Novo Cartão" };
            newCardButton.Clicked += NewCardButton_Clicked;
            

            Stack.Children.Add(Value);
            Stack.Children.Add(ListView);
            Stack.Children.Add(newCardButton);

            ListView.ItemTapped += ListView_ItemTappedAsync;
        }

        private void NewCardButton_Clicked(object sender, EventArgs e)
        {
            Navigation.PushAsync(new InsertDataPage(DestinationUserID, true));
        }

        private async void ListView_ItemTappedAsync(object sender, ItemTappedEventArgs e)
        {
            var card = (Card)e.Item;
            bool result = false;

            src.model.Transaction transaction = new src.model.Transaction();

            if (card.CardNumber == "1111111111111111")
            {
                if (!String.IsNullOrEmpty(Value.Text))
                {
                    transaction.DestinationUserId = DestinationUserID;
                    transaction.Value = int.Parse(Value.Text);
                    transaction.CardNumber = card.CardNumber;
                    transaction.CVV = card.CVV;
                    transaction.ExpiryDate = card.ExpiryDate;

                    result = await TransactionRequester.PostTransaction(transaction);
                }
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
        
        private async void Confirm_ClickedAsync(object sender, EventArgs e)
        {
            bool result = false;
            src.model.Transaction transaction = new src.model.Transaction();

            if (CardNumber.Text == "1111111111111111")
            {
                if (!String.IsNullOrEmpty(Cvv.Text) && !String.IsNullOrEmpty(Value.Text) && !String.IsNullOrEmpty(CardNumber.Text) && !String.IsNullOrEmpty(ExpiryDate.Text))
                {

                    transaction.DestinationUserId = DestinationUserID;
                    transaction.Value = int.Parse(Value.Text);
                    transaction.CardNumber = CardNumber.Text;
                    transaction.CVV = int.Parse(Cvv.Text);
                    transaction.ExpiryDate = ExpiryDate.Text;

                    result = await TransactionRequester.PostTransaction(transaction);
                }
            }           

            if (result)
            {
                SaveCardInfo(transaction);
                await DisplayAlert("", "Transação realizada com Sucesso", "Ok");
                await Navigation.PopToRootAsync();
            }
            else
            {
                await DisplayAlert("", "Transação não realizada", "Ok");
            }

        }

        private async void RegisterNewCard_ClickedAsync(object sender, EventArgs e)
        {
            if (!String.IsNullOrEmpty(Cvv.Text) && !String.IsNullOrEmpty(CardNumber.Text) && !String.IsNullOrEmpty(ExpiryDate.Text))
            {
                var card = new Card
                {
                    CardNumber = CardNumber.Text,
                    CVV = int.Parse(Cvv.Text),
                    ExpiryDate = ExpiryDate.Text,
                    Detail = "CVV:  " + Cvv.Text + "   Vencimento  " + ExpiryDate.Text
                };

                SaveCardInfo(card);

                await DisplayAlert("", "Cadastro realizado com sucesso", "Ok");
                await Navigation.PushAsync(new InsertDataPage(DestinationUserID));
            }
            else
            {
                await DisplayAlert("", "Não foi possível cadastrar", "Ok");
            }

        }

        private void SaveCardInfo(Card card)
        {
            var realm = Realm.GetInstance();
            realm.Write(() =>
            {
                realm.Add(card);
            });
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
        }
    }
}