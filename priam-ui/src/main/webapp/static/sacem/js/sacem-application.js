// gestion des tooltips
$(document).ready(function(){
    // Logo
	$("#logo-bloc a").tooltip({
        placement : 'bottom'
    });
	// breadcrumb
	$("#breadcrumb-bloc a").tooltip({
        placement : 'bottom'
    });
	// barre de titre
	$("#bar-titre-bloc a").tooltip({
        placement : 'bottom'
    });
	// menu contextuel
	$("#menu-cont-bloc a").tooltip({
        placement : 'bottom'
    });
});

// I8N - Traduction de la page
var option = {
fallbackLng: 'fr', 
// Liste des fichiers de traduction .json
ns: { 
    namespaces: ['app', 'common'], 
    defaultNs: 'app'
  } 
};
i18n.init(option, function(t) {
	// translate nav
	$("body").i18n();
});

//Raz des raccourcis claviers
Mousetrap.reset();

//Gestion des raccourcis claviers sur la barre contextuelle
Mousetrap.bind('f2', function(e){ 
 if (e.preventDefault) {
 e.preventDefault();
 } else {
 // internet explorer
 e.returnValue = false;
 }
 alert("Créer une fiche");
 return false;
 });
 
Mousetrap.bind('f3', function(e){ 
 if (e.preventDefault) {
 e.preventDefault();
 } else {
 // internet explorer
 e.returnValue = false;
 }
 alert("Modifier une fiche");
 return false;
 });

 Mousetrap.bind('f4', function(e){ 
 if (e.preventDefault) {
 e.preventDefault();
 } else {
 // internet explorer
 e.returnValue = false;
 }
 alert("Supprimer une fiche");
 return false;
 });

Mousetrap.bind('f9', function(e){ 
 if (e.preventDefault) {
 e.preventDefault();
 } else {
 // internet explorer
 e.returnValue = false;
 }
 alert("Aide sur l'application");
 return false;
 });
 
 Mousetrap.bind('f8', function(e){ 
 if (e.preventDefault) {
 e.preventDefault();
 } else {
 // internet explorer
 e.returnValue = false;
 }
 alert("Retour à l'écran précédent");
 return false;
 });


window.onload=function() {
    $.getJSON( "locales/fr/menu.json", function(data) {
        
        var MenuData = [data];
        
        for(var i = 0, j = MenuData[0].menu.length; i<j; i++) {

            var root_menu = MenuData[0].menu[i];

            if(root_menu.hasOwnProperty("MenuId")) {

                $("#menu").append("<li><a href='" + root_menu.MenuLink + "' data-link=" + root_menu.MenuId + ">" + root_menu.MenuName + "</a></li>");

                if(root_menu.hasOwnProperty("Menus") && root_menu.Menus.length > 0) {

                    $("#submenu").append("<ul id='submenu_" + root_menu.MenuId + "'>");

                    for(var n = 0, m = root_menu.Menus.length; n<m; n++) {
                        var sub_menu = root_menu.Menus[n];
                        if(sub_menu.hasOwnProperty("SubMenuId") && root_menu.Menus[n].hasOwnProperty("Notifications")) {
                            $("#submenu_" + root_menu.MenuId).append("<li><a href='" + root_menu.Menus[n].SubMenuLink + "' data-link=" + root_menu.Menus[n].SubMenuId + ">" + sub_menu.SubMenuName + "<div class='notifications'>" + root_menu.Menus[n].Notifications + "</div></a></li>");
                        } else { 
                            $("#submenu_" + root_menu.MenuId).append("<li><a href='" + root_menu.Menus[n].SubMenuLink + "' data-link=" + root_menu.Menus[n].SubMenuId + ">" + sub_menu.SubMenuName + "</a></li>");
                        }

                    }

                    $("#submenu").append("</li></ul>");
                    $("#submenu_" + root_menu.MenuId).removeClass("visible").addClass("hidden");

                    if ( $("#submenu_1").length ) {
                        $("#submenu_1").removeClass("hidden").addClass("visible");
                    }

                $("#menu li:nth-child(1)").addClass("active");
                $("#submenu_1 li:nth-child(1)").addClass("active");

                }
            }
        }
        
    });
}


$(document).ready(function(){
    
    //Gestion du click sur la première ligne du menu
    
    $("#menu").on('click', "a", function() {
        
        var linkID = $(this).attr('data-link');
        
        $("#menu li").removeClass("active");
        $(this).parent().addClass("active");
        
        if ( $("#submenu_" + linkID).length ) {
            $("#submenu li").removeClass("hidden").addClass("visible");
            $("#submenu_" + linkID).removeClass("hidden").addClass("visible");
            $("#submenu_" + linkID).siblings().removeClass("visible").addClass("hidden");
        } else {
            $("#submenu li").removeClass("visible").addClass("hidden");
        }
        
        $("#submenu li").removeClass("active");
        $("#submenu_" + linkID + " li:nth-child(1)").addClass("active");
    });
    
    
    
    //Gestion du click sur la deuxième ligne du menu
    
    $("#submenu").on('click', "a", function() {
        $("#submenu li").removeClass("active");
        $(this).parent().addClass("active");
    });
    
});

