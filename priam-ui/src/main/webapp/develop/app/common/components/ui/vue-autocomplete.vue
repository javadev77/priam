<template>
  <div :class="(className ? className + '-wrapper ' : '') + 'autocomplete-wrapper'">

    <div class="form-group has-feedback">
      <template v-if="isAlphaNumeric">
        <input  type="text"
                :id="id"
                :class="(className ? className + '-input ' : '') + 'autocomplete-input form-control  input-sm'"
                :placeholder="placeholder"
                v-model="type"
                @input="input(type)"
                @dblclick="showAll"
                @blur="hideAll"
                @keydown="keydown"
                @focus="focus"
                autocomplete="off"
                @keypress="numberKey"/>

      </template>
      <template v-else>

        <input  type="text"
                :id="id"
                :class="(className ? className + '-input ' : '') + 'autocomplete-input form-control  input-sm'"
                :placeholder="placeholder"
                v-model="type"
                @input="input(type)"
                @dblclick="showAll"
                @blur="hideAll"
                @keydown="keydown"
                @focus="focus"
                autocomplete="off"
        />
      </template>
      <i class="form-control-feedback glyphicon glyphicon-search"></i>
    </div>


    <div :class="(className ? className + '-list ' : '') + 'autocomplete transition autocomplete-list pull-left text-left'" v-show="showList">
      <ul>
        <li v-for="(data, i) in json"
            transition="showAll"
            :class="activeClass(i)">

          <a  href="#"
              @click.prevent="selectList(data)"
              @mousemove="mousemove(i)">
            <b>{{ data[anchor] }}</b>
            <span>{{ data[label] }}</span>
          </a>

        </li>
      </ul>
    </div>
  </div>
</template>


