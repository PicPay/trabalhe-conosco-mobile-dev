package test.edney.picpay.view.home

import test.edney.picpay.model.UserModel

interface UserAdapterListener {
      fun onItemClick(user: UserModel)
}