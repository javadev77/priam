<template>
  <div class="container-fluid">
    <nav class="navbar navbar-default navbar-fixed-top">
            <!--<div class="navbar-header">
                <router-link to="/" class="navbar-brand">PRIAM</router-link>
            </div>-->
            <div class="navbar-header">
              <a class="navbar-brand logo" href="#/">
                <img src="static/images/logo-priam.png  " width="100px" height="40px" alt="PRIAM"/>
              </a>
            </div>

             <two-lines-menu :menus="menus" :activeMenu="activeMenu" :activeSubMenu="activeSubMenu"></two-lines-menu>
            <!--<div class="nav navbar-nav">
              <ul class="nav navbar-nav">
                    <router-link to="/chargement" activeClass="active" tag="li"><a>Chargement</a></router-link>
                    <router-link to="/affectation" activeClass="active" tag="li"><a>Affectation</a></router-link>
                </ul>
            </div>
-->
            <div class="nav navbar-nav navbar-right">
              <ul class="nav navbar-nav navbar-right">
                <li class="dropdown"
                    :class="{open: isDropdownOpen}"
                    @click="isDropdownOpen = !isDropdownOpen">
                  <a class="dropdown-toggle clickable" data-toggle="dropdown" aria-expanded="false">
                    <span>Bonjour GUEST</span>
                    <span class="caret"></span>
                  </a>

                  <ul class="dropdown-menu" role="menu">
                    <li> <a href="logout">
                    <span class="fui-exit"></span> Déconnexion</a>
                    </li>
                  </ul>
                </li>
                    <li>
                      <p class="homerversion">Version : 1.0.0</p>
                    </li>
              </ul>
            </div><!-- /.navbar-collapse -->
    </nav>
  </div><!-- /.container-fluid -->

</template>

<script>
  import TwoLinesMenu from './common/TwoLinesMenu.vue'
  export default {
    data() {
      return {
        menus : [

          {
            id : 'Programme',
            name : 'programme',
            label : 'Programme',
            items : [
              {
                id : 'ListePrg',
                name : 'ListePrg',
                label : 'Liste Programmes'
              },
              {
                id : 'CreateProg',
                name : 'CreateProg',
                label : 'Créer Programme'
              }
            ]
          },
          {
            id : 'Chargement',
            name : 'chargement',
            label : 'Chargement'
          },
          {
            id : 'Parametre',
            name : 'parametrage',
            label : 'Paramétrage'
          },

        ],
        activeMenu :  {
          id : 'Programme'
        },
        activeSubMenu :  {
          id : 'ListePrg'
        },

        isDropdownOpen: false
      }
    },

    components :    {
        twoLinesMenu : TwoLinesMenu
    }
  }
</script>
