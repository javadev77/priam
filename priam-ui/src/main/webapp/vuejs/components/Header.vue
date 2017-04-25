<template>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <router-link to="/" class="navbar-brand">PRIAM</router-link>
            </div>


            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <router-link to="/portfolio" activeClass="active" tag="li"><a>Chargement</a></router-link>
                    <router-link to="/stocks" activeClass="active" tag="li"><a>Affectation</a></router-link>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li
                            class="dropdown"
                            :class="{open: isDropdownOpen}"
                            @click="isDropdownOpen = !isDropdownOpen">
                        <a
                                href="#"
                                class="dropdown-toggle"
                                data-toggle="dropdown"
                                role="button"
                                aria-haspopup="true"
                                aria-expanded="false"><strong>Bonjour : PRIAM <span class="caret"></span></strong></a>
                        <ul class="dropdown-menu">
                            <li><a href="#" @click="saveData">Déconnexion</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</template>

<script>
    import {mapActions} from 'vuex';

    export default {
        data() {
          return {
              isDropdownOpen: false
          }
        }
    }
</script>
