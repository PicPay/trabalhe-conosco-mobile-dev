package test.edney.picpay.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
class CardEntity {
      @PrimaryKey(autoGenerate = false)
      var id: Int = 1
      var number: String? = null
      var name: String? = null
      var expiration: String? = null
      var cvv: String? = null
}