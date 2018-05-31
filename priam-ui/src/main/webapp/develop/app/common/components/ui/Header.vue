<template>
  <div class="container-fluid">
    <nav class="navbar navbar-default navbar-fixed-top">

            <div class="navbar-header">
              <a class="navbar-brand logo" href="#/">
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
  import programmeMixin from '../../mixins/programmeMixin';

  export default {

    mixins : [programmeMixin],

    props :['activeMenu', 'activeSubMenu'],
    data() {
      return {
        menus : [

          {
            id : 'programme',
            name : 'programme',
            label : 'Programme',
            routes : ['programme', 'selection', 'selection-cms','affectation', 'affectation-cms', 'ListePrg'],
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
            label : 'Chargement',
            routes : ['chargement']
          },
          {
            id : 'parametrage',
            name : 'parametrage',
            label : 'Paramétrage',
            routes : ['parametrage']
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

    created()  {

        let currentRoute = this.$route.matched;
        this.setCurrentActiveMenu(currentRoute[0].name);
        this.authenticatedMenus();

    },


    computed : {
      displayName() {
        let currentUser = this.$store.getters.getCurrentUser;
        return currentUser.displayName;
      },

      priamVersion() {
          return this.$store.getters.appInfo['priam.version'];
      },


    },

    methods : {

        findMenu(routeViewName) {
            let menu = this.menus.find(elem => {
              return elem.routes && elem.routes.indexOf(routeViewName) > -1;
            });
            return menu;
        },

        setCurrentActiveMenu(currentRouteViewName) {
          let foundMenu = this.findMenu(currentRouteViewName);
          if(foundMenu && foundMenu !== undefined) {
            this.currentActiveMenu.id = foundMenu.id;
            this.currentSubMenu.id = foundMenu.items !== undefined &&
                                     foundMenu.items.length >= 1 &&
                                     foundMenu.items[0] !== undefined ?
                                     foundMenu.items[0].id : '';
          }
        },

      authenticatedMenus() {
       // var menusAuth = this.menus;
        if(this.hasRight('VIJREV')) {
          this.menus.push( {
            id : 'catalogue',
            name : 'catalogue-cms',
            label : 'Catalogue CMS',
            routes : ['catalogue-cms'],
            items : [
              {
                id : 'Catalogue',
                name : 'Catalogue',
                label : 'Catalogue'
              },
              {
                id : 'Journal',
                name : 'Journal',
                label : 'Journal'
              }
            ]
          });
        }
       // return menusAuth;
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
