<script setup>
import { ref } from "vue";
import TodoInfo from "./TodoInfo.vue";
import TodoEdit from "./TodoEdit.vue";
import { updateTodo } from "../api/todos.js";

const { todo } = defineProps(['todo'])
const emits = defineEmits(['updateTodo'])
const updatingTodo = ref(null)

async function update() {
  const data = await updateTodo(updatingTodo.value)
  emits('updateTodo', data)
  updatingTodo.value = null
}
</script>

<template>
  <div class="card-body d-flex gap-2">
    <div class="">
      <input v-if="!updatingTodo" class="form-check-input" type="checkbox" :id="`todo-${todo.id}`">
    </div>

    <TodoInfo v-if="!updatingTodo" :todo="todo" />
    <TodoEdit v-else :updatingTodo="updatingTodo" />

    <div>
      <fa-icon v-if="!updatingTodo" icon="pen" class="controller-icon" @click="updatingTodo = { ...todo }" />
      <div v-else class="d-flex flex-column">
        <fa-icon icon="xmark" class="controller-icon" @click="updatingTodo = null" size="lg" />
        <fa-icon icon="floppy-disk" class="controller-icon" @click="update" />
      </div>
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