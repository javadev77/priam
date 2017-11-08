import Chargement from './components/chargement/Chargement.vue';
import Affectation from './components/affectation/Affectation.vue';
import ListeProgramme from './components/programme/ListeProgramme.vue';
import Selection from './components/selection/SelectionProgramme.vue';
import Parametrage from './components/parametrage/Parametrage.vue';

export const routes = [
    { path: '/', redirect: '/programme/listePrg' },
    { path: '/chargement', name :'chargement', component: Chargement },
    { path: '/programme', name: 'programme', component : ListeProgramme,
      children: [
        {
          path: 'listePrg',
          name: 'ListePrg',
          component: ListeProgramme
        }
      ]
    },

    { path: '/parametrage', name: 'parametrage', component: Parametrage },
    { path: '/programme/affectation/:numProg', name: 'affectation', component: Affectation },
    { path: '/programme/selection/:numProg', name: 'selection', component: Selection }


];
