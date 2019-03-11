import * as components from './components';

export default [
  { path: '/programme/valorisation/affectation/:numProg', name: 'affectation-fv', component: components.EcranAffectationFV },
  // { path: '/programme/valorisation/selection/:numProg', name: 'selection-fv', component: components.EcranSelectionAyantDroit },
 { path: '/programme/valorisation/selectionOeuvre/:numProg', name: 'selection-fv-oeuvre', component: components.EcranSelectionOeuvre},
  { path: '/programme/valorisation/selectionOeuvreAD/:numProg', name: 'selection-fv-oeuvre-ad', component: components.EcranSelectionOeuvreAD},
  { path: '/programme/valorisation/selectionAD/:numProg', name: 'selection-fv-ad', component: components.EcranSelectionAyantDroit}
]
