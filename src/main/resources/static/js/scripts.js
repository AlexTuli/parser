function chooseParser(actionName) {
            //document.getElementById("parser-form").setAttribute('action','/' + actionName);
            document.getElementById("parser-name").innerHTML = actionName.toUpperCase() + " parser";
            $("#parser").collapse("show");
}
