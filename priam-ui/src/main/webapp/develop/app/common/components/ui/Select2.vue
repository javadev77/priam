<template>
  <select>
    <!--<slot></slot>-->
    <option v-for="opt in options" :value="opt.id">{{ opt.value }}</option>
  </select>
</template>

<script>
  import 'select2';
  import 'select2/select2_locale_fr';

  export default {

    props: ['options', 'value', 'searchable'],

    data() {

        return  {
            select2initializer : {
              dropdownAutoWidth: 'true',
              minimumInputLength: 0,
              minimumResultsForSearch : 0
            }
        }

    },

    methods: {
      refreshSelect2() {
        var element = $(this.$el);
        element.select2('destroy') ;
        element.select2(this.select2initializer) ;
        element.select2('container').css('display', 'block');


      }
    },

    mounted: function () {
      var vm = this;
      var element = $(this.$el);
      element.select2(this.select2initializer);


        //.select2({ data: this.options })
      element.val(this.value);
      element.trigger('change');
        // emit event on change.
      element.on('change', function (e) {
          //console.log("change==>"  + this.value);

          console.log("change " + this.value);
          vm.$emit('input', this.value);
        });


      if (!this.searchable) {
        this.select2initializer.minimumResultsForSearch = -1 ;
      }

      element.addClass('select select-primary');

    },

    watch: {
      value: function (value) {
        // update value
        console.log("value changed")
        $(this.$el).val(value).trigger('change');
      },

      options: function (options) {
          this.refreshSelect2();
      }
    },

    destroyed: function () {
      $(this.$el).off().select2('destroy')
    }

  }

</script>
