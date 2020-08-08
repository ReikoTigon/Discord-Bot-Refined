package de.dragoncoder.dragonbot.hibernate.entity;

import de.dragoncoder.dragonbot.Main;
import de.dragoncoder.dragonbot.hibernate.dao.BotGuildDAO;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

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

    @Column(name = "botChannel_ID")
    private long botChannel_ID;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "settings_ID")
    private GuildSettings guildSettings;

    //Loaded from the data provided by database
    @Transient private Guild guild;
    @Transient private TextChannel botChannel;

    //Loaded internally

    public BotGuild(long guild_ID) {
        this.guild_ID = guild_ID;
        this.setJoined(true);

        this.guildSettings = new GuildSettings(false);
    }

    public void loadDiscordData() {
        guild = Main.getDragonBot().getShardManager().getGuildById(guild_ID);
        if (guild == null) {
            deactivate();
        } else {
            boolean update = false;

            //noinspection ConstantConditions
            update = loadBotChannel(update);


            if (update) {
                new BotGuildDAO().update(this);
            }
        }

    }

    public void addToCommandsUsed() {
        this.commandsUsed++;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean loadBotChannel() {
        boolean worked = false;

        if (guild != null) {
            worked = loadBotChannel(false);
        }

        return worked;
    }
    private boolean loadBotChannel(boolean boolValue) {
        if (botChannel_ID != 0L) {
            setBotChannel(guild.getTextChannelById(guild_ID));
        }

        if (botChannel != null) {
            return boolValue;
        }
        else {
            setBotChannel_ID(0L);
            return false;
        }
    }

    public void activate() {
        if (!isJoined()) {
            setJoined(true);
            new BotGuildDAO().update(this);
        }
        else {
            new BotGuildDAO().save(this);
        }
    }
    public void deactivate() {
        setJoined(false);
        new BotGuildDAO().update(this);

        Main.getDragonBot().getGuildManager().removeGuild(guild_ID);
    }
}
