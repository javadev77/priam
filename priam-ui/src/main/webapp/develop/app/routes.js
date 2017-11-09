/**
 * Created by benmerzoukah on 08/11/2017.
 */
import { routes as cms } from './cms';
import { routes as copieprivee } from './copieprivee';
import { routes as common } from './common';

export default [ ...copieprivee, ...cms, ...common ];
