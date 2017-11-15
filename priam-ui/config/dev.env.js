var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  DEBUG_MODE: true,
  CONTEXT_ROOT : '"http://localhost:8080/priambenmerzoukah"'
})
