<script setup>
import { ref } from "vue";
import TodoInfo from "./TodoInfo.vue";
import TodoEdit from "./TodoEdit.vue";
import { deleteTodo, toggleTodoCompleted, updateTodo } from "../api/todos.js";
import TodoCardControllers from "./TodoCardControllers.vue";
import debounce from "../utils/debounce.js";

const { todo } = defineProps(['todo'])
const emits = defineEmits(['updateTodo', 'deleteTodo'])
const updatingTodo = ref(null)
const debouncedToggleCompleted = debounce((id, completed) => toggleTodoCompleted(id, completed), 500)

async function update() {
  try {
    const data = await updateTodo(updatingTodo.value)
    emits('updateTodo', data)
    updatingTodo.value = null
  } catch (e) {
    console.error(e)
  }
}

function openEditTodo() {
  updatingTodo.value = { ...todo }
}

function closeEditTodo() {
  updatingTodo.value = null
}

async function removeTodo(id) {
  try {
    await deleteTodo(id)
    emits('deleteTodo', id)
  } catch (e) {
    console.error(e)
  }
}
</script>

<template>
  <div class="card-body d-flex gap-2">
    <div class="">
      <input
        v-if="!updatingTodo"
        v-model="todo.completed"
        class="form-check-input"
        type="checkbox"
        :id="`todo-${todo.id}`"
        @input="debouncedToggleCompleted(todo.id, todo.completed)"
      >
    </div>

    <TodoInfo v-if="!updatingTodo" :todo="todo" />
    <TodoEdit v-else :updatingTodo="updatingTodo" />

    <TodoCardControllers
      id="delete-dialog"
      :updatingTodo="updatingTodo"
      :todoId="todo.id"
      @openEdit="openEditTodo"
      @closeEdit="closeEditTodo"
      @delete="removeTodo"
      @update="update"
    />
  </div>
</template>