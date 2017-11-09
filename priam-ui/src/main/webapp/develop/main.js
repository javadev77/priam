import Vue from 'vue'
import VueResource from 'vue-resource';
import vueShortkey from 'vue-shortkey'
import { App } from './app';
import router from './router';
import store from './store';
import './utils'
import './filters'
import './validators'

Vue.use(vueShortkey);
Vue.use(VueResource);

if(process.env.DEBUG_MODE) {
  Vue.http.options.root="http://localhost:8080/priam"
} else {
  Vue.http.options.root = process.env.CONTEXT_ROOT;
}



var waitingData = ['LIBELLE_UTILISATEUR', 'LIBELLE_FAMILLE', 'LIBELLE_TYPE_UTILSATION',
                   'FAMILLE_TYPE_UTILSATION_MAP', 'RIONS', 'TERRITOIRE_MAP', 'MIPSA_CONFIG',
                   'RIONS_CREATION', 'CURRENT_USER', 'SELECT_PAGE_SIZE', 'APP_INFO'];
function bootstrapIfReady(type) {
  var index = waitingData.indexOf(type);
  if (index > -1) {
    waitingData.splice(index, 1);
  }else{
    console.error(type + ' is not registered in waiting list');
  }

  if (waitingData.length === 0) {
    new Vue({
       el: '#app',
       router,
       store,
       render: h => h(App)
     });
  }
}
/*********************************************
 * ******** Fetch init Data only Once ********
 * ********************************************/


function fetchInitData() {

  Vue.http.get('app/rest/general/libellefamille')
    .then(response => response.json())
    .then(data => {
      if (data) {

        store.commit('SET_LIBELLE_FAMILLE', data);
        bootstrapIfReady('LIBELLE_FAMILLE');
      }
    });

  Vue.http.get('app/rest/general/libelletypeutil')
    .then(response => response.json())
    .then(data => {
      if (data) {
        store.commit('SET_LIBELLE_TYPE_UTILSATION', data);

        bootstrapIfReady('LIBELLE_TYPE_UTILSATION');
      }
    });

  Vue.http.get('app/rest/general/familleByTypeUil')
    .then(response => response.json())
    .then(data => {
      if (data) {
        store.commit('SET_FAMILLE_TYPE_UTILSATION_MAP', data);
        bootstrapIfReady('FAMILLE_TYPE_UTILSATION_MAP');
      }
    });

  Vue.http.get('app/rest/general/rions')
    .then(response => response.json())
    .then(data => {
      if (data) {
        store.commit('SET_RIONS', data);
        bootstrapIfReady('RIONS');
      }
    });

  Vue.http.get('app/rest/general/rions_creation')
    .then(response => response.json())
    .then(data => {
      if (data) {
        store.commit('SET_RIONS_CREATION', data);
        bootstrapIfReady('RIONS_CREATION');
      }
    });

  Vue.http.get('app/rest/general/territoire')
    .then(response => response.json())
    .then(data => {
      if (data) {
        store.commit('SET_TERRITOIRE_MAP', data);
        bootstrapIfReady('TERRITOIRE_MAP');
      }
    });

  Vue.http.get('app/rest/general/config/mipsa')
    .then(response => response.json())
    .then(data => {
      if (data) {
        store.commit('SET_MIPSA_CONFIG', data);
        bootstrapIfReady('MIPSA_CONFIG');
      }
    });


  Vue.http.get('app/rest/general/libelleUtilisateur')
    .then(response => response.json())
    .then(data => {
      if (data) {
        store.commit('SET_LIBELLE_UTILISATEUR', data);
        bootstrapIfReady('LIBELLE_UTILISATEUR');
      }
    });

  Vue.http.get('app/rest/general/currentUser')
    .then(response => response.json())
    .then(data => {
      if (data) {
        store.commit('SET_CURRENT_USER', data);
        bootstrapIfReady('CURRENT_USER');

        Vue.http.get('app/rest/general/parametres')
          .then(response => response.json())
          .then(data => {
          if (data) {
            store.commit('SELECT_PAGE_SIZE', data.USER_PAGE_SIZE ? data.USER_PAGE_SIZE : store.getters.userPageSize );
            store.commit('SELECT_FAMILLE', data.USER_FAMILLE ? data.USER_FAMILLE : store.getters.userFamille);
            bootstrapIfReady('SELECT_PAGE_SIZE');
          }
        });
      }
    });

  Vue.http.get('app/rest/general/appinfo')
    .then(response => response.json())
    .then(data => {
      if (data) {
        store.commit('SET_APP_INFO', data);
        bootstrapIfReady('APP_INFO');
      }
    });

}

fetchInitData();

