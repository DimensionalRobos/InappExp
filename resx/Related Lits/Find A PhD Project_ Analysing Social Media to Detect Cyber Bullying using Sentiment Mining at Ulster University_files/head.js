$(document).ready(function () {
    if ($(document).pngFix) $(document).pngFix();
    set_realmCentral();
    mainBodyHeightAdjust();
    postLoad();
    bubbles.init();
    anchorScroll.init();
    var t = setTimeout("landfillManager()",400);
});

$(window).bind('orientationchange resize', function (event) { set_realmCentral(); });

$(window).resize(function () {
    landfillManager();
});

function postLoad() { var t = setTimeout("fadeInByClass($('.preLoad'));fadeInByClass($('.archipelago_west'));fadeInByClass($('.archipelago_east'));$('body').css('min-width', '1024px');", 10); }
    
function fadeInByClass(obj) { obj.hide().css('visibility', 'visible').animate({ 'opacity': 'show' }, 300); }

// if west achipelego is taller then body then increase main#div height
function mainBodyHeightAdjust() { var t = setTimeout("setCentralToRealmHeight()", 500) }

// if make the div#main height match the realm height (i.e. if anything taller, then adjust the main page min-height
function setCentralToRealmHeight() {
    var realmHeight = $('#realm_central').outerHeight();
    var centralHeight = $('.archipelago_central').outerHeight();
    var centralMainHeight = $('#main').outerHeight();
    var centralbreadCrumbHeight = $('#breadCrumb').outerHeight();

    if (realmHeight > centralHeight) $('#main').css('min-height', realmHeight - (centralHeight - centralMainHeight + centralbreadCrumbHeight));
}

$(function () { $(".button").button(); });

$(function () {
    // IE7 Support for arrows on clb button links
    $('html.ie7 a.clb').append('<span>►</span>');
});

function set_realmCentral() {
    var winWidth = $(window).width();
    var winHeight = $(window).height();

    if (winWidth > 1280) winWidth = 1280;
    if (winWidth < 1024) winWidth = 1024;
    var adjustment = winWidth - 350;

    // set central width and add preLoad class for smooth appearance
    $('.archipelago_central').css("width", (adjustment)).addClass("preLoad");
}

// Make all div's with .landfill class equal in height
function landfillManager() {
    $('.landfillOn').each(function () {
        var highest = 0; 					// To be used to set all .landfill objects to the same height
        var lf = $(this).find('.landfill');	// get .landfill objects in this row
        lf.height('auto'); 					// reset .landfill height to auto

        // loop through all .landfill's to find the highest in this row
        lf.each(function () {
            var padding = removePX($(this), "padding-top") + removePX($(this), "padding-bottom");
            if ($(this).outerHeight() - padding > highest) highest = $(this).outerHeight() - padding;
        });
            
        lf.height(highest); 				// Set all .landfill objects to the highest height 
    });
}

function sitewide_adverts_switcheroo() {
    $('.sitewide-advert').each(function () {
        // call the relevant UC function based on its data-type
        eval($(this).attr("data-type"))($(this));
    });
}

function getJsonData_AndRandomize(obj) {
    var jsonData = JSON.parse(obj.siblings('.jsonData').attr("data-ads"));    // parse data in to JSON
    var jsonSize = jsonData.length;                                 // get number of results
    var ad = $.map(jsonData, function (el) { return el; });         // maps json to an array
    ad.sort(function () { return .5 - Math.random(); });            // randomize data order
    return ad;
}

// dynamic form post function i.e post('/contact/', {name: 'Pete Smith'});
function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for (var key in params) {
        if (params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}

// removes the "px" part from any css property
function removePX(obj, css) {
    return parseInt(obj.css(css));
}
