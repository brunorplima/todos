<script setup>
const { updatingTodo } = defineProps({
  todoId: { type: String, required: true },
  updatingTodo: { type: Object, required: true }
})

const emits = defineEmits([
  'openEdit',
  'closeEdit',
  'delete',
  'update'
])
</script>

<template>
  <div>
    <div v-if="!updatingTodo">
      <font-awesome-icon icon="pen" class="controller-icon" @click="emits('openEdit')"/>
      <font-awesome-icon icon="trash" class="controller-icon" @click="emits('delete', todoId)"/>
    </div>
    <div v-else class="d-flex flex-column">
      <font-awesome-icon icon="xmark" class="controller-icon" @click="emits('closeEdit')" size="lg"/>
      <font-awesome-icon icon="floppy-disk" class="controller-icon" @click="emits('update')"/>
    </div>
  </div>
</template>

<style scoped>
.controller-icon {
  color: var(--blue);
  padding: 6px;
  cursor: pointer;
  transition: .3s;
}
.controller-icon:hover {
  color: var(--blue-light);
}
</style>