/**
 * Combat Console
 * Available Options:
 * combatId: Id of the combat to get
 * systemType: 'pathfinder', etc...
 * baseUrl: Base url for the application.
 * mode: 'gm' or 'player'. 
 * csrfHeaderName: Header to send to protect posts agains cross site scripting
 * csrfToken: Token value for CSFR
 * combatSystemData: Combat System specific data
 * 
 */
(function ($) {
	
	var combatConsole = new Console();
	
	var widgets = {
		'Pathfinder' : {
			'lg' : '<li character-id="{character-id}" class="combat-character"><div class="selected-char" style="width: 10px">&#160;</div><div class="character-data character-name" attribute-name="name"></div><div style="width:25px" attribute-name="hp" attribute-type="life" class="character-data hp editable" data-type="number" data-title="HP" data-pk="" data-url="/combats/character/currentHitPoints"></div><div class="hiddenSelect character-data editable" attribute-name="hidden" attribute-type="boolean" data-pk="" data-url="/combats/character/hidden">Hidden <input type="checkbox"/></div><div class="character-data conditions-and-effects" attribute-name="conditionsAndEffects" title=""></div>	</li>'
		}
	}
	
	var actions = {
		'Pathfinder' : ['Playing' , 'Ready', 'Delayed']	
	}
	
	$.fn.combatConsole = function(options) {
		
		// This is the easiest way to have default options.
        combatConsole.settings = $.extend({
            // These are the defaults.
        	mode : 'gm'
        }, options );
        
        combatConsole.createCombat();
		
		combatConsole.el = $(this);
		
		combatConsole.createBody();
		
		combatConsole.reload();

		$(window).resize(function() {
			combatConsole.resized();
		})
		
		$.fn.editable.defaults.ajaxOptions = {
    		    beforeSend: beforePost
    		};
    	$.fn.editable.defaults.showbuttons = false;
    	
    	$(this).on('click',".editable[attribute-type='boolean'] input",function(){
    		var pk = $(this).parent().attr("data-pk");
    		var url = $(this).parent().attr("data-url");
    		var checked = $(this).prop( "checked" )
    		$.ajax({
    			url: url,
    			beforeSend : beforePost,
    			type: "POST",
    			data: {pk : pk, value : checked},
    			dataType : "json",
    			success : function(combatStatus) {
    				//Do nothing, already updated here.
    			}
    		})
    	});
    	
    	$(this).editable({
    		selector: ".editable[attribute-type='life']",
    		success : function(response, newValue) {
    			$(this).css("color", response.hit_point_status);
    			$(this).css("background-color", response.hit_point_status);
    		},
    		inputclass : "narrow_input"
    	})
		
		return this;
	}
	
	
	function beforePost(xhr, settings) {
        if (!csrfSafeMethod(settings.type) && sameOrigin(settings.url)) {
            // Send the token to same-origin, relative URLs only.
            // Send the token only if the method warrants CSRF protection
            // Using the CSRFToken value acquired earlier
            xhr.setRequestHeader(combatConsole.settings.csrfHeaderName, combatConsole.settings.csrfToken);
        }
    }
    
    function csrfSafeMethod(method) {
	    // these HTTP methods do not require CSRF protection
	    return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
	}
	function sameOrigin(url) {
	    // test that a given url is a same-origin URL
	    // url could be relative or scheme relative or absolute
	    var host = document.location.host; // host + port
	    var protocol = document.location.protocol;
	    var sr_origin = '//' + host;
	    var origin = protocol + sr_origin;
	    // Allow absolute or scheme relative URLs to same origin
	    return (url == origin || url.slice(0, origin.length + 1) == origin + '/') ||
	        (url == sr_origin || url.slice(0, sr_origin.length + 1) == sr_origin + '/') ||
	        // or any other URL that isn't scheme relative or absolute i.e relative.
	        !(/^(\/\/|http:|https:).*/.test(url));
	}

	function Console() {
		
	}

	Console.prototype.createBody = function() {
		
		combatConsole.el.empty()
		
		combatConsole.consoleWidth = combatConsole.el.width();
		if(combatConsole.consoleWidth >= 400) {
			combatConsole.size = 'lg'
		} else if (combatConsole.consoleWidth >= 300) {
			combatConsole.size = 'md'
		} else {
			combatConsole.size = 'sm';
		}
		
		this.useGridster = (this.size == 'lg' || this.size == 'md');
		
		if(this.useGridster) {
			
			var headerDIV = $("<div/>").appendTo(this.el)
			headerDIV.addClass("titles").addClass("row");
			for(var i = 0; i < actions[this.settings.systemType].length; i++) {
				$("<div/>").addClass("col-xs-4").css("text-align","center").append(actions[this.settings.systemType][i]).appendTo(headerDIV)
			}
			
			var gridsterDIV = $("<div/>").appendTo(this.el);
			gridsterDIV.addClass("gridster")
			var gridsterUL = $("<ul/>").appendTo(gridsterDIV)
			
			gridsterWidth = (combatConsole.consoleWidth-40)/5;
			combatConsole.gridster = gridsterUL.gridster({
	            widget_margins: [5, 5],
	            widget_base_dimensions: [gridsterWidth, 40],
	            min_cols:5,
	            max_cols:5,
	            draggable : {
	            	stop : function(e, ui, $widget){
	            		var data = combatConsole.buildSortAndStatusData();
	            		$.ajax({
	        				url : combatConsole.buildUrl("combats/{id}/character/orderAndAction"),
	        				dataType : "json",
	        				type : "POST",
	        				contentType : "application/json",
	        				data: JSON.stringify(data),
	        				beforeSend : beforePost,
	        				success : function () {
	        				}
	        			})
	            	}
	            }
	        }).data("gridster");
			
			if(this.settings.mode == 'player') {
				this.gridster.disable();
			}
		}
	}
	
	Console.prototype.buildSortAndStatusData = function() {
		$("li.combat-character").each(function(){
    		var id = $(this).attr("character-id")
    		var order = $(this).attr("data-row") - 1
    		col = $(this).attr("data-col")
    		var action = actions[combatConsole.settings.systemType][col-1]
    		combatConsole.combat.updateCharacterOrderAndAction(id, order, action)
    	});
		this.combat.updateCharacters()
    	return this.combat.getOrderAndAction()
	}
	
	Console.prototype.destroyBody = function() {
		if(this.useGridster) {
			this.gridster.destroy();
		}
		this.el.empty();
		this.el.append($("<div style='width:100%;text-align:center;'><i class='fa fa-refresh fa-spin fa-5x'><!--  --></i></div>"))
	}
	
	Console.prototype.resized = function() {
		if(this.consoleWidth == combatConsole.el.width()) {
			//Do nothing, width hasn't changed.
			return;
		} else {
			this.destroyBody();
			if(this.reloadTimeout) {
				clearTimeout(this.reloadTimeout);
			}
			this.reloadTimeout = setTimeout(function() {
				combatConsole.createBody();
				combatConsole.reload();
			}, 1000);
		}
	}
	
	Console.prototype.reload = function() {
		var url = combatConsole.buildUrl("combats/{id}/status")
		$.ajax({
			url: url,
			dataType: "json",
			type: "GET",
			success : function(combatStatus) {
				combatConsole.updateCombat(combatStatus)
			},
			complete: function() {
				setTimeout(combatConsole.reload, 5000); 
			}
		})	
	}
	
	Console.prototype.updateCombat = function(combatStatus) {
		this.combat.update(combatStatus);
		
		//Now change all data, including creating the widgets and stuff
		for(var i = 0; i < this.combat.characters.length; i++) {
			character = this.combat.characters[i];
			characterWidget = $(".combat-character[character-id='"+ character.id +"']")
			if(characterWidget.length == 0) {
				//Create widget
				var widgetText = widgets[this.settings.systemType][this.size].replace("{character-id}", character.id);
				var col = actions[this.settings.systemType].indexOf(character.currentAction) + 1;
				if(this.useGridster) {
					characterWidget = this.gridster.add_widget(widgetText, 3, 1, col, character.order + 1)	
				}
			}
			this.updateWidgetData(characterWidget, character);
		}
	}
	
	Console.prototype.updateWidgetData = function(characterWidget, character) {
		if(this.settings.mode == 'player') {
			var col = actions[this.settings.systemType].indexOf(character.currentAction) + 1;
			characterWidget.attr("data-row", character.order + 1)
				.attr("data-col", col);
		}
		if(this.combat.isCurrent(character)) {
			characterWidget.addClass('current')
		} else {
			characterWidget.removeClass('current')
		}
		characterWidget.find(".character-data").each(function(){
			var attributeName = $(this).attr("attribute-name");
			var attributeType = $(this).attr("attribute-type");
			if($(this).hasClass("editable")) {
				if(character.editable) {
					$(this).attr("data-pk", character.id)
				} else {
					$(this).removeClass("editable")
				}
			}
			if(attributeType == 'boolean') {
				$(this).find("input[type=checkbox]").prop('checked', character[attributeName]);
			} else { 
				$(this).text(character[attributeName]).attr('title', character[attributeName]);
			}
			if (attributeType == 'life') {
				var lifeStatus = character.getLifeStatus();
				$(this).css('color', lifeStatus).css('background-color', lifeStatus)
			}
		});
	}
	
	Console.prototype.createCombat = function() {
		if(this.settings.systemType == 'Pathfinder') {
			this.combat = new PathfinderCombat(this.settings.combatId, this.settings.combatSystemData.turns, this.settings.combatSystemData.roundsPerTurn )
		} //Rest of the combat types
	}

	Console.prototype.buildUrl = function(path) {
		return this.settings.baseUrl + path.replace("{id}", this.combat.id); 
	}
	
}(jQuery));



