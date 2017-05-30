import Home from './components/Home.vue';
import Chargement from './components/chargement/Chargement.vue';
import Affectation from './components/affectation/Affectation.vue';

export const routes = [
    { path: '/', name :'home', component: Affectation },
    { path: '/chargement', name :'chargement', component: Chargement },
    { path: '/programme', name: 'programme', component: Affectation,
      children: [
        {
          path: 'listePrg',
          name: 'ListePrg',
          component: Affectation
        },
        {
          path: 'CreateProg',
          name : 'CreateProg',
          component: Affectation
        }
      ]
    },
    { path: '/parametrage', name: 'parametrage', component: Affectation }
];
