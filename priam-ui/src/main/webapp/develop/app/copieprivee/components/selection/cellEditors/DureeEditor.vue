<template>
  <div>
    <input
      ref="inputDureeEdit"
      v-bind:value="value"
      @input="updateValue($event.target.value)"
      @focus="selectAll"
      @blur="formatValue"
      :disabled="disabled"
      @keyup.enter="formatValue"
      class="tableInput numberInput"
      style="width: 100%"
      maxlength="10"
    >
  </div>

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
      this.formatValue();
      this.mutableValue = this.value;
    },


    methods: {

      updateValue: function (value) {
        debugger;
        var result = parseInt(value, 10);

        if (isNaN(result)) {
          this.$refs.inputDureeEdit.value ='';
          this.mutableValue = null;
          this.$emit('input', '')
        } else {
          this.mutableValue = result;
          this.$emit('input', result);
        }

      },

      formatValue: function () {
          debugger;
          if(!isNaN(this.value)) {
            let jours = Math.floor( this.value / 86400);
            let reste = this.value % 86400;
            let hours = Math.floor( reste / 3600);
            reste = reste % 3600;
            let minutes = Math.floor(reste / 60);
            let seconds = reste % 60;


            let result = ((jours < 10) ? '0'+jours : jours) + 'j ' +((hours < 10) ? '0'+hours : hours)+"h "+((minutes < 10) ? '0' + minutes: minutes)+"m "+ ((seconds < 10) ? '0'+seconds : seconds) + "s";
            debugger;
            this.$refs.inputDureeEdit.value = result;
          } else {
            this.$refs.inputDureeEdit.value = "0";
          }

      },

      selectAll: function (event) {
        debugger;
        // Workaround for Safari bug
        // http://stackoverflow.com/questions/1269722/selecting-text-on-focus-using-jquery-not-working-in-safari-and-chrome
        debugger;
        setTimeout(function () {
          event.target.select()
        }, 0)
      }
    }
  }
</script>