<script>
  /*! Copyright (c) 2016 Naufal Rabbani (http://github.com/BosNaufal)
   * Licensed Under MIT (http://opensource.org/licenses/MIT)
   *
   * Vue 2 Autocomplete @ Version 0.0.1
   *
   */
  /*!
   *  javascript-debounce 1.0.0
   *
   *  A lightweight, dependency-free JavaScript module for debouncing functions based on David Walsh's debounce function.
   *
   *  Source code available at: https://github.com/jgarber623/javascript-debounce
   *
   *  (c) 2015-present Jason Garber (http://sixtwothree.org)
   *
   *  javascript-debounce may be freely distributed under the MIT license.
   */

  import Vue from 'vue';

  let debounce = function(callback, delay) {
    let timeout;
    return function() {
      let context = this, args = arguments;
      clearTimeout(timeout);
      timeout = setTimeout(function() {
        callback.apply(context, args);
      }, delay);
    };
  };
  export default {
    props: {
      id: String,
      className: String,
      placeholder: String,
      // Intial Value
      initValue: {
        type: String,
        default: ""
      },
      // Anchor of list
      anchor: {
        type: String,
        required: true
      },
      // Label of list
      label: String,
      // Debounce time
      debounce: Number,
      // ajax URL will be fetched
      url: {
        type: String,
        required: true
      },
      // query param
      param: {
        type: String,
        default: 'q'
      },
      // Custom Params
      customParams: Object,
      // minimum length
      min: {
        type: Number,
        default: 0
      },
      // Process the result before retrieveng the result array.
      process: Function,
      // Callback
      onInput: Function,
      onShow: Function,
      onBlur: Function,
      onHide: Function,
      onFocus: Function,
      onSelect: Function,
      onBeforeAjax: Function,
      onAjaxProgress: Function,
      onAjaxLoaded: Function,
      isAlphaNumeric: {
        type: Boolean,
        default: false
      },
    },


    mounted() {

      this.resizeWidthComponent();
    },

    data() {
      return {
        showList: false,
        type: "",
        json: [],
        focusList: ""
      };
    },


    methods: {

      resizeWidthComponent() {
        var element = $(this.$el);
        var domEL = element.find('div.has-feedback').get(0);

        $(element.find('ul').get(0)).css('min-width' , $(domEL).css('width'));
      },

      // Netralize Autocomplete
      clearInput() {
        this.showList = false;
        this.type = "";
        this.json = [];
        this.focusList = "";
      },
      // Get the original data
      cleanUp(data){
        return JSON.parse(JSON.stringify(data));
      },
      input(val){
        this.showList = true;
        // Callback Event
        this.onInput ? this.onInput(val) : null;
        // Debounce the "getData" method.
        if(!this.debouncedGetData || this.debounce !== this.oldDebounce) {
          this.oldDebounce = this.debounce;
          this.debouncedGetData = this.debounce ? debounce(this.getData.bind(this), this.debounce) : this.getData;
        }
        // Get The Data
        this.debouncedGetData(val);
        this.resizeWidthComponent();
      },
      showAll(){
        this.json = [];
        this.getData("");
        // Callback Event
        this.onShow ? this.onShow() : null;
        this.showList = true;
        this.resizeWidthComponent();
      },
      hideAll(e){
        // Callback Event
        this.onBlur ? this.onBlur(e) : null;
        setTimeout(() => {
          // Callback Event
          this.onHide ? this.onHide() : null;
        this.showList = false;
      },250);
      },
      focus(e){
        this.focusList = 0;
        // Callback Event
        this.onFocus ? this.onFocus(e) : null;
      },
      mousemove(i){
        this.focusList = i;
      },
      keydown(e){
        let key = e.keyCode;
        // Disable when list isn't showing up
        if(!this.showList) return;
        switch (key) {
          case 40: //down
            this.focusList++;
            break;
          case 38: //up
            this.focusList--;
            break;
          case 13: //enter
            this.selectList(this.json[this.focusList]);
            this.showList = false;
            break;
          case 27: //esc
            this.showList = false;
            break;
        }
        // When cursor out of range
        let listLength = this.json.length - 1;
        this.focusList = this.focusList > listLength ? 0 : this.focusList < 0 ? listLength : this.focusList;
      },
      activeClass(i){
        return {
          'focus-list' : i == this.focusList
        };
      },
      selectList(data){
        let clean = this.cleanUp(data);
        // Put the selected data to type (model)
        this.type = clean[this.anchor];
        this.showList = false;
        /**
         * Callback Event
         * Deep clone of the original object
         */
        this.onSelect ? this.onSelect(clean) : null
      },
      getData(val){
        let self = this;
        if (val.length < this.min) return;
        if(this.url != null){
          // Callback Event
          this.onBeforeAjax ? this.onBeforeAjax(val) : null;
          let ajax = new XMLHttpRequest();
          let params = "";
          if(this.customParams) {
            Object.keys(this.customParams).forEach((key) => {
              params += `&${key}=${this.customParams[key]}`
          })
          }

          let nUrl = `${this.url}?${this.param}=${val}${params}`;

          ajax.open('GET', nUrl, true);

          ajax.withCredentials = true;
          ajax.setRequestHeader("Access-Control-Allow-Origin", "*");
          ajax.setRequestHeader("Content-Type", "application/json");
          ajax.send();
          ajax.addEventListener('progress', function (data) {
            if(data.lengthComputable){
              // Callback Event
              this.onAjaxProgress ? this.onAjaxProgress(data) : null
            }
          });
          ajax.addEventListener('loadend', function (data) {
            let json = JSON.parse(this.responseText);
            // Callback Event
            this.onAjaxLoaded ? this.onAjaxLoaded(json) : null;
            self.json = self.process ? self.process(json) : json;
          });
        }
      },
      setValue(val) {
        this.type = val
      },
      numberKey(event) {
        debugger;
        let charCode = (event.which) ? event.which : event.keyCode;
        if (charCode != undefined) {
          if (charCode > 31 && (charCode < 48 || charCode > 57)) {
            event.preventDefault();
          }
        }
      },
    },
    created(){
      // Sync parent model with initValue Props
      this.type = this.initValue ? this.initValue : null
    }
  }
</script>


<style>
  body > div > div:nth-child(2) > div > div:nth-child(1) > div:nth-child(3) > div.panel.panel-default > div.panel-collapse > div > form > div > div.form-group.col-md-6 > div > div > div.autocomplete.transition.autocomplete-list {
    /*position: fixed;*/
    text-align: left;
  }

</style>
