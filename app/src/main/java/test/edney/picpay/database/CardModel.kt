package test.edney.picpay.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "card")
class CardModel {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}