var siteMenu = new function () {
    this.trigger = 0;
    this.cSubMenu;
    this.activeMenuItem = -1;
    this.t; // sub menu timer
    this.tt; // root menu button timer
    this.isMobile = false;
    this.tta; // root menu button timer action
    this.originalMenuItemStyles = ""; // styles for the main menu item before zooming changes
    this.originalMenuItemFontSize = ""; // font size for the main menu items anchor before zooming changes
    this.minorSelectText = "";

    this.init = function () {
        this.removeSubMenuArrows();
        this.ieAdjuster();
        this.adjustLastMenuItem();
        this.makeMainMenuItemsClickable();
        this.hoverCode();
        this.setSubMenus();
        this.allSubMenusVisible();
        this.highlightMainMenuItem();
        this.mobileTweaks();
        this.sectionMenu_ViewAll_ClickEvent();
        this.positionMoreMenu();
        this.hashInit();
        this.make_Level1_Full_Items_Active();
    };

    // IE7 Support for arrows on menu links
    this.ieAdjuster = function () {
        $('html.ie7 #siteMenu .hasMenu').append('<span>▼</span>');
    };

    /* Detects mobile user agent and applies tweaks */
    this.mobileTweaks = function () {
        if (/Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent)) { this.isMobile = true; }

        if (this.isMobile) {
            $('.hasMenu').each(function () {
                if ($(this).next().attr("class") == "subMenu visible") {
                    $('.menuItem').unbind('mouseenter mouseleave');
                    $(this).attr("href", "javascript:void(0);");
                    $(this).parent().click(function () {
                        siteMenu.mobileTweaks.toggleMenu($(this).children(0));
                    });
                }
            });
        }
    };

    this.mobileTweaks.toggleMenu = function (obj) {
        ///<summary>Turns .subMenu off/on (Used by MobileTweak())
        ///for touchscreens/pads
        ///</summary>
        ///<param name="obj" type="object">
        ///An object
        ///<para>containing a main menu item element</para>
        ///</param>
        
        $('.menuItemLink').parent().removeClass("menuItemLinkHover"); // Remove hover colour

        obj = $(obj);
        if (obj.next().is(':visible')) {
            siteMenu.hideMenu(obj.parent());
        } else {
            $('.subMenu').hide();
            siteMenu.showMenu(obj.parent());
        }
    };

    /* Find the current main menu item in use */
    this.highlightMainMenuItem = function () {

        
        $('.menuItemLink').each(function () {

            // check for exact url match
            if ($(this).attr("href").toLowerCase() == document.URL.toLowerCase()) {
                // if primaryLink on this menu item detected then highlight its closest root item
                if ($(this).hasClass('primaryLink')) {
                    $(this).closest(".menuItem").addClass("menuItemLinkActive d1");
                }
            }
        });

        // if primaryLink found in a sibling then highlight main menu item
        if ($('.menuItemLinkActive').length == 0) {
            $('.menuItemLink').each(function () {
                // check for exact url match
                if ($(this).attr("href").toLowerCase() == document.URL.toLowerCase()) {
                    if ($(this).closest(".menuItem").find('.primaryLink').length == 0 &! $(this).hasClass('shortcut'))
                        $(this).closest(".menuItem").not('.flags').addClass("menuItemLinkActive d2");
                }
            });
        }

        // if primaryLink found but none made any root items active then try again without the primaryLink check (eg. openday link on masters will not highlight if this is removed)
        if ($('.menuItemLinkActive').length == 0) {
            $('.menuItemLink').each(function () {
                // check for exact url match
                if ($(this).attr("href").toLowerCase() == document.URL.toLowerCase() &! $(this).hasClass('shortcut')) {
                    if ($('.menuItemLinkActive').length == 0) $(this).closest(".menuItem").not('.flags').addClass("menuItemLinkActive d3");
                }
            });
        }

        
        // if not found then, check for contains without querystring
        if ($('.menuItemLinkActive').length == 0 && document.URL.indexOf("?")>-1) {
            $('.menuItemLink').each(function () {
                if ($('.menuItemLinkActive').length == 0 && $(this).attr("href").toLowerCase().indexOf(document.URL.substr(0, document.URL.indexOf("?")).toLowerCase()) != -1 &! $(this).hasClass('shortcut')) {
                    $(this).closest(".menuItem").not('.flags').addClass("menuItemLinkActive d4");
                }
            });
        }

        // if still not found then use the breadcrumbs rootNode to find the menu item
        if ($('.menuItemLinkActive').length == 0) {
            $('.menuItemLink').each(function () {
                
                if ($('#siteMenuRootNode span').html() == $(this).html() && $('.menuItemLinkActive').length == 0 &! $(this).hasClass('shortcut')) {
                    $(this).closest(".menuItem").not('.flags').addClass("menuItemLinkActive d5");
                }
            });
        }

        // If no exact match from the siteMenu xml, mark up the first heading
        if ($('#level1_Major .active').length == 0) {
            $('#level1_Major a').each(function (index) {
                // if no active section menu item highlighted and not a section index page then highlight the first entry (helps with keywords search pages)
                if ($('#level1_Major .active').length == 0 && $('.sectionIndex').length == 0) $(this).parent().addClass("active"); 
            });
        }

    };

    /* Changes All .subMenu items to be visibility:visible */
    this.allSubMenusVisible = function () {
        $('.subMenu').addClass("visible");
    };

    // Adjusts the last-child menuItem to fit the .mainHoverMenu
    this.adjustLastMenuItem = function () {
        
        // Record the origianl styles for zoom alterable items (only once)
        if (this.originalMenuItemStyles == "") {
            this.originalMenuItemStyles = $('.menuItem').attr("style");
            this.originalMenuItemFontSize = $('#siteMenu>ul>li.menuItem>a').css("font-size");
        }
        
        //reset padding/font as they may have been adjusted for zooming
        $('.menuItem').attr("style", this.originalMenuItemStyles);
        $('#siteMenu>ul>li.menuItem>a').css("font-size", this.originalMenuItemFontSize);

        var last_li = $('.menuItem:last').prev("li");
        var cWidth = 0;
        var hMWidth = last_li.parent().parent().outerWidth();
        var flagWidth = $('.country_selector') ? $('.country_selector').outerWidth() : 0; // if flags exist then set flagWidth for later use
        $('.menuItem').each(function (index) {
            if ((index + 1) < $('.menuItem').length) cWidth += $(this).outerWidth() + flagWidth;
        });

        // if not many menus i.e. not much widths then do not adjust last child menu item
        if (cWidth > 799) {
            
            // removed this line, not sure it is still required and was causing problems with PhD site menu (ps)
            //last_li.width(hMWidth - cWidth - 20); // entire menu width minus all items other than last menu item width minus some padding

            this.keepFlagWidthConstant();

            // keep reducing width by 1px until "about" fits on menu bar
            for (i = 0; i < 100; i++) {
                this.keepFlagWidthConstant();
                if ($('.mainHoverMenu').height() < 40) break;
                if (last_li.width() > 65) last_li.width(last_li.width() - 1);
            }

            // if still not on one line then adjust the menuItem padding
            for (i = 0; i < 100; i++) {
                this.keepFlagWidthConstant();
                if ($('.mainHoverMenu').height() < 40) break;
                var pixels = (24 - i);
                if (pixels > 2) {
                    $('.menuItem').css("padding", "0 " + pixels + "px");
                }
                
            }

            // if still not on one line then adjust the root menuItem <a> font-size
            for (i = 0; i < 100; i++) {
                if ($('.mainHoverMenu').height() < 40) break;
                $('#siteMenu>ul>li.menuItem>a').css("font-size", (12 - i) + "px");
                
            }
        }
    };

    this.keepFlagWidthConstant = function () {
        // never change the language selector padding
        $('.country_selector').parent().css("padding", "0 10px");
        $('.country_selector').parent().find('li').css("padding", "0px");
    },
    
    this.showMenu = function (obj) {
        ///<summary>Displays the associated .subMenu</summary>
        ///<param name="obj" type="object">
        ///An object
        ///<para>containing a main menu item element</para>
        ///</param>
        obj.addClass("menuItemLinkHover"); // Add hoverover colour

        siteMenu.positionSubMenus(); // check the positions again as some browsers load a little slow.

        // if moving from menuItemRootNode to another menuItemRootNode then kill all subMenus
        if (siteMenu.trigger == 1 && siteMenu.activeMenuItem != obj.index()) $('.subMenu').hide();

        siteMenu.activeMenuItem = obj.index(); // set active menu Index number
        siteMenu.trigger = 0;

        // show subMenu
        tta = obj; // set the object for the timer
        siteMenu.tt = setTimeout(function () { tta.children('.subMenu').show(); }, 250); // set root menu button delay timer
    };

    this.hideMenu = function (obj) {
        ///<summary>Hides the associated .subMenu</summary>
        ///<param name="obj" type="object">
        ///An object
        ///<para>containing a main menu item element</para>
        ///</param>
        obj.removeClass("menuItemLinkHover"); //remove hoverover colour
        siteMenu.cSubMenu = obj.children('.subMenu');
        if (siteMenu.trigger == 0) { siteMenu.cSubMenu.hide(); }
        clearTimeout(siteMenu.tt); // clear root menu button timer 
    };

    // Adds hover code for .menuItem's
    this.hoverCode = function () {
        $('.menuItem').hover(
            function () { siteMenu.showMenu($(this)); },
            function () { siteMenu.hideMenu($(this)); }
        );

        $('.subMenu').hover(
        function () {
            var s = setTimeout(function () { siteMenu.trigger = 1; clearTimeout(siteMenu.t); }, 1);
        }
        , function () {
                        siteMenu.t = setTimeout(function () {
                            siteMenu.cSubMenu.hide();
                            siteMenu.trigger = 0;
                        }, 200);
        }
        );

        $('#innerMenu a').each(function() {
            var link = $(this).attr("href");
            $(this).parent().click(function () {
                location.href = link;
            });
        });

        $('#innerSubMenu a').each(function () {
            var link = $(this).attr("href");
            //$(this).parent().unbind("click");
            //$(this).parent().click(function () {
            //    location.href = link;
            //});
        });
    };

    // Initializes the subMenus
    this.setSubMenus = function () {
        this.wrapSubMenuItems();
        $('.subMenu').hide();
        this.positionSubMenus();
    };

    // Removes menu arrows for menuitems without a subMenu
    this.removeSubMenuArrows = function () {
        $('.menuItemLink').each(function () {
            if ($(this).next().attr("class") != "subMenu") $(this).removeClass("hasMenu")
        });
    };

    // Makes the main menu item clickable, not just the anchor element
    this.makeMainMenuItemsClickable = function () {
        //$('.menuItem').each(function () {
        //    $(this).click(function () {
        //        document.location.href = $(this).children(0).attr("href");
        //    });
        //});
    };

    // Wraps subMenus with a wrapper and splits content into columns and then removes the wrapper
    this.wrapSubMenuItems = function () {
        $('.subMenu').each(function (smIndex) {
            var column2Init = 0;
            var column3Init = 0;

            $(this).wrapInner("<div class='subMenuWrapper' />");
            var smHeight = $(this).outerHeight() - 24;
            var subMenuWrapperItems = $('.subMenuWrapper li');

            subMenuWrapperItems.each(function (index) {
                var smiPos = $(this).position().top;
                var currentSubMenu = $(this).parent().parent();
                var col1 = currentSubMenu.children('.col1');
                var col2 = currentSubMenu.children('.col2')
                var col3 = currentSubMenu.children('.col3');

                // create col1 and populate
                if (smiPos < smHeight) {
                    if (index == 0) currentSubMenu.append("<div class='col1' />");
                    currentSubMenu.children('.col1').append($(this).clone());
                }

                // create col2 and populate
                if (smiPos >= smHeight && smiPos < (smHeight * 2)) {
                    if (column2Init == 0) { currentSubMenu.append("<div class='col2' style='left:" + parseInt(col1.outerWidth()) + "px;' />"); column2Init = 1; }
                    $(this).parent().parent().children('.col2').append($(this).clone());
                }

                // create col3 and populate
                if (smiPos >= (smHeight * 2)) {
                    if (column3Init == 0) { currentSubMenu.append("<div class='col3' style='left:" + parseInt(col1.outerWidth() + col2.outerWidth()) + "px;' />"); column3Init = 1; }
                    currentSubMenu.children('.col3').append($(this).clone());
                }

                // set subMenu width to the total of all three columns (min-width present too)
                if (index == subMenuWrapperItems.length - 1 &! currentSubMenu.prev().hasClass("country_selector")) currentSubMenu.width(parseInt(col1.outerWidth() + col2.outerWidth() + col3.outerWidth()) + 'px');

            });

            // remove the original subMenu
            $('.subMenuWrapper').remove();

        });
    };

    // Positions subMenus to either left or right sides of the screen depending on their parents closest side
    this.positionSubMenus = function () {
        var hmPosLeft = $('.mainHoverMenu').position().left;
        var hmPosTop = $('.mainHoverMenu').position().top;
        var hmPosHeight = $('.mainHoverMenu').outerHeight();

        $('.subMenu').each(function (smIndex) {
            var currentMenuItemPos = $(this).parent().position().left; // get the position of the current Main Menu Item
            var currentMenuItemWidth = $(this).parent().width() / 2; // get the width/2 of the current Main Menu Item
            var hoverMenuHalfwayPoint = hmPosLeft + ($('#siteMenu').width() / 2); // get the position half way across the #hoverMenu
            
            // if menuItem button is over halfway across the main menu then make its popup appear right aligned not left
            if ((currentMenuItemPos + currentMenuItemWidth) > hoverMenuHalfwayPoint) {
                var lastMenuItemPosRight = $('.menuItem:last').position().left + $('.menuItem:last').outerWidth(); // find the last .menuItem and use it for the right alignment
                $(this).css("left", lastMenuItemPosRight - $(this).width() - 4); // from the end of the main menu, minus the current .subMenu width to get the left position
            } else {
                $('.subMenu').css("left", hmPosLeft).css("top", hmPosTop + hmPosHeight);
            }
        });
    };

    // Add click to the "view all" button in the section Menu
    this.sectionMenu_ViewAll_ClickEvent = function () {
        $('#level1_ViewAll').click(function () {
            $(this).prev().animate({ height: "hide" }, 200, function () { $('#level1_All').animate({ height: "show" }, 300); });
            $(this).animate({ height: "hide" }, 100);
        });
    };

    // Positions the more menu under the more button
    this.positionMoreMenu = function () {
        if (siteMenu.minorSelectText == "") siteMenu.minorSelectText = $('#level2_More').html();
        $('#level2_More').click(function () {
            //var lpos = $(this).position().left - $('#more_Container').outerWidth() + $(this).outerWidth() + 1; // position menu to left 
            var lpos = $(this).position().left; // position menu to right
            var tpos = $(this).position().top + $(this).outerHeight() + 5;
            $('#more_Container').css('left', lpos).css('top', tpos).animate({ height: "toggle" }, 1);
            siteMenu.moreBtn_Toggle($(this), "toggle");
        });
    };

    // Toggles the more menu
    this.moreBtn_Toggle = function (obj, text) {
        if (text != "toggle") { obj.html(siteMenu.minorSelectText).removeClass("More_Hide"); return; }
        if (obj.html() != "Hide") {
            siteMenu.minorSelectText = obj.html();
            obj.css("width", obj.width());
            obj.html("Hide").addClass("More_Hide");
        } else {
            obj.html(siteMenu.minorSelectText).removeClass("More_Hide");
        }
    }

    // If users resizes the window then recalculate the last menu item width and position the submenus again
    $(window).resize(function () {
        siteMenu.adjustLastMenuItem();
        siteMenu.positionSubMenus();

        // reset the more_Menu when window is resized
        siteMenu.moreBtn_Toggle($('#level2_More'), siteMenu.minorSelectText);
        $('#more_Container').hide(); 
    });

    // For innerSubMenus that have hash tabs like "living in italy"
    $(window).bind("hashchange", function (event) {
        var hash = window.location.hash.substring(1);
        if (hash == '') hash = 'main';
        siteMenu.changeMenuTab(hash);
    });

    this.hashInit = function () {
        var hash = window.location.hash.substring(1);
        if (hash.length > 0) {
            this.changeMenuTab(hash)
        }


        $('#innerSubMenu li').unbind("click");

        $('#innerSubMenu li').click(function (e) {
            var tabUrl = $(this).find('a').attr("href");
            var hashPos = $(this).find('a').attr("href").indexOf("#");
            var hashBoo = ($(this).find('a').attr("href").indexOf("#root") > -1);

            if (hashPos > -1 || window.location.pathname.substring(hashPos) == tabUrl) {
                //e.preventDefault();
                var tabName = tabUrl.substring(hashPos + 1, tabUrl.length)
                if (hashPos == -1 || hashBoo) tabName = "main";

                if (window.location.pathname.substring(hashPos) == tabUrl) {
                    var oldHref = $(this).find('a').attr("href");
                    tabUrl = oldHref + "#root"
                    $(this).find('a').attr("href", tabUrl);
                    $('#mainTab').show();
                }
                window.location.href = tabUrl;

                // ie7 fix
                if (navigator.appVersion.indexOf("MSIE 7.") != -1) this.changeMenuTab(tabName);
            }
        });
    };

    this.changeMenuTab = function(tabName) {
        if (tabName == 'root') tabName = "main"
        $('.tab').hide();
        $('#' + tabName + "Tab").animate({ "opacity": "show" }, 600);
        $('#innerSubMenu li')
            .removeClass("active")
            .each(function (index) {
                if (index == 0 && tabName == 'main') $(this).addClass("active");
                if ($(this).find("a").attr("href").indexOf(tabName) > -1) $(this).addClass("active");
            });
    }


    this.make_Level1_Full_Items_Active = function () {
        $("#level1_All li").each(function (i) {
            var fullListItem = $(this);
            $('.active').each(function(j) {
                if (fullListItem.html() == $(this).html()) fullListItem.addClass("active")
            });
        });
    };

};

// Initialize the siteMenu
$(function () { siteMenu.init(); });