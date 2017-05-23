<template>
    <div class="container-fluid">
        <app-header></app-header>
        <div class="container-fluid">
                <transition name="slide" mode="out-in">
                    <router-view></router-view>
                </transition>
        </div>
    </div>
</template>

<script>
    import Header from './components/Header.vue';
    export default {
        components: {
            appHeader: Header
        }
    }
</script>

