<template>
    <input
      ref="input"
      v-bind:value="value"
      v-on:input="updateValue($event.target.value)"
      v-on:focus="selectAll"
      v-on:blur="formatValue"
      class="tableInput numberInput"
      style="width: 100%"

    >

</template>

<script>
  export default {
    props: {
      value: {
        type: Number,
        default: 0
      },
      label: {
        type: String,
        default: ''
      }
    },

    mounted: function () {
      this.formatValue()
    },

    methods: {

      updateValue: function (value) {

        var number = +value.replace(/[^\d.]/g, '');
        var result =  isNaN(number) ? 0 : parseInt(number);

        if (result !== undefined) {
          this.$refs.input.value = result;
        }
        this.$emit('input', result)
      },

      formatValue: function () {
        this.$refs.input.value = new Intl.NumberFormat("fr-FR", { maximumFractionDigits: 2 }).format(this.value);
      },

      selectAll: function (event) {
        // Workaround for Safari bug
        // http://stackoverflow.com/questions/1269722/selecting-text-on-focus-using-jquery-not-working-in-safari-and-chrome
        setTimeout(function () {
          event.target.select()
        }, 0)
      }
    }
  }
</script>
