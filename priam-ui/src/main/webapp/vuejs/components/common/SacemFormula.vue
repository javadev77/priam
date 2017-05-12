<template>
  <div class="container-fluid sacem-formula">
    <sacem-collapsible-panel icon-class="{{iconClass}}" icon-info="{{iconInfo}}" heading="{{ panelHeading }}" on-collapse="onCollapse($collapsed)" is-collapsed="isCollapsed" collapsible="collapsible">
        <div>
          <form>
          </form>
        </div>

    </sacem-collapsible-panel>
  </div>
</template>

<script>

  export default {

      props : {

      },

      data() {

          return {
            panelHeading,
            iconClass
          }

      }


  }

</script>

<style>

</style>
