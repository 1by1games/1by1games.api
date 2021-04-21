package com.esgi.onebyone.infrastructure.models

import com.esgi.onebyone.domain.account.Role
import com.esgi.onebyone.domain.room.Room
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "room")
class RoomModel (
    @Id
    @Type(type = "uuid-char")
    @Column(name = "id") var id: UUID,

    @Column(name = "name") var name: String,
    @Column(name = "password") var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "state") var state: Room.State,

    @Column(name = "size") var roomSize: Int,

    @Column(name = "creation_date") var creationDate : LocalDateTime,

    @Column(name = "end_date") var endDate : LocalDateTime?,

    @Column(name = "currentDiceSize") var currentDiceSize : Int,
    @Column(name = "currentDiceAmount") var currentDiceAmount : Int,


    @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL])
    var members: Collection<MemberModel>



) {

}