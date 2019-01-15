<template>
    <input
      ref="input"
      v-bind:value="value"
      v-on:input="updateValue($event.target.value)"
      v-on:focus="selectAll"
      v-on:blur="formatValue"
      class="tableInput numberInput"
      style="width: 100%"
      maxlength="10"
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

    data() {

      return {
        mutableValue : null
      }

    },

    watch : {

      value : function (newValue) {
        this.mutableValue = newValue;

      },

      mutableValue : function (newVal, oldVal) {
        if(newVal !== oldVal) {
          this.$emit('valueChanged', {oldVal  : oldVal, newVal : newVal});
        }
      }

    },


    mounted: function () {
      this.formatValue()
      this.mutableValue = this.value;
    },

    methods: {

      updateValue: function (value) {

        var number = +value.replace(/[^\d.]/g, '');
        var result =  isNaN(number) ? 0 : parseInt(number);

        if (result !== undefined) {
          this.$refs.input.value = result;
        }
        this.mutableValue = result;
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
