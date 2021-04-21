package com.esgi.onebyone.infrastructure.models

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "member")
class MemberModel (

        @Id
        @Type(type = "uuid-char")
        @Column(name = "id")
        var id: UUID,

        @Column(name = "is_author")
        var isAuthor : Boolean,

        @ManyToOne
        @Type(type = "uuid-char")
        @JoinColumn(name = "account_id")
        @MapsId("accountId")
        var account: AccountModel?,

        @ManyToOne
        @Type(type = "uuid-char")
        @JoinColumn(name = "room_id")
        @MapsId("roomId")
        var room: RoomModel,


        @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL])
        var diceThrows: Collection<DiceThrowModel>

)