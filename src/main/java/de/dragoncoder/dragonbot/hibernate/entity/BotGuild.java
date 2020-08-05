package de.dragoncoder.dragonbot.hibernate.entity;

import de.dragoncoder.dragonbot.Main;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Guilds")
public class BotGuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "guild_ID")
    private long guild_ID;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "commandsUsed")
    private int commandsUsed;

    @Column(name = "onServer")
    private boolean joined;


    //Other Tables
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "settings_ID")
    private GuildSettings guildSettings;

    //Loaded from the data provided by database
    @Transient private Guild guild;

    //Loaded internally

    public BotGuild(long guild_ID) {
        this.guild_ID = guild_ID;
        this.setJoined(true);

        this.guildSettings = new GuildSettings(false);
    }

    public void loadDiscordData() {
        guild = Main.getDragonBot().getShardMan().getGuildById(guild_ID);
    }

    public void addToCommandsUsed() {
        this.commandsUsed++;
    }
}
