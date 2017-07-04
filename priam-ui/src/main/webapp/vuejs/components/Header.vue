<template>
  <div class="container-fluid">
    <nav class="navbar navbar-default navbar-fixed-top">
            <!--<div class="navbar-header">
                <router-link to="/" class="navbar-brand">PRIAM</router-link>
            </div>-->
            <div class="navbar-header">
              <a class="navbar-brand logo" href="#/">
                <img src="../assets/Logo_Priam.png" width="100px" height="40px" alt="PRIAM"/>
              </a>
            </div>

             <two-lines-menu :menus="menus" :activeMenu="currentActiveMenu" :activeSubMenu="currentSubMenu"></two-lines-menu>
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

    props :['activeMenu', 'activeSubMenu'],
    data() {
      return {
        menus : [

          {
            id : 'programme',
            name : 'programme',
            label : 'Programme',
            items : [
              {
                id : 'ListePrg',
                name : 'ListePrg',
                label : 'Liste programmes'
              }
            ]
          },
          {
            id : 'chargement',
            name : 'chargement',
            label : 'Chargement'
          },
          {
            id : 'parametrage',
            name : 'parametrage',
            label : 'Paramétrage'
          },

        ],
       currentActiveMenu :  {
          id : ''
        },
        currentSubMenu :  {
          id : ''
        },

        isDropdownOpen: false
      }
    },

    created()  {
        let currentRoute = this.$route.matched;
        console.log('currentRoute =  ' + currentRoute[0].name);
        this.currentActiveMenu.id = currentRoute[0].name;
        this.currentSubMenu.id = currentRoute.length > 1 && currentRoute[1] !== undefined ? currentRoute[1].name : '';

        console.log('currentActiveMenu ' + this.currentActiveMenu.id);
        console.log('currentSubMenu ' + this.currentSubMenu.id);

    },

    components :    {
        twoLinesMenu : TwoLinesMenu
    }
  }
</script>
