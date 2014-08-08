/**
 * 
 */
function Character() {
}

Character.prototype.baseUpdate = function(character) {
	this.id = character.id
	this.name = character.name;
	this.order = character.order;
	this.hidden = character.hidden;
	this.currentAction = character.current_action;
	this.editable = character.editable;
	this.type = character.type;
}

Character.prototype.updateOrderAndAction = function(order, action) {
	this.order = order;
	this.currentAction = action;
}

Character.prototype.getLifeStatus = function() {
	maxLife = this.getMaxLife();
	var percent = this.getCurrentLife() * 100 / maxLife;
	if(percent >= 100) return "green";
	else if (percent >= 90) return "white";
	else if (percent >= 50) return "yellow";
	else if (percent >= 0) return "red";
	else return "black";
}

PathfinderCharacter.prototype = (function(parent){
    function protoCreator(){};
    protoCreator.prototype = parent.prototype;
    return new protoCreator();
})(Character);

function PathfinderCharacter(character) {
	this.update(character);
}

PathfinderCharacter.prototype.getCurrentLife = function() {
	return this.hp;
}

PathfinderCharacter.prototype.getMaxLife = function() {
	return this.maxHp;
}

PathfinderCharacter.prototype.update = function(character) {
	this.baseUpdate(character);
	this.hp = character.hp;
	this.maxHp = character.max_hp;
	this.conditionsAndEffects = character.conditions_and_effects;
}

