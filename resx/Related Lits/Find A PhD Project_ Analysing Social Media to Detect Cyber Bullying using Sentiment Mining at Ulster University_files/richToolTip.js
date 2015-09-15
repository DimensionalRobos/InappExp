/*
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////// richToolTip v1.0 ///// 26/3/2010 (PS) ///////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    // STEP 1, CREATE: Div Layer Below is the popup container // (PS)
    <div id="divPopupContainer" style="border-right: white 5px solid; border-top: white 5px solid;
        border-bottom: white 5px solid; border-left: white 5px solid; padding: 7px; background-color: #ffffcc;
        visibility: hidden; font: 11pt arial; width: 200px; height: 120px; position: absolute;
        text-align: left; z-index: 1;">
    </div>
    
    // STEP 2, INSERT: a mouseover on the object for tooltip // (PS)
    <span id="Span1" onmouseover="var obj = document.getElementById('ToolTip1');richToolTip(obj.innerHTML,event,obj.style.height,obj.style.width);"
        onmouseout="hideToolTip();">test1</span>

    // STEP 3, CREATE: The info container, height and width are extracted from here // (PS)
    <div id="ToolTip1" style="display:none;">
            <div style="height:110px;width:190px;padding:5px;border:solid 1px green; text-align:justify;">
            <img src="/images/warning.gif" style="float:left;padding:0px 5px 2px 0px;" />
            Blah blah, blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah.
            </div></div>
            
    // STEP 4, LINK: Paste this line in your <head> to link to this library file // (PS)
    <script src="../library/js/richToolTip.js" language="javascript" type="text/javascript"></script>
    /////////////////////////////////////////////////////////////////////////////////////////////////
*/


function richToolTip(divPopupContainer, e, setH, setW) {
    var xCoord = 0;
    var yCoord = 0;
    var oScreenWidth = 0;
    var oScreenHeight = 0;
    var myElement;

    if (document.layers) {
        // old Netscape versions
        myElement = document.divPopupContainer;
        if ((myElement == null) || (myElement == "undefined"))
        { myElement = document.getElementById('divPopupContainer'); }

        myElement.style.height = setH;
        myElement.style.width = setW;

        oScreenWidth = window.innerWidth;
        oScreenHeight = window.innerHeight;

        xCoord = e.pageX + 15;
        yCoord = e.pageY + 15;

        if (xCoord + 200 + 5 > oScreenWidth)
        { xCoord = xCoord - 225; }
        if (yCoord + 120 + 15 > oScreenHeight)
        { yCoord = yCoord - 150; }
    }
    else {
        // IE and newer versions of Netscape
        myElement = document.getElementById('divPopupContainer');

        myElement.style.height = setH;
        myElement.style.width = setW;

        if (myElement != null && myElement != "undefined") {
            oScreenWidth = document.body.clientWidth;
            oScreenHeight = document.body.clientHeight;

            xCoord = e.clientX + document.body.scrollLeft +
                                 document.documentElement.scrollLeft + 15;
            yCoord = e.clientY + document.body.scrollTop +
                                 document.documentElement.scrollTop + 15;

            if (e.clientX + 200 + 5 > oScreenWidth)
            { xCoord = xCoord - 225; }
            if (e.clientY + 120 + 15 > oScreenHeight)
            { yCoord = yCoord - 0; }
        }
    }
    if (xCoord != 0 && yCoord != 0 && myElement != null && myElement !=
"undefined") {
        myElement.innerHTML = divPopupContainer;
        if (document.layers) {

            if (typeof myElement.style.top != 'number')
            { eval("myElement.moveTo(xCoord, yCoord)"); }
            else {
                myElement.style.top = yCoord;
                myElement.style.left = xCoord;
            }

            if ((myElement.style.visibility == null) ||
(myElement.style.visibility == "undefined"))
            { myElement.visibility = 'visible'; }
            else
            { myElement.style.visibility = 'visible'; }

        }
        else {
            myElement.style.top = yCoord + "px";
            myElement.style.left = xCoord + "px";
            myElement.style.visibility = 'visible';
        }
    }
}

function hideToolTip() {
    if (document.layers) {
        // old Netscape versions
        var myElement = document.divPopupContainer;
        if ((myElement == null) || (myElement == "undefined"))
        { myElement = document.getElementById('divPopupContainer'); }
        if ((myElement.style.visibility == null) ||
(myElement.style.visibility == "undefined"))
        { myElement.visibility = 'hidden'; }
        else
        // IE and newer versions of Netscape
        { myElement.style.visibility = 'hidden'; }
    }
    else
    { document.getElementById('divPopupContainer').style.visibility = 'hidden'; }
}
