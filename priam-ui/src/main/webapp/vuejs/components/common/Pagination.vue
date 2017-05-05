<template>
  <div class="row sacem-pager">
    <div class="col-sm-24">
      <div class="results pull-left">
        {{ (totalItems || 0) | number: 0 }} résultat{{ totalItems > 1 ? 's' : '' }}
        - Page {{ currentPage}} / {{ totalPages}}
            <span>
                - Resultat par page

            </span>
      </div>
      <div class="pull-right">
        <ul class="pagination-plain">
        <li class="previous">
          <a href="#" @click.prevent="pageChanged(1)" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </a>
        </li>
        <li v-for="n in paginationRange" :class="activePage(n)">
          <a href="#" @click.prevent="pageChanged(n)">{{ n }}</a>
        </li>
        <li class="next">
          <a href="#" @click.prevent="pageChanged(lastPage)" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
          </a>
        </li>
      </ul>
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
        required: true
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
      }
    },

    data () {

      return {}
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
      paginationRange () {
        let start = this.currentPage - this.visiblePages / 2 <= 0
          ? 1 : this.currentPage + this.visiblePages / 2 > this.lastPage
            ? Util.lowerBound(this.lastPage - this.visiblePages + 1, 1)
            : Math.ceil(this.currentPage - this.visiblePages / 2)
        let range = []
        for (let i = 0; i < this.visiblePages && i < this.lastPage; i++) {
          range.push(start + i)
        }
        return range
      }
    },

    methods: {
      pageChanged (pageNum) {
        this.$emit('page-changed', pageNum);
      },
      activePage (pageNum) {
        return this.currentPage === pageNum ? 'active' : ''
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
