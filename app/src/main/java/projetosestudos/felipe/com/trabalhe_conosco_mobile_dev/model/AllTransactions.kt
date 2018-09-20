package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "all_transactions")
data class AllTransactions(@PrimaryKey @ColumnInfo(name = "username") val username: String,
                           @ColumnInfo(name = "image") val image: String,
                           @ColumnInfo(name = "time_transaction") val timeTransaction: String)