import Vue from 'vue'
import VueRouter from 'vue-router';
import VueResource from 'vue-resource';

import App from './App.vue'
import { routes } from './routes';
//import { fetchInitData } from './store/actions';
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
        max: (e, n) => e + " ne peut pas contenir plus de " + n[0] + " caractères.",
        numeric: (e) => "Le champ '" + e +  "' ne peut contenir que des chiffres."
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
  Vue.http.options.root="http://localhost:8080/priam"
} else {
  Vue.http.options.root = process.env.CONTEXT_ROOT;
}


const router = new VueRouter({
  routes
});


var waitingData = ['LIBELLE_UTILISATEUR', 'LIBELLE_FAMILLE', 'LIBELLE_TYPE_UTILSATION',
                    'FAMILLE_TYPE_UTILSATION_MAP', 'RIONS', 'TERRITOIRE_MAP', 'MIPSA_CONFIG', 'RIONS_CREATION', 'SET_CURRENT_USER'];
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

  /*if(process.env.DEBUG_MODE) {
   console.log("DEBUG MODE TRUE");
   var data = JSON.parse('[{"id":"CMS","value":"CMS"},{"id":"COPIEPRIV","value":"Copie Privée"},{"id":"FDSVAL","value":"Valorisation"}]');
   commit('SET_LIBELLE_FAMILLE', data);
   commit('SET_LIBELLE_TYPE_UTILSATION', JSON.parse('[{"id":"COPRIVSON","value":"Copie Privée Sonore"},{"id":"CPRIVAUDPL","value":"Copie Privée Audiovisuel - Part Littéraire"},{"id":"CPRIVAUDV","value":"Copie Privée Audiovisuelle"},{"id":"CPRIVSONPH","value":"Copie privée sonore Phono"},{"id":"CPRIVSONRD","value":"Copie Privée Sonore radio"},{"id":"ENCOURG","value":"Encouragement"},{"id":"PRIME","value":"Prime"},{"id":"VALORIS","value":"Fonds de valorisation"}]'));
   commit('SET_FAMILLE_TYPE_UTILSATION_MAP', JSON.parse('{"COPIEPRIV":[{"id":"COPRIVSON","value":"Copie Privée Sonore"},{"id":"CPRIVAUDPL","value":"Copie Privée Audiovisuel - Part Littéraire"},{"id":"CPRIVAUDV","value":"Copie Privée Audiovisuelle"},{"id":"CPRIVSONPH","value":"Copie privée sonore Phono"},{"id":"CPRIVSONRD","value":"Copie Privée Sonore radio"}],"FDSVAL":[{"id":"PRIME","value":"Prime"},{"id":"VALORIS","value":"Fonds de valorisation"}],"CMS":[{"id":"ENCOURG","value":"Encouragement"}]}'));
   commit('SET_RIONS', JSON.parse('[{"id":"639","value":"639"}]'));

   } else {*/

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
        bootstrapIfReady('SET_CURRENT_USER');
      }
    });
}

fetchInitData();

