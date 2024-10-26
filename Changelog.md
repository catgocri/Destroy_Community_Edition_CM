# Destroy CE Changelog

ADDITIONS
- Added porkchop blocks, bacon blocks, raw bacon, cooked bacon, animal fat, and soap (which removes negative status effects from the user), as as well glycerol production from animal fat
- Added thionyl chloride substitution generic reaction
- Added isopropanol, bisulfite, sulfite, thionyl chloride, sodium bisulfite synthesis, 
- Added soap + glycerol production from calcium hydroxide and sodium hydroxide
- Added gold ore dissolution and gold precipitation using sodium bisulfite, as well as gold dust that can be smelted back into gold
- Added hydrogen cyanide dissociation using hydroxides
- Added dissolution of unoxidized sodium ingots
- Added the encased fan washing of sodium ingots into their oxidized versions, to allow for easier automation

BALANCE CHANGES
- Multiple pipes can now connect to a single Bubble Cap block, allowing for easier input and output from the reboiler
- Protons no longer prevent electrolysis recipes from happening (this is a temporary fix for the fact that there is no easy way to remove protons from solution, and will likely receive a more permanent solution eventually)
- Superheated distillation towers now heat to 650K instead of 600K, to allow for the separation of mercury from sodium
- Extrusion dies are now crafted with inox rods instead of nanodiamonds to bring extrusion more in-line with mid-game crafting
- Multiplied the refinery gas extracted from crude oil distilling by a factor of 20, to allow for sufficient amounts of extraction and make petrochemistry more worthwhile
- SBR now accepts any rubber plastic (SBR or polyisoprene) instead of only SBR

BUG FIXES

- Fixed library implementations to allow for modding of Destroy's files
- Made vat controllers mineable with iron pickaxes
- Fixed texture/minimap issues with pollution making everything gray
- Fixed a crash with siphons
- Carbon monoxide oxidation should now only happen at elevated temperatures
- R-22 pyrolysis should now only happen at elevated temperatures
- Adjusted golden carrots to yield 8 moles of gold when dissolved instead of 10 (since they're made with 8 gold nuggets, not 10)
- Sodium dissolution is now strongly exothermic
- Fixed some tag issues that broke recipes involving primary or secondary explosives, such as bomb bons and fireworks
- Having an open-vent Vat no longer causes the game to hang when you quit to menu
- Fixed a bug which causes the game to crash when going through the End's exit portal