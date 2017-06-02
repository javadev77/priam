<template>
  <div class="row sacem-pager">
    <div class="col-sm-24">
      <div class="results pull-left">
        {{ totalItems || 0 }} résultat{{ totalItems > 1 ? 's' : '' }}
        - Page <input style="width: 20px; height: 20px;" :value="currentPage"
                      type="number"
                      ref="input"
                      @keyup.enter="gotoInputPage($event.target.value)"> / {{ totalPages }}
        <!--@input="updateValue($event.target.value)"-->
            <span>
                - Résultats par page
              <select v-model="itemsPerPage" @change="pageSizeChanged()">
                <option v-for="s in sizes"> {{ s }}</option>
              </select>
            </span>
      </div>
      <div class="pull-right">
        <ul class="pagination-plain no-select">
          <li class="previous" :class="currentPage === 1 ? 'active': ''">
            <a @click.prevent="selectPage(currentPage - 1)" aria-label="Previous">
              &leftarrow;
            </a>
          </li>

          <li v-for="p in pages" :class="currentPage === p ? 'active': ''">
            <a @click.prevent="selectPage(p)" >{{ p  }}</a>
          </li>

          <li class="next" :class="currentPage === totalPages ? 'active': ''">
            <a  @click.prevent="selectPage(currentPage + 1)" aria-label="Next">
              &rightarrow;
            </a>
          </li>
      </ul>
      </div>

      <div class="pull-right second-message">
        </span>&#160; &#160;</span>
      </div>
    </div>
  </div>
</template>

<script>

  import Util from '../services/Utils'

  export default {

    props: {
      // Current Page
      currentPage: {
        type: Number,
        required: true,
        default : 1
      },
      // Total page
      totalPages: Number,
      // Items per page
      itemsPerPage: Number,
      // Total items
      totalItems: Number,
      // Visible Pages
      visiblePages: {
        type: Number,
        default: 5,
        coerce: (val) => parseInt(val)
      },

      disabled : {
        type: Boolean,
        default: false
      }
    },

    data () {

      return {
        pageSize : 25,
        sizes : [25, 50, 100]
      }
    },

    computed: {
      lastPage () {
        if (this.totalPages) {
          return this.totalPages
        } else {
          return this.totalItems % this.itemsPerPage === 0
            ? this.totalItems / this.itemsPerPage
            : Math.floor(this.totalItems / this.itemsPerPage) + 1
        }
      },
      pages () {
        /*let start = this.currentPage - this.visiblePages / 2 <= 0
          ? 1 : this.currentPage + this.visiblePages / 2 > this.lastPage
            ? Util.lowerBound(this.lastPage - this.visiblePages + 1, 1)
            : Math.ceil(this.currentPage - this.visiblePages / 2)
        let range = []
        for (let i = 0; i < this.visiblePages && i < this.lastPage; i++) {
          range.push(start + i)
        }
        return range*/

        let pages = [];

        if (this.currentPage <= 0 || this.currentPage > this.totalPages) {
          return pages;
        }

        // Default page limits
        let startPage = 1, endPage = this.totalPages;
        let isMaxSized = this.visiblePages !== undefined && this.visiblePages < this.totalPages;

        // recompute if maxSize
        if (isMaxSized) {
            // Current page is displayed in the middle of the visible ones
            startPage = Math.max(this.currentPage - Math.floor(this.visiblePages / 2), 1);
            endPage = startPage + this.visiblePages - 1;

            // Adjust if limit is exceeded
            if (endPage > this.totalPages) {
              endPage = this.totalPages;
              startPage = endPage - this.visiblePages + 1;
            }
        }

        // Add page number links
        for (let number = startPage; number <= endPage; number++) {
          pages.push(number);
        }

        return pages;
      }
    },

    created() {
      this.pageSize = this.itemsPerPage;
    },

    methods: {

      gotoInputPage(pageInput) {
          let page = Number.parseInt(pageInput);
          if(page <= 0 || page > this.totalPages) {
            this.$refs.input.value = this.currentPage;
          } else {
            this.selectPage(page);
          }

      },

      selectPage(page) {

        if (!this.disabled && this.currentPage !== page && page > 0 && page <= this.totalPages) {
          this.currentPage = page;
          this.$emit('page-changed', page, Number.parseInt(this.itemsPerPage));
        }
        console.log("currentPage=" + this.currentPage);
      },

      activePage (pageNum) {
        return this.currentPage === pageNum ? 'active' : ''
      },

      pageSizeChanged() {
        console.log("pageSizeChanged" + this.itemsPerPage)
        this.$emit('page-size-changed', Number.parseInt(this.itemsPerPage));
      }


    }
  }

</script>


<style scoped>
  .sacem-pager {
    margin: 1px;
  }

  .results {
    font-size:  13px;
  }

  .results p {
    padding:0 !important;
    margin:0 !important;
  }

</style>
