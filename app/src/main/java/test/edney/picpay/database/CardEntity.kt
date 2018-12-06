package test.edney.picpay.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
class CardEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}