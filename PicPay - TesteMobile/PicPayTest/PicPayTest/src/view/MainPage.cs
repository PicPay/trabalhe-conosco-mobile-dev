using PicPayTest.src.model;
using PicPayTest.src.restful;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Xamarin.Forms;

namespace PicPayTest
{
	public class MainPage : ContentPage
	{
        StackLayout Stack;

		public MainPage ()
		{
            this.Title = "Lista de Pessoas";
            Stack = new StackLayout{};
            Content = Stack;
            AddListView();
        }

        public async void AddListView()
        {

            var listView = new ListView
            {
                ItemTemplate = new DataTemplate(() =>
                {
                    var imageCell = new ImageCell();
                    imageCell.SetBinding(TextCell.TextProperty, "Name");
                    imageCell.SetBinding(TextCell.DetailProperty, "Username");
                    imageCell.SetBinding(ImageCell.ImageSourceProperty, "Img");

                    return imageCell;
                })
            };

            var listUsers = await UserRequester.GetUsers();            

            listView.ItemsSource = listUsers;
            listView.ItemTapped += ListView_ItemTapped;

            Stack.Children.Add(listView);            
        }

        private void ListView_ItemTapped(object sender, ItemTappedEventArgs e)
        {
            var user = (User)e.Item;
            
            Navigation.PushAsync(new InsertDataPage(user.Id));
        }
    }
}