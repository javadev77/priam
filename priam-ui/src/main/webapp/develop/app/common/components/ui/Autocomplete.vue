<template>
  <div style="position:relative" :class="{'open':openSuggestion}">
    <input class="form-control" type="text" v-model="selectedValue"
           @keydown.enter = 'enter'
           @keydown.down = 'down'
           @keydown.up = 'up'
           @input = 'change'
    />
    <ul class="dropdown-menu" style="width:100%">
      <li v-for="(suggestion, index) in matches"
          :class="{'active': isActive(index)}"
          @click="suggestionClick(index)"
      >
        <a href="#">{{ suggestion }}</a>
      </li>
    </ul>
  </div>
</template>

<script>
  import Vue from 'vue';
  export default {
    data() {
      return {
        open: false,
        current: 0,
        selectedValue : ''
      }
    },

    created() {
      this.selectedValue = this.selection;
    },

    props: {
      suggestions: {
        type: Array,
        required: true
      },

      selection: {
        type: String,
        default : '',
        required: true
      }
    },

    computed: {
      matches() {
        return this.suggestions.filter((str) => {
          return str.indexOf(this.selectedValue) >= 0;
        });
      },
      openSuggestion() {
        return this.selectedValue !== "" &&
          this.matches.length != 0 &&
          this.open === true;
      }
    },
    methods: {
      enter() {
        this.selectedValue = this.matches[this.current];
        this.open = false;
        this.$emit('selected-value', this.selectedValue);
      },

      up() {
        if(this.current > 0)
          this.current--;
      },

      down() {
        if(this.current < this.matches.length - 1)
          this.current++;
      },

      isActive(index) {
        return index === this.current;
      },

      change() {
        if (this.open == false) {
          this.open = true;
          this.current = 0;
        }
      },

      suggestionClick(index) {
        this.selectedValue = this.matches[index];
        this.open = false;
        this.$emit('selected-value', this.selectedValue);
      },
    }
  }
</script>
