/**
 * Created by benmerzoukah on 08/11/2017.
 */
import { routes as cms } from './cms';
import { routes as copieprivee } from './copieprivee';
import { routes as common } from './common';
import { routes as cataloguecms } from './catalogue-cms';
import { routes as valorisation } from './valorisation';

export default [ ...copieprivee, ...cms, ...common, ...cataloguecms, ...valorisation];
