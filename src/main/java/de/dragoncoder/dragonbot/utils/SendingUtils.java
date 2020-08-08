package de.dragoncoder.dragonbot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SendingUtils {

    public static void sendError(MessageChannel channel, String message) {
        EmbedPreparer preparer = new EmbedPreparer();
        preparer.title = "ERROR";
        preparer.description = "Message: ".concat(message);
        preparer.color = 0xd62b00;
        preparer.author = "DragonBot";

        send(channel, prepareBuilder(preparer, false), 30L);
    }

    public static void sendInfo(MessageChannel channel, String message) {
        EmbedPreparer preparer = new EmbedPreparer();
        preparer.title = "INFO";
        preparer.description = "Message: ".concat(message);
        preparer.color = 0x1eb4d6;
        preparer.author = "DragonBot";

        send(channel, prepareBuilder(preparer, false), 10L);
    }

    public static void sendNotification(MessageChannel channel, String message) {
        EmbedPreparer preparer = new EmbedPreparer();
        preparer.title = "Notification";
        preparer.description = "Message: ".concat(message);
        preparer.color = 0x1eb4d6;
        preparer.author = "DragonBot";

        send(channel, prepareBuilder(preparer, false), 0L);
    }

    public static void sendEmbedWithTitle(MessageChannel textChannel, String title, boolean useDefault, long deleteAfter) {
        EmbedPreparer preparer = new EmbedPreparer();
        preparer.title = title;

        send(textChannel, prepareBuilder(preparer, useDefault), deleteAfter);
    }
    public static void sendEmbedWithDescription(MessageChannel textChannel, String description, boolean useDefault, long deleteAfter) {
        EmbedPreparer preparer = new EmbedPreparer();
        preparer.description = description;

        send(textChannel, prepareBuilder(preparer, useDefault), deleteAfter);
    }
    public static void sendEmbedWithHeader(MessageChannel textChannel, String title, String description, boolean useDefault, long deleteAfter) {
        EmbedPreparer preparer = new EmbedPreparer();
        preparer.title = title;
        preparer.description = description;

        send(textChannel, prepareBuilder(preparer, useDefault), deleteAfter);
    }

    /**
     * Send embed with all Field Parameters.
     *
     * @param textChannel The {@link net.dv8tion.jda.api.entities.TextChannel TextChannel} or {@link net.dv8tion.jda.api.entities.PrivateChannel PrivateChannel} the message should be sent to.
     * @param title       The Title for the {@link net.dv8tion.jda.api.entities.MessageEmbed MessageEmbed}.
     * @param description The Description for the {@link net.dv8tion.jda.api.entities.MessageEmbed MessageEmbed}.
     * @param fields      The {@link net.dv8tion.jda.api.entities.MessageEmbed.Field Fields} for the {@link net.dv8tion.jda.api.entities.MessageEmbed MessageEmbed}.
     * @param useDefault  A flag if you want the default builder settings for fields that are initial.
     * @param deleteAfter The time in {@link java.util.concurrent.TimeUnit#SECONDS Seconds} after which the message gets deleted. If 0 it won't get deleted.
     *
     */
    public static void sendEmbedWithAll(MessageChannel textChannel, String title, String description , ArrayList<MessageEmbed.Field> fields, boolean useDefault, long deleteAfter) {
        EmbedPreparer preparer = new EmbedPreparer();
        preparer.title = title;
        preparer.description = description;
        preparer.fields.addAll(fields);

        send(textChannel, prepareBuilder(preparer, useDefault), deleteAfter);
    }

    private static EmbedBuilder prepareBuilder(EmbedPreparer preparer, boolean useDefault) {
        EmbedBuilder builder = new EmbedBuilder();

        if (useDefault && preparer.title.equals("")) {
            builder.setTitle(null);
        } else builder.setTitle(preparer.title);
        if (useDefault && preparer.description.equals("")) {
            builder.setDescription(null);
        } else builder.setDescription(preparer.description);
        if (useDefault && preparer.author.equals("")) {
            builder.setAuthor("DragonBot");
        } else builder.setAuthor(preparer.author);
        if (useDefault && preparer.footer.equals("")) {
            builder.setFooter("provided by Reiko Tigon#3233");
        } else builder.setFooter(preparer.footer);
        if (useDefault && preparer.color == 0) {
            builder.setColor(0x1eb4d6);
        } else builder.setColor(preparer.color);
        if (!preparer.fields.isEmpty()) {
            for (MessageEmbed.Field field: preparer.fields) {
                builder.addField(field);
            }
        }
        builder.setTimestamp(Instant.now());

        return builder;
    }

    private static void send(MessageChannel textChannel, EmbedBuilder builder, long deleteAfter) {
        if (deleteAfter != 0L) {
            textChannel.sendMessage(builder.build()).complete().delete().queueAfter(deleteAfter, TimeUnit.SECONDS);
        }
        else {
            textChannel.sendMessage(builder.build()).queue();
        }
    }


    private static class EmbedPreparer {
        private String title;
        private String description;
        private final ArrayList<MessageEmbed.Field> fields;
        private int color;
        private String author;
        @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
        private String footer;

        EmbedPreparer() {
            title = "";
            description = "";
            fields = new ArrayList<>();
            color = 0;
            author = "";
            footer = "";
        }
    }
}
