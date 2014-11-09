/**
 * Apply iconToggle on inputs of type checkbox.
 * iconToggle relies on font awesome classes to display icons.
 * 
 * @param 
 * trueIcon - Icon to display when the checkbox is checked.
 * falseIcon - Icon to display when the checkbox is unchecked.
 * description - Description of the action that is performed by this checkbox.
 * 
 */
(function ($) {
	
	$.fn.iconToggle = function (options) {
		var settings = $.extend({
			trueIcon : 'fa-check',
			falseIcon : 'fa-close',
			trueDescription : 'true',
			falseDescription : 'false',
			description : 'Change value'
        }, options );
		
		var iconToggle = new IconToggle($(this), settings);
		
		$(this).data("iconToggle", iconToggle);
		
		iconToggle.create();
		return $(this);
	}
	
	function IconToggle(el, settings) {
		this.el = el;
		this.settings = settings;
		this.changeFunctions = [];
	}
	
	IconToggle.prototype.create = function() {
		var iconToggle = this;
		var container = $("<div>").addClass("icon-toggle-container");
		this.value = this.el.prop("checked");
		var icon = $("<i/>").addClass("fa").appendTo(container);
		this.el.wrap(container)
		this.el.hide();
		//Look for icon again as it changed when inserted
		this.icon = this.el.parents(".icon-toggle-container").find("i")
		this.setIcon();
		this.icon.click(function() {
			iconToggle.toggleValue(true);
		})
	}
	
	IconToggle.prototype.setValue = function(value) {
		if(value != this.value) this.toggleValue(false);
	}
	
	IconToggle.prototype.getValue = function() {
		return this.value;
	}
	
	IconToggle.prototype.toggleValue = function(propagate) {
		this.value = !this.value;
		this.setIcon();
		this.el.prop("checked", this.value);
		if(propagate) {
			for(var i = 0 ; i < this.changeFunctions.length ; i++) {
				this.changeFunctions[i](this.el, this.value);
			}	
		}
	}
	
	IconToggle.prototype.setIcon = function() {
		if(this.value) {
			this.icon.removeClass(this.settings.falseIcon).addClass(this.settings.trueIcon).attr("title", this.settings.description + " (" + this.settings.trueDescription + ")");
		} else {
			this.icon.addClass(this.settings.falseIcon).removeClass(this.settings.trueIcon).attr("title", this.settings.description + " (" + this.settings.falseDescription + ")");
		}
	}
	
	IconToggle.prototype.change = function(changeFunction) {
		this.changeFunctions.push(changeFunction);
	} 
}(jQuery));