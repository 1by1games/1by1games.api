package com.esgi.onebyone.infrastructure.mappers

import com.esgi.onebyone.domain.account.AccountID
import com.esgi.onebyone.domain.room.Member
import com.esgi.onebyone.infrastructure.models.MemberModel


fun MemberModel.to() = Member(
        id = AccountID(id),
        username = account.username,
        isAuthor = isAuthor,
        diceThrows = diceThrows.map { it.to() }.toSortedSet{ a, b -> a.throwDate.compareTo(b.throwDate)}
)

//fun Member.to() = MemberModel(id.value, isAuthor, ??? ,  )