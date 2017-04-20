<template>
    <div>
        <h1>Bienvenue sur PRIAM</h1>
    </div>
</template>

<script>
    export default {
        computed: {
            funds() {
                return this.$store.getters.funds;
            }
        }
    }
</script>
