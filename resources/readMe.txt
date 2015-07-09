// Forum Post //
http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1294844-pistronics-2-modular-pistons-rotators-and-statues

// Installation //
1. Install Forge and run in it once.
2. Place this zip into %appdata%/.minecraft/mods

// Changelog //
- Beta Version 0.6.0
  Added: All recipes will now be shown in NEI
  Added: ConfigOption: showAllExtensions, showAllExtensionParts and showAllSails
  Added: ConfigOption: alternateSlimeblockRecipe
  Added: Mirrored Recipes for Saw and Chisel
  Added: Stopper
  Added: The following vanilla blocks can now be rotated: Logs, Stairs, Pistons, Furni,
	Droppers, Dispensers and Chests
  Changed: Tools can no longer be crafted with redstone rods (this was never intended)
  Changed: Doubled the output of the Camoupaste recipe
  Changed: Changed and nerfed crafting path for Petrify Arrows 
	(24 Arrows/spidereye -> 6 Arrows/spidereye)
  Fixed: various not-stacking-bugs
  Fixed: redstone rods sometimes being treated as normal ones
  Fixed: being able to use glass camoued Camoublocks to see through the ground (aka X-Ray bug)
  

- Beta Version 0.5.8
  Added: Chisel
  Changed: Statues now use the same texture as vanilla stone 
	(if you have a texturepack installed it will use that)
  Changed: Statue default texture resolution
  Fixed: Blocks not rendering when rotated over chunkborders
  Fixed: Statues not rendering while rotating
  Fixed: some render glitches in the Book of Gears

- Beta Version 0.5.7
  Fixed: Statues not updating for other players on servers
  Fixed: Statue camou textures
  Fixed: Sides of moving blocks not rendering sometimes

- Beta Version 0.5.6
  Fixed: Statues causing problems on server load

- Beta Version 0.5.5
  Added: Book of Gears
  Added: ConfigOption: whiteList
  Added: ConfigOption: modBlackList
  Added: ConfigOption: modWhiteList
  Added: ConfigOption: opaqueSlimeblocks
  Changed: Added WIP tag to gears
  Changed: default blacklist
  Fixed: One possible recipe for extensions should now be shown in NEI

- Beta Version 0.5.4
  Fixed: Petrify Arrows not being consumed
  Fixed: Arrow renderer being reigstered serverside

- Beta Version 0.5.3
  Added: Statues
  Added: Petrify Arrow
  Added: Petrify Extract
  Added: ConfigOption: statueScale -Min/-Max/-Step
  Added: ConfigOption: renderStatuesInInventory
  Added: ConfigOption: renderStatuesAsBoxes
  Added: ConfigOption: statueSmoke
  Changed: Updated Camoupaste and Spade Tooltip
  Changed: sortInRows is now disabled by default
  Changed: FMP blocks are now blacklisted (This is a temporary solution)
  Changed: slightly increased gear hardness
  Fixed: Recipes including gears not showing in NEI
  Fixed: Camoublock textures can now be removed with a Spade
  Fixed: Pushed Blocks sometimes changing their Blockbounds
  (Hopefully) Fixed: Gears sometimes not rendering if the center is not in your field of vision.

- Beta Version 0.5.2
  Added: Pistons (not Rotators) now push Entities.
  Added: The creative machine can now be configured for every redstone 
	strength individually
  Added: Tooltip to Gears and Sailparts to clearify how they are made.
  Added: Rods and Extension now try to predict the way you want to place them
  Added: ConfigOption: predictPlacement
  Changed: Rightclicking a Rodfolder or CreativeMachine will now only open the GUI
	when the base of the machine was targeted.
  Fixed: Placing blocks by clicking on the side of a partblock now works again
  Fixed: Redstone signals being transfered "through" the creative machine
  Fixed: Client sometimes crashing when pushing/retrieving on a very fast pace
  Fixed: Machines no longer z-Fight with rods/extensions
  Fixed: Rodsides not rendering if a block is next to them
  Fixed: Rods are no longer shown to move inside the rodfolder from the back.
  Fixed: ExtensionPart Textures on 1.6.4
  Fixed: You can no longer pass through moving blocks
  (Hopefully) Fixed: Game sometimes crashing with a ConcurrentModificationException

- Beta Version 0.5.1
  Fixed: Game sometimes crashing when rightcliking a camou block
  Fixed: Pistons excepting/absorbing rodfolders from behind

- Beta Version 0.5.0
  Added: Sail Parts
  Added: Gears (not functional yet but used in recipes)
  Added: ConfigOption: sortTab
  Added: ConfigOption: sortInRows
  Changed: Creative Tab Icon 
  Changed: The Creative Tab is now sorted id independend
  Changed: Piston, Rotator, Rodfolder, Creative Machine and Tool Recipes to include 
	small gears instead of sticks
  Fixed: Saw and Extension Parts not working in other dimension in singleplayer

- Beta Version 0.4.0
  Removed: Adv Pison and Adv Rotator (they were invisible)
  Added: Creative Machine 
  Added: Camoupaste can now be used on pistons, rotators and rodfolders
  Added: The ability to tune redstone extensions on specific redstone strengths
  Added: Config Option enabling the creative machine recipe
  Fixed: Saw not working in singleplayer
  Fixed: Not being able to add material to extension parts in singleplayer
  Fixed: Rodfolder gui not showing the inventory name

