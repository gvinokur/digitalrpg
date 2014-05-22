
drop table  pathfinder_combat_character_conditions;
drop table pathfinder_combat_character_magical_effects;
drop table pathfinder_combat_characters;
update combats set current_character_id = null;
drop table combats cascade;
drop table combat_characters;
drop table pathfinder_combats;


drop table if exists non_player_characters;
drop table if exists player_characters;
drop table pathfinder_character;
drop table system_character cascade;
drop table character_invites;
drop table characters cascade;
