<template>
    <input
      ref="input"
      v-bind:value="value"
      v-on:input="updateValue($event.target.value)"
      v-on:focus="selectAll"
      v-on:blur="formatValue"
      :disabled="disabled"
      @keyup.enter="formatValue"
      class="tableInput numberInput"
      style="width: 100%"
      maxlength="13"
    >

</template>

<script>
  export default {
    props: {
      value: {
        type: Number,
        default: 0
      },

      disabled : {
        type : Boolean,
        default : true
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
      debugger;
      this.formatValue();
      this.mutableValue = this.value;
    },


    methods: {
      updateValue: function (value) {
          debugger;
        var result = currencyValidator.parse(value, this.value)
        if (result.warning) {

          this.$refs.input.value = result.value;
        }

        this.mutableValue = result.value;
        this.$emit('input', result.value)
      },

      formatValue: function () {
        debugger;
        //currencyValidator.format(this.value)
        this.$refs.input.value = this.$options.filters.numberFormat(this.value);
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
