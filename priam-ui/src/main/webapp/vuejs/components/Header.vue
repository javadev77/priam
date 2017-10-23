<template>
  <div class="container-fluid">
    <nav class="navbar navbar-default navbar-fixed-top">

            <div class="navbar-header">
              <a class="navbar-brand logo" href="#/">
                <img src="../../static/images/Logo_Priam.png" width="100px" height="40px" alt="PRIAM"/>
              </a>
            </div>

             <two-lines-menu :menus="menus" :activeMenu="currentActiveMenu" :activeSubMenu="currentSubMenu"></two-lines-menu>

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

        isDropdownOpen: false,

        //displayName: ''
      }
    },

    created()  {

      const customActions = {
        searchUser : {method : 'GET', url :'app/rest/general/currentUser'}
      }
      this.resource= this.$resource('', {}, customActions);

        let currentRoute = this.$route.matched;
        console.log('currentRoute =  ' + currentRoute[0].name);
        this.currentActiveMenu.id = currentRoute[0].name;
        this.currentSubMenu.id = currentRoute.length > 1 && currentRoute[1] !== undefined ? currentRoute[1].name : '';

        console.log('currentActiveMenu ' + this.currentActiveMenu.id);
        console.log('currentSubMenu ' + this.currentSubMenu.id);

    },


    computed : {
      displayName() {
        let currentUser = this.$store.getters.getCurrentUser;
        return currentUser.displayName;

      }
    },

    methods: {
      getDisplayName() {
        this.resource.searchUser()
          .then(response => {
            return response.json();
          })
          .then(data => {
            console.log('data = ' + data);
            debugger;
            //this.displayName = data.displayName;
          });

      }
    },

    components :    {
        twoLinesMenu : TwoLinesMenu
    }
  }
</script>
