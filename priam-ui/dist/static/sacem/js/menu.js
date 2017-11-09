// gestion du menu sur 2 lignes
var MenuData = [{
    "menu":[
        {
            "MenuId":1,
            "MenuName":"Collecte",
            "MenuLink":"#1",
            "Menus":[
                {
                    "MenuId":7,
                    "MenuName":"Qualification",
                    "MenuLink":"sacem-template-homere-collecte-qualification.html",
                    "Menus":{
                    }
                },
                {
                    "MenuId":8,
                    "MenuName":"Saisie",
                    "MenuLink":"#",
                    "Menus":{
                    }
                },
                {
                    "MenuId":9,
                    "MenuName":"Liens",
                    "MenuLink":"sacem-template-homere-collecte-liens.html",
                    "Menus":{
                    }
                },
                {
                    "MenuId":10,
                    "MenuName":"Synthèse",
                    "MenuLink":"#",
                    "Menus":{
                    }
                },
                {
                    "MenuId":11,
                    "MenuName":"Facturation",
                    "MenuLink":"#",
                    "Menus":{
                    }
                },
                {
                    "MenuId":12,
                    "MenuName":"Avis de Crédit",
                    "MenuLink":"#",
                    "Menus":{
                    }
                },
                {
                    "MenuId":13,
                    "MenuName":"Association",
                    "MenuLink":"#",
                    "Menus":{
                    }
                },
                {
                    "MenuId":14,
                    "MenuName":"Validation",
                    "MenuLink":"#",
                    "Menus":{
                    }
                }
            ]
        },
        {
            "MenuId":2,
            "MenuName":"Répartition",
            "MenuLink":"#2",
            "Menus":{
            }
        },
        {
            "MenuId":3,
            "MenuName":"Consultation",
            "MenuLink":"#3",
            "Menus":[
                {
                    "MenuId":15,
                    "MenuName":"Déclarations",
                    "MenuLink":"sacem-template-homere-consultation-declaration.html",
                    "Menus":{
                    }
                },
                {
                    "MenuId":16,
                    "MenuName":"Fichiers annexes",
                    "MenuLink":"#",
                    "Menus":{
                    }
                }
            ]
        },
        {
            "MenuId":4,
            "MenuName":"Statistiques",
            "MenuLink":"#4",
            "Menus":{
            }
        },
        {
            "MenuId":5,
            "MenuName":"Paramétrage",
            "MenuLink":"#5",
            "Menus":{
            }
        }
        
    ]
}
];


for(var i = 0, j = MenuData[0].menu.length; i<j; i++) {
    
    var root_menu = MenuData[0].menu[i];
    
    if(root_menu.hasOwnProperty("MenuId")) {
        
        $("#menu").append("<li><a href='" + root_menu.MenuLink + "' data-link=" + root_menu.MenuId + ">" + root_menu.MenuName + "</a></li>");
        
        if(root_menu.hasOwnProperty("Menus") && root_menu.Menus.length > 0) {
            
            $("#submenu").append("<ul id='submenu_" + root_menu.MenuId + "'>");
            
            for(var n = 0, m = root_menu.Menus.length; n<m; n++) {
                var sub_menu = root_menu.Menus[n];
                if(sub_menu.hasOwnProperty("MenuId")) {
                    $("#submenu_" + root_menu.MenuId).append("<li><a href='" + root_menu.Menus[n].MenuLink + "' data-link=" + root_menu.MenuId + ">" + sub_menu.MenuName + "</a></li>");
                }
            }
            
            $("#submenu").append("</li></ul>");
            $("#submenu_" + root_menu.MenuId).removeClass("visible").addClass("hidden");
            
            
        }
    }
}



$(document).ready(function(){
    
    
    if(window.location.hash) {
        var full_url = document.URL;
        var urlID = full_url.substring(full_url.lastIndexOf('#') + 1);
        $("#submenu_" + urlID).removeClass("hidden").addClass("visible");
        $("#submenu_" + urlID).parent().siblings().removeClass("active").end().addClass("active");
        
    } else {
        $("#menu li").first().addClass("active");
        $("#submenu_1").removeClass("hidden").addClass("visible");
        $("#submenu_1 li").first().addClass("active");
    }
    
    $("#menu").on('click', "a", function() {
        
        var linkID = $(this).attr('data-link');
        
        $(this).parent().siblings("li").removeClass("active").end().addClass("active");
        
        $("#submenu_" + linkID).removeClass("hidden").addClass("visible");
        $("#submenu_" + linkID).siblings().removeClass("visible").addClass("hidden");

    });
    
    $("#submenu").on('click', "a", function() {
        $(this).parent().siblings("li").removeClass("active").end().addClass("active");
    });
    
});