- Beta Version 0.3.5
  Fixed: Rightclicking parts with an empty handing causing crahs
  Fixed: Game Crashing in Multiplayer when trying to move a structure with
	attached Blocks.
  Fixed: Some Blocks not updating for other players in Multiplayer

- Beta Version 0.3.4
  Fixed: Rotators still being able to rotate blocks out of the world
  Fixed: Redioactive (Super) Glue tooltip

- Beta Version 0.3.3
  Added: Redioactive (Super) Glue
  Added: Blacklist to the config. Bedrock is blacklisted by default
  Fixed: Blocks can no longer be pushed over the world bounds
  Fixed: Cleaned up some extension code
  Fixed: Cleaned up some debug code

- Beta Version 0.3.2
  Added: Config Files
  Added: ConfigOption: maxBlocksMoving
  Added: ConfigOption: machineSmoke
  Added: ConfigOption: toolSpeed
  Added: ConfigOption: moveAttached
  Changed: rotating Blocks with The Tool is now 3x faster by default
  Fixed: Extensions not able to push orthogonal rods
  Fixed: some bugs moving attached blocks
  Fixed: redstone extensions not outputting in 1.6.4
  Fixed: Crash when trying to move attached blocks in 1.6.4

- Beta Version 0.3.1
  Fixed: Common Rendercrash with newer forge versions

- Beta Version 0.3.0
  Added: Redstone Rods (etc.) are now functional
  Added: RedstoneTorches/Blocks now pulse all connected redstone things
  Added: Bugs (amount unknown)
  Added: some blocks that depend on moving blocks will now move along
  Changed: rightclicking a rodfolder with "The Tool" will no longer open its inventory
  Fixed: a bug where rods/extension tried to convert into partblocks while hold by a 	machine

- Beta Version 0.2.0
  Added: Tooltips/Flavortext to all blocks and items
  Added: Slime-, Glue- and Superglueblock.
  Added: Rodfolder
  Changed: Rod, Extension, Part items. Old ones will disappear.
  Changed: Rules for pushing and pulling through pistons.
  Fixed: Crashbug where the piston was trying to move without an element.
         (Reported by Cheeyev)
  Fixed: Rods and Extensions can now be added to existing partblocks and extensions.
  Fixed: Extensions sometimes moving not connected blocks while rotating.
  Fixed: Some Items not stacking in 1.6.4

- Beta Version 0.1.1
  Fixed: missing modbase changes for 1.7.10

- Beta Version 0.1.0
  Added: all missing Recipes currently needed.
  Added: lang file for 1.7.2/1.7.10 and default localization for 1.6.4
  Added: localization to extension tooltips and modtab.
  Added: extensions, rods and partblocks will now drop in creative if harvested with a 
	shovel, axe or pickaxe.
  Added: using the "pick block" key now correctly works for extensions, rods and 	partblocks.
  Fixed: rods not dropping the correct item.
  Fixed: Camoublock not reacting to rotation.
  Fixed: Blocks being bulldozed over by moving blocks in 1.6.4.
  Fixed: 1.6.4 Itemrendering.
  Fixed: Machines not saving extension/rod data in 1.6.4.
  Fixed: Machines not reading the nbt from an added item.
  Fixed: Should now work on servers for all versions.
  Changed: removed RodFolder, Adv. Piston and Adv. Rotator from creative inventory.
  Changed: "The Tool" no longer acts as a debug tool and is functional.
  Changed: Camou Block texture slightly.

- Beta Version 0.0.0

// Copyright //

TERMS AND CONDITIONS
0. USED TERMS
MOD - modification, plugin, a piece of software that interfaces with the Minecraft client to extend, add, change or remove original capabilities.
MOJANG - Mojang AB
OWNER - Letiu, Original author of the MOD. Under the copyright terms accepted when purchasing Minecraft 
	(http://www.minecraft.net/copyright.jsp) the OWNER has full rights over their MOD despite use of MOJANG code.
USER - End user of the mod, person installing the mod.

1. LIABILITY
THIS MOD IS PROVIDED 'AS IS' WITH NO WARRANTIES, IMPLIED OR OTHERWISE. 
THE OWNER OF THIS MOD TAKES NO RESPONSIBILITY FOR ANY DAMAGES INCURRED FROM THE USE OF THIS MOD. 
THIS MOD ALTERS FUNDAMENTAL PARTS OF THE MINECRAFT GAME, PARTS OF MINECRAFT MAY NOT WORK WITH THIS MOD INSTALLED. 
ALL DAMAGES CAUSED FROM THE USE OR MISUSE OF THIS MOD FALL ON THE USER.

2. USE
Use of this MOD to be installed, manually or automatically, is given to the USER without restriction.

3. REDISTRIBUTION
On its own this MOD may only be distributed where uploaded, mirrored, or otherwise linked to by the OWNER solely. 
It may be included in any modpack with other mods.
All mirrors of this MOD must have advance written permission from the OWNER. 
ANY attempts to make money off of this MOD (selling, selling modified versions, adfly, sharecash, etc.) 
are STRICTLY FORBIDDEN, and the OWNER may claim damages or take other action to rectify the situation.

4. DERIVATIVE WORKS/MODIFICATION
This mod is provided freely and may be decompiled and modified for private use, either with a decompiler or a bytecode editor. 
Public distribution of modified versions of this MOD require advance written permission of the OWNER and may be subject to certain terms.