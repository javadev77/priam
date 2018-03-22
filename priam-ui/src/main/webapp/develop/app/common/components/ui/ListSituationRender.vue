<template>
    <ul style="list-style: none;">
      <li v-for="item in value">
        <span v-html="item.situation"></span>
      </li>
    </ul>


</template>

<script>

  export  default {
    props : {
      value : Array
    },

    methods : {
      format(value) {
        debugger;
        return value;
      }
    }
  }

</script>
