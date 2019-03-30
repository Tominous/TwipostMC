@ECHO OFF
set plugin_path="C:\Users\kiyoshi kanazawa\Documents\miecraft_server\plugins"
set server_path="C:\Users\kiyoshi kanazawa\Documents\miecraft_server"

copy /Y TwipostMC-1.0-SNAPSHOT.jar %plugin_path%
cd %server_path%
java -Xms1024M -Xmx1024M -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 spigot-1.13.2.jar