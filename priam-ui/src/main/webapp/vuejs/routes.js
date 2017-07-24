import Chargement from './components/chargement/Chargement.vue';
import Affectation from './components/affectation/Affectation.vue';
import ListeProgramme from './components/programme/ListeProgramme.vue';
import Selection from './components/selection/SelectionProgramme.vue';

export const routes = [
    { path: '/', redirect: '/programme/listePrg' },
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
    { path: '/parametrage', name: 'parametrage', component: Selection },
    { path: '/programme/affectation/:numProg', name: 'affectation', component: Affectation },
    { path: '/programme/selection/:numProg', name: 'selection', component: Selection }

];
