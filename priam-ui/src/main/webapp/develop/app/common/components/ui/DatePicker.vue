<template>
  <!--<masked-input v-model="dateValue" class="form-control datepicker from" mask="11/11/1111" size="10" :placeholder="placeHolder" />-->
  <input class="form-control datepicker from" size="10" :placeholder="placeHolder" />
</template>

<script>
  import moment from 'moment';

  export default {


      props : ['dateFormat', 'placeHolder', 'zeroHour', 'value'],

      data() {

          return {
              dateValue : '',
              isZeroHour : false
          }

      },

      mounted() {
          this.isZeroHour =  this.zeroHour;
          this.initDatePicker();
          var self = this;
          var element = $(this.$el);

          element.attr('maxlength', '10') ;
          element.attr('pattern', '^[0-9]{2}/[0-9]{2}/[0-9]{4}$') ;

          element.datepicker({
            dateFormat: this.dateFormat,
            onSelect: function(date) {
              var val = self.stringToDate(date);
              self.$emit('input', val);
            }
          }).mask("99/99/9999", {
              completed : function() {
                var date = this.val();

                var val = self.stringToDate(date);
                element.datepicker('setDate', val);
                self.$emit('input', val);
              }

          });

         console.log("initial Date Value = " + self.value);

         element.val(self.dateToString(self.value));


        element.on('keydown', function (e) {
          console.log('keydown event fired !!!');
          if (e.which === 13) {
            element.datepicker('hide');
          }
        });

        element.on('change' , function (e) {
          console.log('change event fired !!!');
          let val = element.val();
          if(val === '' || val.length === 0) {
              element.datepicker('setDate', null);
              self.$emit('input', null);
            }
        });


      },

      methods : {

          initDatePicker() {
              // Initialize jquery date picker
            $.datepicker.regional.fr = {
                closeText: 'Fermer',
                prevText: 'Précédent',
                nextText: 'Suivant',
                currentText: 'Aujourd\'hui',
                monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin',
                  'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
                monthNamesShort: ['Janv.', 'Févr.', 'Mars', 'Avril', 'Mai', 'Juin',
                  'Juil.', 'Août', 'Sept.', 'Oct.', 'Nov.', 'Déc.'],
                dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
                dayNamesShort: ['Dim.', 'Lun.', 'Mar.', 'Mer.', 'Jeu.', 'Ven.', 'Sam.'],
                dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],
                weekHeader: 'Sem.',
                dateFormat: 'dd/mm/yy',
                firstDay: 1,
                isRTL: false,
                showMonthAfterYear: false,
                yearSuffix: '',
                autoSize: true
              };

            $.datepicker.setDefaults($.datepicker.regional.fr);

          },

          stringToDate(str) {
              if (typeof str === 'string' && str.length > 3 && str.length < 11) {
                  var parts = str.split("/");
                  var date = null;
                  if(this.isZeroHour) {
                      date = moment.utc([parts[2], parts[1] - 1, parts[0], 0, 0, 0, 0]).toDate();

                  } else {

                      date = moment.utc([parts[2], parts[1] - 1, parts[0], 23, 59, 59, 999]).toDate();
                  }

                  debugger;
                  return date;

              }
              return null;
          },

        dateToString(date) {
          debugger;
             if(date !== null) {
               var m = moment.utc(date);
               return m.format("DD/MM/YYYY");
             }

             return null;
        }


      },

      watch : {

        value: function (dateValue) {
            console.log("The date value changed !!! " + dateValue );
            let formattedDate = this.dateToString(dateValue);
            let element = $(this.$el);
            element.val(formattedDate);


            element.datepicker('setDate', formattedDate);
            debugger;
            this.$emit('input', dateValue);
        },

        dateValue : function (value) {
          if (typeof value === 'string' && value.length === 10) {
              let element = $(this.$el);
              element.val(value);
              let date = this.stringToDate(value);

            debugger;
              element.datepicker('setDate', date);
              this.$emit('input', date);
          }
        }

      },

      beforeDestroy() {
          $(this.$el).datepicker('hide').datepicker('destroy');
      }

  }

</script>
