<template>
  <select class="form-control" v-model="optSelected">
    <option v-for="opt in mutableOptions" :value="opt.id">
      {{ opt.value }}
    </option>
  </select>
</template>

<script>

  export default {

      props : {
          multiple : {
              type : Boolean,
              default :false
          },

          noSearch : {
            type : Boolean,
            default :false
          },

          options: {
            type: Array,
            default() {
              return []
            },
          }
      },

      data() {

          return {
              optSelected: null,
              mutableOptions : []
          }

      },

      created() {

        this.mutableOptions = this.options;

      },

      mounted() {
          var self = this;
        var select2initializer = {
          dropdownAutoWidth: 'true',
          minimumInputLength: 0
        };

        if (this.noSearch !== undefined && !this.noSearch) {
          select2initializer.minimumResultsForSearch = -1 ;
        }

        var element = $(this.$el);
        if (this.multiple && element.hasClass('no-drop-down')) {
          select2initializer.dropdownCssClass = 'ng-hide';
        }

        function refreshSelect2(newValue, oldValue, select2initializer) {
          element.select2('destroy') ;
          element.select2(select2initializer) ;
          element.select2('container').css('display', 'block');

          //multiple
          if (self.multiple) {
            element.select2('container').find('a').removeAttr('href');
            if(element.hasClass('no-drop-down')){
              element.select2('container').on('click', function () {
                element.select2('close');
              });
            }
          }
        }

        element.addClass('select select-primary');
        //scope.$watch('model', refreshSelect2, false);

        /*if(angular.isDefined(scope.watchOptions)){
          scope.$watch('watchOptions', refreshSelect2, true);
        }

        if (typeof scope.disabled === 'boolean') {
          scope.$watch('disabled', function (disabled) {
            element.select2('enable', !disabled);
          });
        }*/

      },

      watch : {

      },

      methods : {

      },

      beforeDestroy() {
          $(this.$el).select2('destroy') ;
      }

  }

</script>
