import Home from './components/Home.vue';
import Chargement from './components/chargement/Chargement.vue';
import Affectation from './components/affectation/Affectation.vue';
import ListeProgramme from './components/programme/ListeProgramme.vue';

export const routes = [
    { path: '/', redirect: '/programme' },
    { path: '/chargement', name :'chargement', component: Chargement },
    { path: '/programme', name: 'programme', component: ListeProgramme,
      children: [
        {
          path: 'listePrg',
          name: 'ListePrg',
          component: ListeProgramme
        },
        {
          path: 'createProg',
          name : 'createProg',
          component: Affectation
        }
      ]
    },
    { path: '/parametrage', name: 'parametrage', component: Chargement },
    { path: '/affectation/:numProg', name: 'affectation', component: Affectation }
];
