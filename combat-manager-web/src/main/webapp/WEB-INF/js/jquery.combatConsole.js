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
 * largePanel : Selector to get the dom element for the large panel
 * smallPanel : Selector to get the dom element for the small panel
 * playerPanel : Selector to get the dom element for the current player panel (Use only for Player Console)
 * dialogs : map with modal dialogs selectors to use.
 */
(function ($) {
	
	var combatConsole = new Console();
	
	var widgets = {
		'Pathfinder' : {
			'lg' : '<li character-id="{character-id}" class="combat-character"><div class="selected-char" style="width: 10px">&#160;</div><div class="character-name" ><span class="character-data" attribute-name="name"></span>&nbsp;-&nbsp;<span class="character-data" attribute-name="type"></span></div><div style="width:25px" attribute-name="hp" attribute-type="life" class="character-data hp editable" data-type="number" data-title="HP" data-pk="" data-url="/combats/character/currentHitPoints"></div><div class="hiddenSelect character-data editable" attribute-name="hidden" attribute-type="boolean" data-pk="" data-url="/combats/character/hidden">Hidden <input type="checkbox"/></div><div class="character-data conditions-and-effects" attribute-name="conditionsAndEffects" title=""></div>	</li>'
		}
	}
	
	var panels = {
		'Pathfinder' : {
			'lg' : [
				{ 'name' : 'Image', 'modes' : ['gm','player'], 'html' : '<div class="row"><div class="col-xs-12"><img class="character-data img-rounded img-responsive" attribute-name="image_url" attribute-type="Image" src="/img/no_pic_available.jpg"/></div></div>' } ,
				{ 'name' : 'Stats', 'modes' : ['gm'], 'html' : '<div class="character-data" attribute-name="stats" attribute-type="Stats"></div><div class="clearfix"></div>' } ,
				{ 'name' : 'Actions',  'modes' : ['gm'], 'children' : [
	               { 'name' : 'Conditions',  'modes' : ['gm'], 'items' : 'conditions' },
	               { 'name' : 'Magical Effects',  'modes' : ['gm'], 'items' : 'magicalEffects'  }
				]},
				{ 'name' : 'Actions',  'modes' : ['player'], 'children' : [
	               { 'name' : 'Conditions',  'modes' : ['player'], 'items' : 'conditions', 'readOnly' : true },
	               { 'name' : 'Magical Effects',  'modes' : ['player'], 'items' : 'magicalEffects', 'readOnly' : true  }
				]}
			],
			'sm' : [ { 'name' : 'Stats', 'modes' : ['gm','player'], 'html' : '' }],
			'player' : [ { 'name' : 'All', 'modes': ['player'], 'html' : '<div class="row"><div class="col-xs-3"><img class="player-data img-rounded" style="max-height:1px;max-width:100%" attribute-name="image_url" attribute-type="Image" src="/img/no_pic_available.jpg"/></div><div class="col-xs-9 player-data" attribute-name="stats" attribute-type="Stats"></div></div>' }]
		}
	}
	
	var controls = {
		'Pathfinder': { 
			'header' : '<div class="navbar navbar-inverse"><div><ul class="nav navbar-nav centered"><li><a href="#" class="previous" title="Previous"><i class="fa fa-step-backward"></i></a></li><li><a>Round <span class="combat-data" attribute-name="round">3</span></a></li><li><a>Turn <span class="combat-data" attribute-name="turn">5</span></a></li><li><a href="#contact" class="next" title="Next"><i class="fa fa-step-forward"></i></a></li></ul></div></div>',
			'footer' : '<div class="navbar navbar-inverse"><div><ul class="nav navbar-nav centered"><li><a href="#" class="add-character" title="Add Character"><span><i class="fa fa-user"></i><i class="fa fa-plus small"></i></span></a></li><li><a href="#" class="delete-character" title="Delete Selected Character"><span><i class="fa fa-user"></i><i class="fa fa-minus small"></i></span></a></li><li><li><a href="#" class="add-log" title="Add Log Entry"><span><i class="fa fa-comment"></i></i></span></a></li><li><li><a href="#" class="view-log" title="View Log"><span><i class="fa fa-comments"></i></i></span></a></li><li><a href="#" class="end-combat" title="End Combat"><i class="fa fa-stop"></i></a></li></ul></div></div>'
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
		
		combatConsole.createPanels();
		
		combatConsole.reload();

		$(window).resize(function() {
			combatConsole.resized();
		})
		
		$.fn.editable.defaults.ajaxOptions = {
    		    beforeSend: beforePost
    		};
    	$.fn.editable.defaults.showbuttons = false;
    	$.fn.editable.defaults.placement = "right";
    	
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
    	
    	
    	
    	$(this).on('click','.previous', function() {
    		combatConsole.previous();
    	})
    	
    	$(this).on('click','.next', function() {
    		combatConsole.next();
    	})
    	
    	$(this).on('click','.end-combat', function() {
    		combatConsole.endCombat();
    	})
    	
    	$(this).on('click','.add-character', function() {
    		combatConsole.addCharacter();
    	})
    	
    	$(this).on('click','.delete-character', function() {
    		combatConsole.deleteCharacter();
    	})
    	
    	$(this).on('click','.add-log', function() {
    		combatConsole.addLog();
    	})
    	
    	$(this).on('click','.view-log', function() {
    		combatConsole.viewLogs();
    	})
    	
    	
    	
    	$(this).editable({
    		selector: ".editable[attribute-type='life']",
    		success : function(response, newValue) {
    			$(this).css("color", response.hit_point_status);
    			$(this).css("background-color", response.hit_point_status);
    		},
    		inputclass : "narrow_input"
    	})
    	
    	$(this).on('click', '.combat-character', function() {
    		var characterId = $(this).attr("character-id");
    		combatConsole.select(characterId);
    	});
    	
    	$(".panel").on('click', '.item a input', function(e) {
    		
    		var characterId = $(".combat-character.selected").attr("character-id")
    		combatConsole.updateCharacterItem(characterId, $(this).attr("item-type"), $(this).attr("item-id") , $(this).prop("checked"));
    		
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
	
	Console.prototype.endCombat = function() {
		var confirmDialog = $(this.settings.dialogs.confirm);
		confirmDialog.find(".modal-title").empty().append("End Combat?")
		confirmDialog.find(".modal-body p").empty().append("Confirm combat end. After ending the combat, you wont be able to start it again.")
		confirmDialog.find(".confirm").unbind( "click" ).click(function(){
			var url = combatConsole.buildUrl("combats/{id}/end");
			window.location = url;
		});
		confirmDialog.modal();
	}
	
	Console.prototype.deleteCharacter = function() {
		var selectedCharacter = $(".combat-character.selected");
		if(selectedCharacter.length == 1) {
			var confirmDialog = $(this.settings.dialogs.confirm);
			confirmDialog.find(".modal-title").empty().append("Remove Character")
			confirmDialog.find(".modal-body p").empty().append("Remove character from combat.")
			confirmDialog.find(".confirm").unbind( "click" ).click(function(){
				var url = combatConsole.buildUrl("combats/{id}/character/{characterId}/delete").replace("{characterId}", selectedCharacter.attr("character-id"));
				$.ajax({
		    		url: url,
		    		dataType: "json",
		    		type: "POST",
		    		beforeSend : beforePost,
		    		success : function(combatStatus) {
		    			combatConsole.updateCombat(combatStatus);
		    			confirmDialog.modal('hide')
		    		}
		    	})
			});
			confirmDialog.modal();	
		}
	}
	
	Console.prototype.addCharacter = function() {
		var addCharacterDialog = $(this.settings.dialogs.addCharacter);
		addCharacterDialog.find(".confirm").unbind( "click" ).click(function(){
			var id = addCharacterDialog.find(".remaining-characters option:selected").val();
			if(id == 'new') {
				window.location = combatConsole.buildUrl("combats/{id}/character/new");
			} else {
				var url = combatConsole.buildUrl("combats/{id}/character/{characterId}/add").replace("{characterId}", id);
				$.ajax({
		    		url: url,
		    		dataType: "json",
		    		type: "POST",
		    		beforeSend : beforePost,
		    		success : function(combatStatus) {
		    			combatConsole.updateCombat(combatStatus);
		    			addCharacterDialog.modal('hide')
		    		}
		    	})
			}
		});
		var url = this.buildUrl("combats/{id}/characters/remaining");
		$.ajax({
    		url: url,
    		dataType: "json",
    		type: "GET",
    		success : function(characters) {
    			var characterSelect = addCharacterDialog.find(".remaining-characters")
    			characterSelect.empty();
    			for(var i=0; i < characters.length ; i++) {
    				var character = characters[i];
    				$("<option>").attr("value",character.id).append(character.character.name).appendTo(characterSelect)
    			}
    			$("<option>").attr("value","new").append("Create new Character").appendTo(characterSelect);
    			addCharacterDialog.modal();
    		}
    	})
	}
	
	Console.prototype.addLog = function() {
		var addLogDialog = $(this.settings.dialogs.addLog)
		addLogDialog.find(".confirm").unbind('click').click(function(){
			var data = { logEntry : addLogDialog.find('.log-entry').val() };
			var url = combatConsole.buildUrl("combats/{id}/logs");
			$.ajax({
	    		url: url,
	    		dataType: "json",
	    		type: "POST",
	    		data : JSON.stringify(data),
	    		contentType: "application/json",
	    		beforeSend : beforePost,
	    		success : function(result) {
	    			addLogDialog.modal('hide')
	    		}
	    	})
		})
		addLogDialog.modal();
	}
	
	Console.prototype.viewLogs = function() {
		var viewLogsDialog = $(this.settings.dialogs.viewLogs);
		var url = combatConsole.buildUrl("combats/{id}/logs");
		$.ajax({
			url : url,
			dataType : "json",
			type : "GET",
			success : function(logEntries) {
				viewLogsDialog.find(".modal-body").empty();
				for(var i=0; i< logEntries.length; i++) {
					var logEntry = logEntries[i];
					var panelDivEL = $("<div/>").addClass('panel').addClass('panel-default').prependTo(viewLogsDialog.find(".modal-body"));
					var panelHeadingEL = $("<div/>").addClass('panel-heading').appendTo(panelDivEL)
					var panelBodyEL = $("<div/>").addClass('panel-body').appendTo(panelDivEL)
					$("<h3/>").addClass("panel-title").append(logEntry.combat_context_description).appendTo(panelHeadingEL)
					panelBodyEL.append(logEntry.log)
				}
			}
		})
		viewLogsDialog.modal();
	}
	
	Console.prototype.updateCharacterItem = function(characterId, itemType, itemId, value) {
		var url = combatConsole.buildUrl("combats/character/" + itemType + "/{action}")
		var data = { pk : characterId, itemId : itemId }
		if(value) {
			url = url.replace("{action}","ADD")
		} else {
			url = url.replace("{action}","REMOVE")
		}
		$.ajax({
			url : url,
			dataType : "json",
			type : "POST",
			data: data,
			beforeSend : beforePost,
			success : function (combatCharacter) {
				combatConsole.updateCombatCharacter(combatCharacter) 
			}
		})
	}
	
	Console.prototype.updateCombatCharacter = function(combatCharacter) {
		var character = this.combat.updateCharacter(combatCharacter);
		var characterWidget = $(".combat-character[character-id='"+ character.id +"']")
		this.updateWidgetData(characterWidget, character)
	}

	Console.prototype.select = function(characterId) {
		var url = this.buildUrl("combats/characters/${characterId}").replace("${characterId}", characterId);
		$.ajax({
    		url: url,
    		dataType: "json",
    		type: "GET",
    		success : function(characterData) {
    			$(".combat-character").removeClass("selected")
    			$(".combat-character[character-id='" + characterId +"']").addClass("selected")
    			//TODO: Populatemagical effects
    			$(".panel .character-data").each(function() {
    				combatConsole.updateDataItem($(this), characterData);
    			})
    		}
    	})
	}
	
	
	
	Console.prototype.createPanels = function() { 
		var panelDefinitions = panels[this.settings.systemType];
		this.createPanel($(this.settings.smallPanel), panelDefinitions['sm'])
		this.createPanel($(this.settings.largePanel), panelDefinitions['lg'])
		this.createPlayerPanel($(this.settings.playerPanel), panelDefinitions['player'])
	}
	
	Console.prototype.createPlayerPanel = function(el, panelDefinition) {
		var url = this.buildUrl("combats/{id}/playerCharacters")
		$.ajax({
    		url: url,
    		dataType: "json",
    		type: "GET",
    		success : function(charactersData) {
    			var containerEL = $("<div/>").addClass("row").appendTo(el)
    			var tabsEL = $("<ul/>").addClass("col-xs-3").addClass("nav").addClass("nav-pills").addClass("nav-stacked").css("padding-left","15px").attr("role","tablist").appendTo(containerEL);
    			var tabContentEL = $("<div/>").addClass("col-xs-9").addClass("tab-content").appendTo(containerEL)
    			var maxHeight = Math.max(40 * charactersData.length + 2 * (charactersData.length-1), 82);
    			for(var i = 0; i < charactersData.length; i++){
    				var characterData = charactersData[i];
    				var partTabEL = $("<li/>").appendTo(tabsEL);
    				var partLinkEL = $("<a/>").addClass("overflown").append(characterData.name).appendTo(partTabEL)
    				var id = characterData.name.replace(/\W/g, '')
    				partLinkEL.attr("href", "#" + id).attr("role","tab").attr("data-togle",  "tab")
    				partLinkEL.click(function (e) {
					  e.preventDefault()
					  $(this).tab('show')
					})
    				var characterRowEL = $(panelDefinition[0].html).addClass("tab-pane").attr("id", id).appendTo(tabContentEL)
    				characterRowEL.find(".player-data").each(function() {
    					combatConsole.updateDataItem($(this), characterData);
    					if($(this).css("max-height") == "1px") {
    						$(this).css("max-height", maxHeight + "px")
    					}
    				})
    			}
    			tabsEL.tab();
    			tabsEL.find("li:first-child a").click();
    		}
    	})
	}
	
	Console.prototype.createPanel = function(el, panelDefinition) {
		if(panelDefinition.length > 1) {
			//Create tabs
			var tabsEL = $("<ul/>").addClass("nav").addClass("nav-tabs").attr("role","tablist").appendTo(el);
			var tabContentEL = $("<div/>").addClass("tab-content").appendTo(el)
			for(var i = 0; i < panelDefinition.length; i++) {
				var partDefiniiton = panelDefinition[i]
				this.appendTab(tabsEL, tabContentEL, partDefiniiton)
			}
			tabsEL.tabdrop({'text' : 'More'})
			tabsEL.children("li:not(.dropdown)").first().find("a").click();
		} else {
			//Simply put data there.
			this.appendData(el, panelDefinition[0])
		}
	}
	
	Console.prototype.appendTab = function(tabsEL, tabContentEL, partDefinition, active) {
		if( partDefinition.modes.indexOf(this.settings.mode) >= 0 ) {
			var tabId = partDefinition.name.replace(/ /g, "");
			if(partDefinition.children && partDefinition.children.length > 0) {
				for(var i = 0; i < partDefinition.children.length; i++) {
					var innerPartDefinition = partDefinition.children[i];
					this.appendTab(tabsEL, tabContentEL, innerPartDefinition);
				}
			} else {
				var partTabEL = $("<li/>").appendTo(tabsEL);
				var partLinkEL = $("<a/>").append(partDefinition.name).appendTo(partTabEL)
				partLinkEL.attr("href", "#" + tabId).attr("role","tab").attr("data-togle",  "tab")
				partLinkEL.click(function (e) {
				  e.preventDefault()
				  $(this).tab('show')
				})
			}
			if(partDefinition.html || partDefinition.items) {
				var partContentEL = $("<div/>").addClass("tab-pane").addClass("fade").attr("id", tabId).appendTo(tabContentEL)
				this.appendData(partContentEL, partDefinition)
			}
			
		}
	} 
	
	Console.prototype.appendData = function(el, partDefinition) {
		if( partDefinition.modes.indexOf(this.settings.mode) >= 0 ) {
			if(partDefinition.html) {
				el.append($(partDefinition.html))	
			} else if(partDefinition.items) {
				var url = this.buildUrl("combats/{id}/items/" + partDefinition.items);
				$.ajax({
		    		url: url,
		    		dataType: "json",
		    		type: "GET",
		    		success : function(items) {
		    			var scrollDivEL = $("<div/>").addClass("scroll_list_400").addClass("nice_list")
		    				.addClass("character-data").attr("attribute-name","current_items_map").attr("attribute-type","Items").appendTo(el);
		    			var scrollUlEL = $("<ul/>").appendTo(scrollDivEL);
		    			for(var i=0; i< items.length; i++) {
		    				var item = items[i];
		    				var itemLiEL = $("<li/>").addClass("item").appendTo(scrollUlEL);
		    				var itemLinkEl = $("<a/>").appendTo(itemLiEL);
		    				
		    				if(partDefinition.readOnly) {
		    					itemLiEL.addClass("hidden").addClass("hideable").attr("item-id",item.id).attr("item-type",partDefinition.items)
		    				}else {
			    				$("<input/>").attr("type","checkbox").addClass("pull-right").css("margin-right","10px")
		    						.attr("item-type",partDefinition.items).attr("item-id",item.id).appendTo(itemLinkEl);
		    				}
		    				$("<label/>").addClass("overflown").append(item.label).appendTo(itemLinkEl);
		    			}
		    		}
				})
			}
		}
	}
	
	Console.prototype.previous = function() {
		var url = this.buildUrl("combats/{id}/character/previous");
		$.ajax({
			url: url,
			beforeSend : beforePost,
			type: "POST",
			dataType : "json",
			success : function(combatStatus) {
				combatConsole.updateCombat(combatStatus);
			}
		})
	}
	
	Console.prototype.next = function() {
		var url = this.buildUrl("combats/{id}/character/next");
		$.ajax({
			url: url,
			beforeSend : beforePost,
			type: "POST",
			dataType : "json",
			success : function(combatStatus) {
				combatConsole.updateCombat(combatStatus);
			}
		})
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
		
		this.useGridster = true;
		
		if(this.useGridster) {
			
			var controlsDIV = $(controls[this.settings.systemType]['header']).appendTo(this.el)
			
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
			
			var controlsDIV = $(controls[this.settings.systemType]['footer']).appendTo(this.el)
			
			if(this.settings.mode == 'player') {
				this.gridster.disable();
				$('.previous,.next,.end-combat,.delete-character,.add-character,.add-log,.view-log').css("display", "none");
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
		this.el.find(".combat-data").each(function() {
			var attributeName = $(this).attr("attribute-name");
			$(this).text(combatConsole.combat[attributeName])
		})
		//Now change all data, including creating the widgets and stuff
		$(".combat-character").each(function(){
			var characterId = $(this).attr("character-id");
			if(!combatConsole.combat.findCharacter(characterId)) {
				combatConsole.gridster.remove_widget($(this));
			}
		})
		for(var i = 0; i < this.combat.characters.length; i++) {
			character = this.combat.characters[i];
			characterWidget = $(".combat-character[character-id='"+ character.id +"']")
			if(characterWidget.length == 0 && (this.settings.mode == 'gm' || !character.hidden )) {
				//Create widget
				var widgetText = widgets[this.settings.systemType][this.size].replace("{character-id}", character.id);
				var col = actions[this.settings.systemType].indexOf(character.currentAction) + 1;
				if(this.useGridster) {
					characterWidget = this.gridster.add_widget(widgetText, 3, 1, col, character.order + 1)	
				}
			}
			this.updateWidgetData(characterWidget, character);
//			TODO: Is not the same data type
//			if(characterWidget.hasClass("selected")) {
//				$(".panel .character-data").each(function() {
//					combatConsole.updateDataItem($(this), character);
//				})
//			}
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
			combatConsole.updateDataItem($(this), character)
		});
	}
	
	Console.prototype.updateDataItem = function(item, data) {
		var attributeName = item.attr("attribute-name");
		var attributeType = item.attr("attribute-type");
		if(item.hasClass("editable")) {
			if(data.editable) {
				item.attr("data-pk", data.id)
			} else {
				item.removeClass("editable")
			}
		}
		if(attributeType == 'boolean') {
			item.find("input[type=checkbox]").prop('checked', data[attributeName]);
		} else if (attributeType == 'Image' ) {
			if(data[attributeName]) {
				item.attr("src", data[attributeName]);
			} else {
				item.attr("src", "/img/no_pic_available.jpg");
			}
		} else if (attributeType == 'Stats') {
			var statWidgetContainer = item.find(".stat-widget-container");
			var createWidgets = false;
			if(statWidgetContainer.size() == 0) {
				statWidgetContainer = $("<div/>").addClass("stat-widget-container").appendTo(item);
				createWidgets = true;
			}
			for(var i = 0; i < data[attributeName].length; i++) {
				var statGroup = data[attributeName][i];
				var groupDivEL;
				if(createWidgets) {
					groupDivEL = $("<div>").addClass("row").addClass("pull-left").css("width","120px").appendTo(statWidgetContainer)
				}
				for(key in statGroup) {
					var value = statGroup[key]
					var statWidget = item.find(".stat-widget[stat='" + key + "']")
					if(statWidget.size() == 0) {
						$("<span/>").addClass("control-label").addClass("col-xs-6").append(key).appendTo(groupDivEL)
						var statDivEL = $("<span/>").addClass("col-xs-6").appendTo(groupDivEL)
						$("<strong/>").addClass("stat-widget").attr("stat",key).append(value).appendTo(statDivEL)
					}
					statWidget.empty().append(value);
				}
			}
		} else if (attributeType == "Items") {
			var items = data[attributeName];
			item.find("input[type='checkbox']").each(function(){
				var itemType = $(this).attr("item-type");
				var itemId = parseInt($(this).attr("item-id"));
				if(items[itemType] && items[itemType].indexOf(itemId) >= 0) {
					$(this).prop("checked", true);
				} else {
					$(this).prop("checked", false);
				}
			});
			item.find(".hideable").each(function() {
				var itemType = $(this).attr("item-type");
				var itemId = parseInt($(this).attr("item-id"));
				if(items[itemType] && items[itemType].indexOf(itemId) >= 0) {
					$(this).removeClass("hidden");
				} else {
					$(this).addClass("hidden");
				}
			});
		} else { 
			item.text(data[attributeName]);
			
			if (attributeType == 'life') {
				var lifeStatus = data.getLifeStatus();
				item.css('color', lifeStatus).css('background-color', lifeStatus)
			} else {
				item.attr('title', data[attributeName]);
			}
			
		}
		
		if(attributeName == 'hidden') {
			if(combatConsole.settings.mode != 'gm' || data.type == 'PC') {
				item.css('display', 'none');
			}
		}
		
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



