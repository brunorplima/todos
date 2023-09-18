<script setup>
</script>

<template>
  <div id="app">
    <div class="">
      <router-link to="/new">
        <div id="addButton" class="btn btn-success"><font-awesome-icon icon="plus" /></div>
      </router-link>
      <h1 class="h1 d-flex justify-content-center m-4">BL Todo List</h1>
    </div>
    <router-view></router-view>
  </div>
</template>

<style scoped>
#app {
  max-width: 1000px;
  margin-left: auto;
  margin-right: auto;
}
#addButton {
  float: right;
  margin-right: 40px;
}
</style>
