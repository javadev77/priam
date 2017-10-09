<template>
  <masked-input v-model="dateValue" class="form-control datepicker from" mask="11/11/1111" size="10" :placeholder="placeHolder" />
</template>

<script>
  import moment from 'moment';
  import MaskedInput from 'vue-masked-input';

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
          console.log("this.dateFormat="+this.dateFormat)
          element.datepicker({
            dateFormat: this.dateFormat,
            onSelect: function(date) {
              var val = self.stringToDate(date);
              self.$emit('update-date', val);
            }
          });


        console.log("value="+ this.value);
        element.val(this.value);

          var inputValidate = function() {
            var regExp = new RegExp(/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/);
            var validDate = regExp.test(element.val());
            if(validDate){
              var parts = element.val().split('/');
              var _year = parseInt(parts[2]);
              var _month = parseInt(parts[1]) - 1;
              var _date = parseInt(parts[0]);
              /*if(angular.isDefined(attrs.minDate)){
                try {
                  var minDate = new Date();
                  minDate.setDate( minDate.getDate() + parseInt( options.minDate ) );
                  _year = Math.max( minDate.getYear(), _year );
                  _month = Math.max( minDate.getMonth(), _month );
                  _date = Math.max( minDate.getDate(), _date );
                } catch ( e ) {
                  //ignore
                }
              }*/
              //var value = new Date(Date.UTC(_year, _month, _date, 0, 0, 0, 0));
              //var newStringVal = $filter( 'date')( value, 'dd/MM/yyyy');
              //ngModelCtrl.$setViewValue(value);

              /*if(element.val() !== newStringVal){
                element.val( newStringVal );
              }*/
            }else {
              element.val('');
            }
          } ;

          element.bind('keypress', function(e){
            var code = e.which||e.charCode||e.keyCode ;
            // backspace, suppr, arrow left and right, enter, tab, F8, start, end
            var special = [8,46,37,39,13,9,119,36,35] ;
            if ( special.indexOf(code) > -1  ) {
              return ;
            }

            var inputchar = '0123456789/:';
            var char = String.fromCharCode(code) ;
            if ( inputchar.indexOf(char) < 0 ) {
              e.preventDefault() ;
            }
          });

          element.on('keydown', function (e) {
            if (e.which === 13) {
              inputValidate() ;
            }
          });
          element.on('blur', inputValidate);

          element.change(function () {
              console.log('onChange element')

              var date = $(this).val();
              if(typeof date === 'string' && date.length > 0) {
                  date = self.stringToDate(date);
                  if ( date === null ) {
                    throw new Error('value must be a Date, or a String object with a date formatter - currently it is a ' + typeof date + ' - use ui-date-format to convert it from a string');
                  } else {
                    element.datepicker('setDate', date);
                    self.$emit('update-date', date);
                  }
              } else {
                if ( element.datepicker ) {
                  element.datepicker('setDate', null);
                  self.$emit('update-date', null);
                }
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
                      //date =  new Date(Date.UTC(parts[2], parts[1] - 1, parts[0], 0, 0, 0, 0));
                  } else {
                      //date =  new Date(Date.UTC(parts[2], parts[1] - 1, parts[0], 23, 59, 59, 999));
                      date = moment.utc([parts[2], parts[1] - 1, parts[0], 23, 59, 59, 999]).toDate();
                  }

                  return date;

              }
              return null;
          },

        dateToString(date) {
             if(date !== null) {
               var m = moment.utc(date);
               return m.format("DD/MM/YYYY");
             }

             return '';
        }


      },

      watch : {

        value: function (value) {
            console.log("update value = " + value);

            let formattedDate = this.dateToString(value);
            console.log("formatted update value = " + formattedDate);

            $(this.$el).val(formattedDate);
        },

      },

      beforeDestroy() {
          $(this.$el).datepicker('hide').datepicker('destroy');
      },

      components: {
        MaskedInput
      }


  }

</script>
