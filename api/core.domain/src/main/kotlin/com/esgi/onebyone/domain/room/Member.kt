package com.esgi.onebyone.domain.room

import com.esgi.onebyone.domain.account.AccountID
import java.util.*

class Member(
    val id: AccountID,
    val username: String,
    var isAuthor: Boolean,
    val diceThrows: SortedSet<DiceResult>,
    val accountId: UUID?
)
{

}