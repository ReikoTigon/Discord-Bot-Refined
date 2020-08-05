package de.dragoncoder.dragonbot.hibernate.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString(exclude = "guildInfo") @Getter @Setter @EqualsAndHashCode
@NoArgsConstructor
@Table(name = "Guild_Settings")
public class GuildSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "delete_Commands")
    private boolean delete_Commands;

    @OneToOne(mappedBy = "guildSettings")
    private BotGuild guildInfo;

    public GuildSettings(boolean delete_Commands) {
        setDelete_Commands(delete_Commands);
    }
}