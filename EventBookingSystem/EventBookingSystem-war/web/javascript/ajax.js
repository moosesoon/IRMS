/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var xmlHttpRequest = null;
var ajax_Action = "";

var enableWriteToStatusBar = true;

function ajax_Init(){
    xmlHttpRequest = ajax_GetXmlHttpObject();
    if(xmlHttpRequest){
        xmlHttpRequest.onreadystatechange = ajax_ReadyStateChange;
    }
}

function ajax_GetXmlHttpObject(){
    var objXmlHttpRequest = null;
    try{
        objXmlHttpRequest = new XMLHttpRequest();
    } catch(e){
        try{
        objXmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
            //IE 6 and Below    
        } catch(e){
            objXmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    return objXmlHttpRequest;
}

function ajax_ReadyStateChange(){
    var ajaxResponseXML = "";
    
    if(ajax_Action == "addNewEvent"){
        switch(xmlHttpRequest.readyState){
            case 0: 
                writeToStatusBar("Ajax: The request is no initialised");
                break;
            case 1: 
                writeToStatusBar("Ajax: The request has been set up"); 
                break;
            case 2: 
                writeToStatusBar("Ajax: The request has been sent"); 
                break;
            case 3: 
                writeToStatusBar("Ajax: The request is in process"); 
                break;
            case 4:
                ajaxResponseXML = xmlHttpRequest.responseText;
                ajax_ActionHandler_AddNewEvent(ajaxResponseXML);
                writeToStatusBar("Ajax: The request is completed " + xmlHttpRequest.status 
                               + ", " + xmlHttpRequest.statusText);
                break;
        }
    }
}

function ajax_SendRequestByGet(url, action){
    ajax_Init();
    xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.send(null);
    ajax_Action = action;
}

function ajax_SendRequestByPost(url, parameters, action){
    ajax_Init();
    xmlHttpRequest.open("POST", url, true);
    xmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlHttpRequest.setRequestHeader("Content-length", parameters.length);
    xmlHttpRequest.setRequestHeader("Connection", "close");
    alert(parameters);
    xmlHttpRequest.send(parameters);
    
    ajax_Action = action;
}

function writeToStatusBar(text){
    if(enableWriteToStatusBar){
        window.status = text;
    }
}

function ajax_ActionHandler_AddNewEvent(ajaxResponseXML){
    if(ajaxResponseXML == "conflict"){
        document.getElementById("spanStatus").innerHTML = "<font color='red'>Venue Conflict</font>"
    } else{
        document.getElementById("spanStatus").innerHTML = "<font color='green'>New event added successfully: " 
                                                            + ajaxResponseXML + "</font>"
        document.getElementById("formTable").style.visibility = "hidden";
    }
    document.getElementById("ajaxSpinner").style.visibility = "hidden";
}

function ajax_ActionHandler_Invoke(){
    if(validateForm()){
        document.getElementById("ajaxSpinner").style.visibility = "visible";
        
        var params = "eventName=" + document.getElementById("eventName").value 
                   + "&startDateTime=" + document.getElementById("startDateTime").value 
                   + "&endDateTime=" + document.getElementById("endDateTime").value 
                   + "&venue=" + document.getElementById("venue").options[document.getElementById("venue").selectedIndex].value;
        
        ajax_SendRequestByPost(document.getElementById("formAddNewEvent").action, params, "addNewEvent");
    }
}

