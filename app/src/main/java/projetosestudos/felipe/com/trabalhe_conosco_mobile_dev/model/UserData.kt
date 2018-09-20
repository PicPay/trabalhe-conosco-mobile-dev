package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "user_data")
data class UserData(@PrimaryKey @NonNull @ColumnInfo(name = "id") val id: Int, @ColumnInfo(name = "saldo") val saldo: Double)