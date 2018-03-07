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

  import Select2 from '../../../../common/components/ui/Select2.vue';

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

      computed : {
        pointsOptions() {
            //return [{id : 'p6', value:6}, {id : 'p12', value:12}, {id : 'p18', value:18}, {id : 'p24', value:24}, {id : 'p30', value:30}];
          return [6, 12, 18, 24, 30];
        }
      },

     methods : {

       updateValue(valueChanged) {
         this.$emit('input', parseInt(valueChanged));
       }
     },

     mounted() {
       this.value= parseInt(this.value);

     },

      components : {
        select2 :Select2
      }
  }

</script>
