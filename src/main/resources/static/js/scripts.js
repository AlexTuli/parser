function chooseParser(actionName) {
            document.getElementById("parser-form").setAttribute('action','/' + actionName);
            document.getElementById("parser-name").innerHTML = actionName.toUpperCase() + " parser";
            $("#parser").collapse("show");
}

function httpGet(theUrl){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}