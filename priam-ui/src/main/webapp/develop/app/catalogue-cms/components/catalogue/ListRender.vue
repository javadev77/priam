<template>
  <div>
    <template v-for="elem in data">
      <span>&nbsp;•&nbsp;</span>
      <span> {{ elem }} </span>
      <br/>
    </template>
  </div>

</template>

<script>

export default {
    props : {

        data : {
            type : Array,
          default :[]
        }
    }
}

</script>
