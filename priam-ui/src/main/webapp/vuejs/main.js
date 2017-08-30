import Vue from 'vue'
import VueRouter from 'vue-router';
import VueResource from 'vue-resource';

import App from './App.vue'
import { routes } from './routes';
import { fetchInitData } from './store/actions';
import store from './store/store';
import './directives/form-elements'
import './filters/filters'
import './utils/Arrays'
import VeeValidate from 'vee-validate';
import vueShortkey from 'vue-shortkey'


Vue.use(vueShortkey);


Vue.use(VeeValidate, {
  locale: 'fr',
  dictionary: {
    fr: {
      messages: {
        required : (e) => "Le champ '" + e + "' est obligatoire et non renseigné.",
        max: (e) => e + " ne peut pas contenir plus de " + n[0] + " caractères."
      },

      attributes : {
        'rion.theorique' : 'Rion statuaire',
        'typeUtilisation' : "Type d'utilisation"
      }
    }
  }
});

Vue.use(VueRouter);
Vue.use(VueResource);

if(process.env.DEBUG_MODE) {
  Vue.http.options.root="http://localhost:8083/priam"
} else {
  Vue.http.options.root="/priambenmerzoukah"
}


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
