import Vue from 'vue'
import VueRouter from 'vue-router';
import VueResource from 'vue-resource';

import App from './App.vue'
import { routes } from './routes';
import { fetchInitData } from './store/actions';
import store from './store/store';
import './directives/form-elements'
import './utils/Arrays'
import VeeValidate from 'vee-validate';

Vue.use(VeeValidate);
Vue.use(VueRouter);
Vue.use(VueResource);

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

/*********************************************
 * ******** Fetch init Data only Once ********
 * ********************************************/
fetchInitData(store);
