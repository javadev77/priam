<template>
  <div class="nav navbar-nav menu2lines no-select">
    <div class="menu">
      <ul id="menu">
        <!--<li v-for="menu in menus"
            :class="menuClass(menu)">
          <!--<a v-bind:href="menu.items[0].href" @click="selectMenu(menu)" :class="{'badged' : menu.badge}">
            <span translate="{{ menu.name }}"></span>
            <!--<div class="notifications ng-hide" v-if="menu.badge" homer-tooltip
                 data-original-title="{{menu.title | translate : {number:menu.badge} }}" data-placement="bottom">
              {{ menu.badge }}
            </div>
          </a>--


        </li>-->
        <template v-for="menu in menus">
          <!--<router-link  :to="{name : routeName(menu)}" :activeClass="menuClass(menu)" tag="li" @click.native="selectMenu(menu)"><a >{{ menu.label }}</a></router-link>-->
          <li :class="menuClass(menu)">
            <a :href="resolveUrl(menu.name)" @click="selectMenu(menu)">{{ menu.label }}</a>
          </li>
        </template>

      </ul>
    </div>
    <div class="submenu">
      <template v-for="menu in menus" v-if="menu.items">
      <ul   :class="subMenuClass(menu)">
          <!--ng-class="(\menu-\ + menu.id + \-items\) + \ \ + ($isActive(menu) ? \visible\ : \hidden\)"-->
        <!--<li v-for="item in menu.items"
            ng-class="(\item-\ + item.id) + \ \ + ($isActive(menu, item) ? \active\ : \\)">
          <a ng-href="{{ item.href }}" ng-click="$selectItem(menu, item)"
             ng-style="::(menu.id === \traitementCrd\ || menu.id === \monitoring\) ? {width:\112px\} :\\">
            <span translate="{{ item.name }}"></span>
            <div class="notifications ng-hide" ng-show="item.badge" homer-tooltip
                 data-original-title="{{item.title | translate : {number:item.badge} }}" data-placement="bottom">
              {{ item.badge }}
            </div>
          </a>
        </li>-->

        <template v-for="item in menu.items">
          <!--<router-link  :to="{name : routeName(item)}" activeClass="active" tag="li" @click.native="selectItem(menu, item)">
            <a :class="menuClass2(menu, item)">{{ item.label }}</a></router-link>-->
          <li :class="menuClass2(menu, item)">
            <a :href="resolveUrl(item.name)" @click="selectItem(menu, item)">{{ item.label }}</a>
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
            required: false
        },

        activeSubMenu : {
            required : false
        }

      },

      methods : {

          resolveUrl(routeName) {
              var resolve = this.$router.resolve({name: routeName});
              return resolve.href;

          },

          isActive(menu, item) {
              var isActiveMenu = this.activeMenu && menu && this.activeMenu.id === menu.id;
              if (isActiveMenu && item !== undefined )  {
                var isActive = this.activeSubMenu && item && this.activeSubMenu.id === item.id;
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
                this.$router.push({name : item.name})
              } else {
                this.$router.push({name : menu.name})
              }

              this.activeMenu = menu || null;
              this.activeSubMenu = item || null;
          },

          routeName(menu) {
              return menu.name;
          }
      }


  }

</script>

<style>

</style>
