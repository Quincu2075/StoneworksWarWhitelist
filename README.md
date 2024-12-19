# StoneworksWarWhitelist
The easiest way to whitelist any war! (works with Lands plugin)

Dependencies: 
- LuckPerms
- Lands

Soft-Dependencies:
- RedisChat

# War Whitelist (Staff)

NOTE: This is a permission-based whitelist and doesn't modify the vanilla whitelist! It will let in players if they meet one of the following conditions:
- The player is trusted in one of the two lands at war
- The player is whitelisted on the vanilla whitelist
- The player has the bypass permission (warwhitelist.bypass)

You can use these commands below to enable/disable a war whitelist.

`/startwarwhitelist <land1> [land2]`

If there is a lands war active, you only need 1 land argument to start the war. The preparation phase of the war doesn't count; it has to be in an ACTIVE war.
If there isn't a lands war active yet, which is likely the case, you'll need to enter both land names as arguments.

`/endwarwhitelist`

This will terminate the war whitelist completely. An end of a war does NOT automatically end the war whitelist, this must be done manually.

# Demonstration

Admin uses '/war admin start test1 test2 0 15m' to start the lands war and then '/startwarwhitelist test1 test2' to enable war whitelist
   
![image](https://github.com/user-attachments/assets/a266f51f-d0ab-446d-b5f7-843832543b7a)

![image](https://github.com/user-attachments/assets/e6344935-8742-4e6d-9e49-eac66dbcf64b)

NOTE: On a velocity configuration, the player can just be sent to the lobby.

At the end of the war the admin uses '/endwarwhitelist'
   
![image](https://github.com/user-attachments/assets/d91a4c8c-1571-4551-a8db-8d5d139a199e)

# Other Features

War End Broadcast

![image](https://github.com/user-attachments/assets/b2cfea67-7be1-4779-af8c-9701dee9f877)

Score Update Broadcast

![image](https://github.com/user-attachments/assets/2d165faa-8eb5-4daa-9cce-21c9dca2d4b6)

Quincu, the lands plugin already has these messages available, why did you add them as features?

1. When a player disconnects from the server and reconnects (or goes to another quad and comes back), they will no longer receive these messages.
2. These broadcasts now are available to work with RedisChat and can be broadcasted to the other servers synced with it.

NOTE: These features can be disabled by setting their values to "" in the config.yml!

Dynamic Player Limit

![Dynamic Player Limit](https://github.com/user-attachments/assets/308a657b-4048-4e1d-b713-102af8f0cbe6)

The server will lower the player limit when a war whitelist is enabled, granting the server better performance for a war! There isn't a need to let in more than 100-120 players in a war anyways, so it works perfectly!

NOTE: You can disable this feature by setting 'war-max' to -1 !


