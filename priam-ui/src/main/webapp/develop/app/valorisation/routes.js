import * as components from './components';

export default [
  { path: '/programme/valorisation/affectation/:numProg', name: 'affectation-fv', component: components.EcranAffectationFV },
  // { path: '/programme/valorisation/selection/:numProg', name: 'selection-fv', component: components.EcranSelectionAyantDroit },
  { path: '/programme/valorisation/selectionOeuvre/:numProg', name: 'selection-fv-oeuvre', component: components.EcranSelectionOeuvre},
  { path: '/programme/valorisation/selectionAyantDroit/:numProg', name: 'selection-fv-ayant_droit', component: components.EcranSelectionAyantDroit}
]
