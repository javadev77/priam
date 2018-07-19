<template>
  <div class="container-fluid">
    <nav class="navbar navbar-default navbar-fixed-top">

            <div class="navbar-header">
              <a class="navbar-brand logo" href="#/" @click="$router.push('listeProg')">
                <img src="static/images/Logo_Priam.png" width="100px" height="40px" alt="PRIAM"/>
              </a>
            </div>

             <two-lines-menu :menus="menus" :activeMenu="currentActiveMenu.id" :activeSubMenu="currentSubMenu.id"></two-lines-menu>

            <div class="nav navbar-nav navbar-right">
              <ul class="nav navbar-nav navbar-right">
                <li class="dropdown"
                    :class="{open: isDropdownOpen}"
                    @click="isDropdownOpen = !isDropdownOpen">
                  <a class="dropdown-toggle clickable" data-toggle="dropdown" aria-expanded="false">
                    <span>Bonjour {{ displayName }}</span>
                    <span class="caret"></span>
                  </a>

                  <ul class="dropdown-menu" role="menu">
                    <li> <a href="logout">
                    <span class="fui-exit"></span> Déconnexion</a>
                    </li>
                  </ul>
                </li>
                    <li>
                      <p class="homerversion">v{{ priamVersion }}</p>
                    </li>
              </ul>
            </div><!-- /.navbar-collapse -->
    </nav>
  </div><!-- /.container-fluid -->

</template>

<script>
  import TwoLinesMenu from './TwoLinesMenu.vue';
  import ProgrammeMixin from  './../../mixins/programmeMixin';

  export default {

    props :['activeMenu', 'activeSubMenu'],
    data() {
      return {
        menus : [

          {
            id : 'programme',
            routeName : 'programme',
            label : 'Programme',
            routes : ['programme', 'selection', 'selection-cms','affectation', 'affectation-cms', 'listeProg'],
            authorized : true,
            items : [
              {
                id : 'listeProg',
                routeName : 'programme',
                label : 'Liste programmes',
                authorized : true
              }
            ]
          },
          {
            id : 'chargement',
            routeName : 'chargement',
            label : 'Chargement',
            authorized : true,
            routes : ['chargement', 'fichiers'],
            items : [
              {
                id : 'fichiers',
                routeName : 'chargement',
                label : 'Fichiers',
                authorized : true
              }
            ]
          },

          {
            id : 'catalogue',
            routeName : 'catalogue-cms',
            label : 'Catalogue CMS',
            authorized : true,//this.hasRight('MENUCATAL'),
            routes : ['catalogue-cms', 'journal', 'stat'],

            items : [
              {
                id : 'catalogue',
                routeName : 'catalogue-cms',
                label : 'Catalogue',
                authorized : true,//this.hasRight('MENUCATAL'),
              },

              {
                id : 'journal',
                routeName : 'journal',
                label : 'Journal',
                authorized : true,//this.hasRight('MENUCATAL'),
              },

              {
                id : 'stat',
                routeName : 'stat',
                label : 'Statistiques',
                authorized : true, //this.hasRight('MENUCATAL')
              }
            ]
          },

          {
            id : 'parametrage',
            routeName : 'parametrage',
            label : 'Paramétrage',
            routes : ['parametrage'],
            authorized : true
          }



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

    mixins : [ProgrammeMixin],

    created()  {

        debugger;
        let currentRoute = this.$route.matched;
        this.setCurrentActiveMenu(currentRoute[0].name);

    },


    computed : {
      displayName() {
          debugger;
        let currentUser = this.$store.getters.getCurrentUser;
        return currentUser.displayName;
      },

      priamVersion() {
          return this.$store.getters.appInfo['priam.version'];
      }
    },

    methods : {

        findMenu(routeViewName) {
            let menu = this.menus.find(elem => {
              return elem.routes && elem.routes.indexOf(routeViewName) > -1;
            });
            return menu;
        },

        setCurrentActiveMenu(currentRouteViewName) {
            debugger;
          let foundMenu = this.findMenu(currentRouteViewName);
          if(foundMenu && foundMenu !== undefined) {
            this.currentActiveMenu.id = foundMenu.id;

            let subMenu = foundMenu.items.find(elem => {
              return elem && elem.routeName.indexOf(currentRouteViewName) > -1;
            });

            if(subMenu !== undefined && subMenu !== null) {
              this.currentSubMenu.id = subMenu.id;
            }

          }
        }

    },

    watch : {

      '$route' (to, from) {
        console.log('from route =  ' + from.path);
        console.log('to route =  ' + to.path);
        let newRoute = to.matched;
        this.setCurrentActiveMenu(newRoute[0].name);

      }

    },

    components :    {
        twoLinesMenu : TwoLinesMenu
    }
  }
</script>
