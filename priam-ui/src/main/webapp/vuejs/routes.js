import Home from './components/Home.vue';
import Chargement from './components/chargement/Chargement.vue';
import Affectation from './components/affectation/Affectation.vue';

export const routes = [
    { path: '/', component: Chargement },
    { path: '/chargement', component: Chargement },
    { path: '/affectation', component: Affectation }
];
