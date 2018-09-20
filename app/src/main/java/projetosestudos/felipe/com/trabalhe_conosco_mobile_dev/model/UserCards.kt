package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user_cards")
data class UserCards(@PrimaryKey @ColumnInfo(name = "card_number") val cardNumber: String,
                     @ColumnInfo(name = "card_name") val cardName: String,
                     @ColumnInfo(name = "card_validate") val card_validate: String)