<template>
  <div>
    <!--<select2 class="form-control"
           :options="pointsOptions"
           :value="value"
           :searchable="false" >
    </select2>-->

    <select class="tableInput numberInput"
            style="width: 100%"
            :disabled="disabled"
            :value="value"
            @change="updateValue($event.target.value)">
      <!--<slot></slot>-->
      <option v-for="opt in pointsOptions" :value="opt">
        {{ opt }}
      </option>
    </select>
  </div>

</template>

<script>


  export default {

      props : {
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

      computed : {
        pointsOptions() {
            //return [{id : 'p6', value:6}, {id : 'p12', value:12}, {id : 'p18', value:18}, {id : 'p24', value:24}, {id : 'p30', value:30}];
          return [6, 12, 18, 24, 30];
        }
      },

     methods : {

       updateValue(valueChanged) {
         let val = parseInt(valueChanged);
         this.mutableValue = val;
         this.$emit('input', val);
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

     mounted() {
       this.value= parseInt(this.value);
       this.mutableValue = this.value;

     }
  }

</script>
