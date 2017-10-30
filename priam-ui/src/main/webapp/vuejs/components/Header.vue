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

        //displayName: ''
      }
    },

    created()  {

        let currentRoute = this.$route.matched;
        console.log('currentRoute =  ' + currentRoute[0].name);
        this.currentActiveMenu.id = currentRoute[0].name;
        this.currentSubMenu.id = currentRoute.length > 1 && currentRoute[1] !== undefined ? currentRoute[1].name : '';

    },


    computed : {
      displayName() {
        let currentUser = this.$store.getters.getCurrentUser;
        return currentUser.displayName;

      }
    },

    methods : {

        findMenu(idMenu) {
            let menu = this.menus.find(elem => {
              return elem.id === idMenu;
            });

            return menu;
        }

    },

    watch : {

      '$route' (to, from) {
        console.log('from route =  ' + from.path);
        console.log('to route =  ' + to.path);
        if( to.path !== from.path) {
            let newRoute = to.matched;
            let foundMenu = this.findMenu(newRoute[0].name);
            if(foundMenu && foundMenu !== undefined) {
              this.currentActiveMenu.id = foundMenu.id;
              this.currentSubMenu.id = foundMenu.items !== undefined &&
                                       foundMenu.items.length >= 1 &&
                                       foundMenu.items[0] !== undefined ? foundMenu.items[0].id : '';
            } // if not found we stay in the current active menu
        }

      }

    },

    components :    {
        twoLinesMenu : TwoLinesMenu
    }
  }
</script>
