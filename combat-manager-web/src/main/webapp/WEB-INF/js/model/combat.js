/**
 * Combat class
 */
function Combat(combatId) {
	this.id = combatId;
}

Combat.prototype.next = function () {
	
}

Combat.prototype.back = function () {
	
}

Combat.prototype.isCurrent = function(character) {
	return this.currentCharacterId == character.id;
}

Combat.prototype.updateCharacter = function(combatCharacter) {
	var character = this.findCharacter(combatCharacter.id)
	character.update(combatCharacter);
	return character;
}

Combat.prototype.updateCharacterOrderAndAction = function(id, order, action) {
	var character = this.findCharacter(id)
	character.updateOrderAndAction(order, action);
}

Combat.prototype.updateCharacters = function() {
	this.characters.sort(function(c1, c2) {
		return c1.order - c2.order;
	})
}

Combat.prototype.getOrderAndAction = function() {
	var result = {}
	for (var i = 0; i <this.characters.length ; i ++) {
		result[this.characters[i].id] = {order: this.characters[i].order, action : this.characters[i].currentAction}
	}
	return result;
}

Combat.prototype.findCharacter = function(id) {
	if(this.characters) {
		for (var i = 0; i <this.characters.length ; i ++) {
			if(this.characters[i].id == id) {
				return this.characters[i]
			}
		}		
	}
}

Combat.prototype.updateBase = function(combat) {
	this.currentCharacterId = combat.current_character_id;
	this.finished = combat.finished;
	
	//Sometimes combat characters don't get updated
	if(combat.combat_characters) {
		var tempCharacters = new Array();
		
		for(var i = 0 ; i < combat.combat_characters.length; i++) {
			var characterUpdates = combat.combat_characters[i]
			var character = this.findCharacter(characterUpdates.id);
			if(character) {
				tempCharacters.push(character.update(characterUpdates));
			} else {
				tempCharacters.push(this.createCharacter(characterUpdates));
			}
		}
		this.characters = tempCharacters;
		
		this.updateCharacters();
	}
	
}

Combat.prototype.createCharacter = function (character) {
	throw "Abstract function"
}

/**
 * Pathfinder implementation of the combat
 */
PathfinderCombat.prototype = (function(parent){
    function protoCreator(){};
    protoCreator.prototype = parent.prototype;
    return new protoCreator();
})(Combat);

function PathfinderCombat(combatId, turns, roundsPerTurn) {
	Combat.apply(this, arguments)
	this.totalTurns = turns;
	this.totalRounds = roundsPerTurn;

}


PathfinderCombat.prototype.createCharacter = function (character) {
	return new PathfinderCharacter(character)
}

PathfinderCombat.prototype.update = function (combat) {
	this.turn = combat.current_turn;
	this.round = combat.current_round;
	this.updateBase(combat)
}


