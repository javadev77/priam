<template>
    <div class="container-fluid">
        <app-header :activeMenu="activeMenu" ></app-header>
        <div class="container-fluid">
                <transition name="slide" mode="out-in">
                    <router-view></router-view>
                </transition>
        </div>

      <div class="routing" v-if="isRouting"></div>
    </div>
</template>

<script>
    import Header from './common/components/ui/Header.vue';

    export default {
        data() {

            return {
              activeMenu : {
                  id : ''
              }
            }
        },

      computed : {
            isRouting() {
              return this.$store.getters.routing;
            }
      },

        created() {
          this.activeMenu.id = this.$route.name;
        },


        components: {
            appHeader: Header
        }
    }
</script>

