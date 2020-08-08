# Discord-Bot-Refined

Version 0.0.2 has most of the things you'll need to start with the command modules.
(I'll modularize the commands like music, servradministration, events, etc. Each one can be activated for each server)

It connects to Discord.
It has a Exit-Command in the Console, so you can stop the bot.
Connects to the database and creates the tables. 
Listens to the Discord Server Chats and tests if you talk with him.

Just add your Discord Token to the DONOTOPEN.json
And add all other data needed :P

If using the .jar:
Create a .json file with the name DONOTOPEN.json and put

```
[
	{
		"token": "YOUR TOKEN HERE",
		"link": "BOT_INVITE_LINK",
		"mysqlLink": "LINK TO YOUR DATABASE",
		"mysqlUser": "DATABASE USERNAME",
		"mysqlPswd": "DATABASE PASSWORD"
	}
]
```

in it. The file has to be located in the same directory as the .jar file!

