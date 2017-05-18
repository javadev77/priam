import Vue from 'vue'
import VueRouter from 'vue-router';
import VueResource from 'vue-resource';
import vSelect from 'vue-select';

import App from './App.vue'
import { routes } from './routes';
import { fetchInitData } from './store/actions';
import store from './store/store';

Vue.use(VueRouter);
Vue.use(VueResource);
Vue.component('v-select', vSelect);

Vue.http.options.root="/priam"

const router = new VueRouter({
  routes
});

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})


fetchInitData(store);
