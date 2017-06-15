<template>
  <input class="form-control datepicker from" type="text" size="10" :placeholder="placeHolder"/>
</template>

<script>
  import moment from 'moment';

  export default {

      props : ['dateFormat', 'placeHolder'],

      mounted() {
        this.initDatePicker();
        var self = this;
        $(this.$el).datepicker({
          dateFormat: this.dateFormat,
          onSelect: function(date) {
            var val = self.stringToDate(date);
            console.log("DATE: " + val);
            self.$emit('update-date', val);
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

              // Initialize Highcharts default values
              /*Highcharts.setOptions({
                credits: {enabled: false},
                title: {text: null},
                subtitle: {text: null}
              });*/
          },

          stringToDate(str) {
              /*if (typeof str === 'string' && str.length >= 11) {
                //format like "2015-10-04T00:00:00.000+0000"
                str = moment(str).format('yyyy-MM-dd');
              }*/
              var date = moment(str, 'dd/mm/yyyy');
              console.log("str="+date);
              if (typeof str === 'string' && str.length > 3 && str.length < 11) {
                  var parts = str.split("/");
                  return new Date(Date.UTC(parts[2], parts[1], parts[0], 0, 0, 0, 0));
              }
              return null;
          }


      },

      beforeDestroy() {
        $(this.$el).datepicker('hide').datepicker('destroy');
      }

  }

</script>
