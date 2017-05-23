<template>
    <div>
        <input type="checkbox" checked="checked"/> {{ mutableLebel }}
         {{ checked }}
    </div>
</template>



<script>
  export default {

      props : ['checked', 'disabled', 'label', 'value'],

      mounted() {
        var vm = this;
        $(() => {

          var element = $(this.$el);
          element.checkbox();

          function _check(newValue) {
            element.checkbox(newValue ? 'check' : 'uncheck');
          }

          var checked = this.checked;
          if (typeof checked === 'boolean') {
            _check(checked);
          }

        });
      },

      watch : {

          checked : function () {
            $(() => {
              var element = $(this.$el);
              element.checkbox(newValue ? 'check' : 'uncheck');
            });
          },

          disabled : function () {
            $(() => {
              var element = $(this.$el);
              var parent = element.parent();
              element.checkbox(disabled ? 'disable' : 'enable');
              if(disabled){
                parent.addClass('disabled');
              }else{
                parent.removeClass('disabled');
              }

            });
          }

      },

      created () {
        this.mutableLebel = this.label;
      },

      data() {
          return {
            mutableLebel : ''
          }
      }



  }
</script>

<style>

</style>
