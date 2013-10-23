<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">
    
    <jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
	  <jsp:output omit-xml-declaration="true" />
	  <jsp:output doctype-root-element="HTML"
	              doctype-system="about:legacy-compat" />
					
					<div class="header_02">Atributes</div>
					<div class="generic_label no_margin narrowest">
						<label for="race">Race</label>
					</div>
					<div class="generic_value">
						${character.race }
					</div>
					<div class="margin_bottom_5">&#160;</div>
					
					<div class="generic_label no_margin narrowest">
						<label for="class">Class</label>
					</div>
					<div class="generic_value">
						${character.characterClass }
					</div>
					<div class="margin_bottom_5">&#160;</div>
					
					<div class="generic_label no_margin narrowest">
						<label for="hp">HP</label>
					</div>
					<div class="generic_value">
						${character.hp }
					</div>
					<div class="margin_bottom_5">&#160;</div>
					<div class="character_main_attributes pathfinder">
						<div class="header_02">Abilities</div>
						<div class="generic_label no_margin narrow">
							<label for="pathfinder.strength">Strength</label>
						</div>
						<div class="generic_value">
							${character.strength }
						</div>
						<div class="margin_bottom_5">&#160;</div>
						
						<div class="generic_label no_margin narrow">
							<label for="pathfinder.dexterity">Dexterity</label>
						</div>
						<div class="generic_value">
							${character.dexterity }
						</div>
						<div class="margin_bottom_5">&#160;</div>
								
						<div class="generic_label no_margin narrow">
							<label for="pathfinder.constitution">Constitution</label>
						</div>
						<div class="generic_value">
							${character.constitution }
						</div>
						<div class="margin_bottom_5">&#160;</div>
								
						<div class="generic_label no_margin narrow">
							<label for="pathfinder.intelligence">Intelligence</label>
						</div>
						<div class="generic_value">
							${character.intelligence }
						</div>
						<div class="margin_bottom_5">&#160;</div>
								
						<div class="generic_label no_margin narrow">
							<label for="pathfinder.wisdom">wisdom</label>
						</div>
						<div class="generic_value">
							${character.wisdom }
						</div>	
						<div class="margin_bottom_5">&#160;</div>
							
						<div class="generic_label no_margin narrow">
							<label for="pathfinder.charisma">Charisma</label>
						</div>
						<div class="generic_value">
							${character.charisma }
						</div>
						<div class="margin_bottom_20">&#160;</div>		
					</div>       
</jsp:root>