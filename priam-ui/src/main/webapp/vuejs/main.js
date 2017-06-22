import Vue from 'vue'
import VueRouter from 'vue-router';
import VueResource from 'vue-resource';

import App from './App.vue'
import { routes } from './routes';
import { fetchInitData } from './store/actions';
import store from './store/store';
import './directives/form-elements'
import './utils/Arrays'
import VeeValidate ,{Validator} from 'vee-validate';
import messagesFr from './strings/validator/fr.js';

Vue.use(VeeValidate, {
  locale: 'fr',
  dictionary: {
    fr: { messages: messagesFr, attributes: messagesFr }
  }
});
Vue.use(VueRouter);
Vue.use(VueResource);



if(process.env.DEBUG_MODE) {
  Vue.http.options.root="http://localhost:8090/priam"
} else {
  Vue.http.options.root="/priam"
}


const router = new VueRouter({
  routes
});
const dictionary = {

  fr: {
    messages: {
      required: () => 'ffffffffffff'
    }
  }
};
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
