<template>
  <transition name="modal">
    <div class="modal-mask" @click="$emit('close')">
      <div class="container-fluid"  style="width: 75%; margin-top: 50px;">

        <div class="modal-container" style="height: 850px; overflow-y: scroll; padding: 20px 24px;"  @click.stop>
          <slot name="body">
          </slot>
        </div>

      </div>
    </div>
  </transition>
</template>

<script>

  export default {
    props : {
      style : {
        type: String,
        required : false,
        default : ''
      }
    }
  }

</script>

<style>

  .modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
  }

  .modal-wrapper {
    display: table-cell;
    vertical-align: middle;
  }

  .modal-container {
    /*width: 600px;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #def;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
    transition: all .3s ease;
    font-family: Helvetica, Arial, sans-serif;*/

   /* width: auto;
    margin: 0px auto;
    background: #fff;
    border: none;
    border-radius: 7px;*/

    background-color: #f9fafb;
    border: 2px solid #ebedef;
    border-radius: 7px;
    -webkit-background-clip: border-box;
    -moz-background-clip: border-box;
    background-clip: border-box;
    -webkit-box-shadow: none;
    box-shadow: none;
  }

  .modal-header h3 {
    margin-top: 0;
    color: #42b983;
  }

  /*  .modal-body {
      margin: 20px 0;
    }*/

  .modal-default-button {
    float: right;
  }

  /*
   * The following styles are auto-applied to elements with
   * transition="modal" when their visibility is toggled
   * by Vue.js.
   *
   * You can easily play with the modal transition by editing
   * these styles.
   */

  .modal-enter {
    opacity: 0;
  }

  .modal-leave-active {
    opacity: 0;
  }

  .modal-enter .modal-container,
  .modal-leave-active .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
  }
</style>
