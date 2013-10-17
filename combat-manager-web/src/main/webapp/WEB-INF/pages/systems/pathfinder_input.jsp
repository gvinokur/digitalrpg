<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">
    
    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	  <jsp:output omit-xml-declaration="true" />
	  <jsp:output doctype-root-element="HTML"
	              doctype-system="about:legacy-compat" />

					<div class="margin_bottom_20">&#160;</div>
					<div class="generic_label narrowest">
						<label for="race">Race</label>
					</div>
					<input type="text" id="pathfinder_race" name="pathfinder.race" autocomplete="off"
						class="generic_field narrowest" />
					
					<div class="generic_label narrowest">
						<label for="class">Class</label>
					</div>
					<input type="text" id="pathfinder_class" name="pathfinder.characterClass" autocomplete="off"
						class="generic_field narrowest" />
					
					<div class="generic_label narrowest">
						<label for="hp">HP</label>
					</div>
					<input type="text" id="pathfinder_hp" name="pathfinder.hp" autocomplete="off"
						class="generic_field tinier" />
					
					<div class="character_main_attributes pathfinder">
						<div class="header_02">Abilities</div>
						<div class="generic_label narrow">
							<label for="pathfinder.strength">Strength</label>
						</div>
						<input type="text" id="pathfinder_strength" name="pathfinder.strength" autocomplete="off"
							class="generic_field tinier" />
							
						<div class="generic_label narrow">
							<label for="pathfinder.dexterity">Dexterity</label>
						</div>
						<input type="text" id="pathfinder_dexterity" name="pathfinder.dexterity" autocomplete="off"
							class="generic_field tinier" />
								
						<div class="generic_label narrow">
							<label for="pathfinder.constitution">Constitution</label>
						</div>
						<input type="text" id="pathfinder_constitution" name="pathfinder.constitution" autocomplete="off"
							class="generic_field tinier" />
								
						<div class="generic_label narrow">
							<label for="pathfinder.intelligence">Intelligence</label>
						</div>
						<input type="text" id="pathfinder_intelligence" name="pathfinder.intelligence" autocomplete="off"
							class="generic_field tinier" />
								
						<div class="generic_label narrow">
							<label for="pathfinder.wisdom">wisdom</label>
						</div>
						<input type="text" id="pathfinder_wisdom" name="pathfinder.wisdom" autocomplete="off"
							class="generic_field tinier" />	
							
						<div class="generic_label narrow">
							<label for="pathfinder.charisma">Charisma</label>
						</div>
						<input type="text" id="pathfinder_charisma" name="pathfinder.charisma" autocomplete="off"
							class="generic_field tinier" />		
					</div>
					
</jsp:root>