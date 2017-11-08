<template>
  <div class="nav navbar-nav menu2lines no-select">
    <div class="menu">
      <ul id="menu">

        <template v-for="menu in menuData">
          <!--<router-link  :to="{name : routeName(menu)}" :activeClass="menuClass(menu)" tag="li" @click.native="selectMenu(menu)"><a >{{ menu.label }}</a></router-link>-->
          <li :class="menuClass(menu)">
            <a @click="selectMenu(menu)">{{ menu.label }}</a>
          </li>
        </template>

      </ul>
    </div>
    <div class="submenu">
      <template v-for="menu in menuData" v-if="menu.items">
      <ul  :class="subMenuClass(menu)">


        <template v-for="item in menu.items">

          <li :class="menuClass2(menu, item)">
            <a @click="selectItem(menu, item)">{{ item.label }}</a>
          </li>
        </template>
      </ul>
      </template>
    </div>
  </div>
</template>

<script>

  export default {

      props : {
        menus : {
            required : true,
            type : Array
        },

        activeMenu : {
            type: String,
            required: false
        },

        activeSubMenu : {
            type: String,
            required : false
        }

      },

      data() {

          return {
              //activeMenu : {},
              //activatedSubMenu : {}
          }

      },

      created() {
          //this.activatedMenu = this.activeMenu;
          //this.activeSubMenu = this.activeSubMenu;
      },

      computed :{

          menuData() {
              return this.menus;
          }

      },

      methods : {

          resolveUrl(routeName) {
              var resolve = this.$router.resolve({name: routeName});
              return resolve.href;

          },

          isActive(menu, item) {
              console.log("activeMenu=" + this.activeMenu)
              console.log("menu=" + menu.id)
              var isActiveMenu = this.activeMenu && menu && this.activeMenu === menu.id;
              console.log("isActiveMenu=" + isActiveMenu)
              if (isActiveMenu && item !== undefined )  {
                var isActive = this.activeSubMenu && item && this.activeSubMenu === item.id;
                return isActive;
              } else {
                return isActiveMenu;
              }
          },

          menuClass(menu) {
              return 'menu-' +  menu.id + ' ' + (this.isActive(menu) ? 'active' : '');

          },

          menuClass2(menu, item) {
            var isActive = this.isActive(menu, item);
            return 'item-' +  item.id + ' ' + ( isActive ? 'active' : '');
          },

          subMenuClass(menu) {
              return 'menu-' +  menu.id + '-items ' + (this.isActive(menu) ? 'visible' : 'hidden');
          },

          selectMenu(menu) {
              function _hasSubItems(menu) {
                return menu && (menu.items instanceof Array) && menu.items.length > 0;
              }

              var firstItem = _hasSubItems(menu) ? menu.items[0] : null;
              this.selectItem(menu, firstItem);
          },

          selectItem(menu, item) {
              if(item != null) {
                this.activeSubMenu = item.id;
                this.$router.push({name : item.name})
              } else {
                this.$router.push({name : menu.name})
              }

              this.activeMenu = menu.id;

          },

          routeName(menu) {
              return menu.name;
          }
      }


  }

</script>

<style>

</style>
